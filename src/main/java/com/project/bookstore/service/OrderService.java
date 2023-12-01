package com.project.bookstore.service;

import com.project.bookstore.dto.Item;
import com.project.bookstore.dto.Order;
import com.project.bookstore.entity.BooksEntity;
import com.project.bookstore.entity.ItemsEntity;
import com.project.bookstore.entity.OrderEntity;
import com.project.bookstore.entity.UserEntity;
import com.project.bookstore.exceptions.BookOutOfStockException;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.ItemRepository;
import com.project.bookstore.repository.OrderRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.request.BuyRequest;
import com.project.bookstore.response.BuyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ItemRepository itemRepository;

    public BuyResponse order(String username, BuyRequest buyRequest) throws Exception {
            String orderId = UUID.randomUUID().toString();

            UserEntity userEntity = userRepository.getByUsername(username);
            OrderEntity orderEntity = new OrderEntity(orderId, buyRequest.getAddress(), userEntity.getUserid(), buyRequest.getModeOfPayment(), buyRequest.getPrice());

            List<ItemsEntity> itemsEntities = new ArrayList<>();
            List<String> bookNames = new ArrayList<>();
            List<BooksEntity> booksEntities = new ArrayList<>();
            buyRequest.getItems().forEach((item) -> {
                BooksEntity book = bookRepository.getByIsbn(item.isbn());
                int booksAvailable = book.getBooksAvailable();
                if (booksAvailable >= item.quantity()) {
                    itemsEntities.add(new ItemsEntity(orderId, item.isbn(), item.quantity(), item.price()));
                    book.setBooksAvailable(book.getBooksAvailable() - item.quantity());
                    booksEntities.add(book);
                } else {
                    bookNames.add(book.getName());
                }
            });
            if (bookNames.size() > 0) {
                throw new BookOutOfStockException(bookNames);
            }
            OrderEntity responseEntity = orderRepository.save(orderEntity);
            itemRepository.saveAll(itemsEntities);
            bookRepository.saveAll(booksEntities);
            return new BuyResponse(responseEntity.getOrderId());
    }

    public List<Order> getOrders(String username) {
        UserEntity userEntity = userRepository.getByUsername(username);
        List<OrderEntity> orderEntities = orderRepository.findByUserIdOrderByOrderDateDesc(userEntity.userid);

        return orderEntities.stream().map((order) -> {
            List<Item> items = itemRepository.findByIdOrderId(order.getOrderId()).stream().map((item) -> {
                BooksEntity booksEntity = bookRepository.getByIsbn(item.getId().getBookId());

                return new Item(booksEntity.getName(), booksEntity.getAuthor(), item.getQuantity(), booksEntity.getImageUrlM(), item.getPrice());
            }).toList();

            return new Order(order.getOrderId(), order.getAddress(), order.getModeOfPayment(), items, order.getOrderDate(), order.getTotalPrice());
        }).toList();
    }
}
