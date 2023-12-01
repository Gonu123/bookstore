package com.project.bookstore.service;

import com.project.bookstore.dto.Item;
import com.project.bookstore.dto.Order;
import com.project.bookstore.entity.BooksEntity;
import com.project.bookstore.entity.ItemsEntity;
import com.project.bookstore.entity.OrderEntity;
import com.project.bookstore.entity.UserEntity;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.ItemRepository;
import com.project.bookstore.repository.OrderRepository;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.request.BuyRequest;
import com.project.bookstore.request.ItemRequest;
import com.project.bookstore.response.BuyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(OrderService.class)
@AutoConfigureMockMvc
class OrderServiceTest {
    @MockBean
    BookRepository bookRepository;

    @Autowired
    private OrderService orderService;
    @MockBean
    UserRepository userRepository;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    OrderRepository orderRepository;

    @Test
    void orderSuccessful() throws Exception {
        Mockito.when(userRepository.getByUsername("username")).thenReturn(new UserEntity());
        OrderEntity orderEntity = new OrderEntity("123", "address", "u1234", "COD");
        Mockito.when(orderRepository.save(any())).thenReturn(orderEntity);
        List<ItemRequest> items = new ArrayList<>();
        items.add(new ItemRequest("123", 1, 2.20));
        BuyRequest buyRequest = new BuyRequest("address", items, "COD", 2.22);
        BuyResponse response = orderService.order("username", buyRequest);
        assertEquals("123", response.orderId());
    }

    @Test
    void orderFailureInUserNotFound() {
        Mockito.when(userRepository.getByUsername("username")).thenThrow(RuntimeException.class);
        List<ItemRequest> items = new ArrayList<>();
        items.add(new ItemRequest("123", 1, 2.20));
        BuyRequest buyRequest = new BuyRequest("address", items, "COD", 2.22);
        assertThrows(Exception.class, () -> orderService.order("username", buyRequest));
    }

    @Test
    void orderFailureOnItemsEmpty() {
        Mockito.when(userRepository.getByUsername("username")).thenReturn(new UserEntity());
        List<ItemRequest> items = new ArrayList<>();
        BuyRequest buyRequest = new BuyRequest("address", items, "COD", 2.22);
        assertThrows(Exception.class, () -> orderService.order("username", buyRequest));
    }

    @Test
    void orderFailureOnOrdersNotStored() {
        Mockito.when(userRepository.getByUsername("username")).thenReturn(new UserEntity());
        Mockito.when(orderRepository.save(any())).thenThrow(RuntimeException.class);
        List<ItemRequest> items = new ArrayList<>();
        items.add(new ItemRequest("123", 1, 2.20));
        BuyRequest buyRequest = new BuyRequest("address", items, "COD", 2.22);
        assertThrows(Exception.class, ()-> orderService.order("username", buyRequest));
    }

    @Test
    void getOrdersShouldReturnOrdersForTheGivenUser() {
        Mockito.when(userRepository.getByUsername("vineeth")).thenReturn(new UserEntity("u1234", "vin-450", "Vineeth R", "phNo", "pwd"));

        List<OrderEntity> ordersEntities = new ArrayList<>();
        ordersEntities.add(new OrderEntity("o1234", "Bluru", "u1234", "COD"));
        ordersEntities.add(new OrderEntity("o4567", "Mysuru", "u1234", "COD"));
        Mockito.when(orderRepository.findByUserIdOrderByOrderDateDesc("u1234"))
                .thenReturn(ordersEntities);

        List<ItemsEntity> itemsEntities1 = new ArrayList<>();
        itemsEntities1.add(new ItemsEntity("o1234", "isbn1", 2));
        itemsEntities1.add(new ItemsEntity("o1234", "isbn2", 1));

        List<ItemsEntity> itemsEntities2 = new ArrayList<>();
        itemsEntities2.add(new ItemsEntity("o4567", "isbn3", 3));

        Mockito.when(itemRepository.findByIdOrderId("o1234")).thenReturn(itemsEntities1);
        Mockito.when(itemRepository.findByIdOrderId("o4567")).thenReturn(itemsEntities2);

        Mockito.when(bookRepository.getByIsbn("isbn1"))
                .thenReturn(new BooksEntity("isbn1", "Abc", "Abc", "Some author", "2023", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));
        Mockito.when(bookRepository.getByIsbn("isbn2"))
                .thenReturn(new BooksEntity("isbn2", "New Abc", "Abc", "Some author 2", "2023", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));
        Mockito.when(bookRepository.getByIsbn("isbn3"))
                .thenReturn(new BooksEntity("isbn3", "New Abc : 1", "Abc", "Some author 3", "2024", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));

        List<Order> expectedOrders = new ArrayList<>();
        List<Item> order1Items = new ArrayList<>();
        order1Items.add(new Item("Abc", "Some author", 2, "MImageUrl"));
        order1Items.add(new Item("New Abc", "Some author 2", 1, "MImageUrl"));
        expectedOrders.add(new Order("o1234", "Bluru", "COD", order1Items, ""));

        List<Item> order2Items = new ArrayList<>();
        order2Items.add(new Item("New Abc : 1", "Some author 3", 3, "MImageUrl"));
        expectedOrders.add(new Order("o4567", "Mysuru", "COD", order2Items, ""));

        List<Order> serviceResponse = orderService.getOrders("vineeth");

        Assertions.assertEquals(serviceResponse, expectedOrders);
    }
}