package com.project.bookstore.controller;

import com.project.bookstore.request.BuyRequest;
import com.project.bookstore.request.ItemRequest;
import com.project.bookstore.response.BuyResponse;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.OrderService;
import com.project.bookstore.testutils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
@AutoConfigureMockMvc
class OrdersControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    void order() throws Exception {
        List<ItemRequest> items = new ArrayList<>();
        items.add(new ItemRequest("123", 1, 2.20));
        BuyRequest buyRequest = new BuyRequest("address", items, "COD", 2.22);
        Mockito.when(orderService.order("username", buyRequest)).thenReturn(new BuyResponse("123456"));
        mockMvc.perform(post("/order").param("username", TestUtils.asJsonString(buyRequest)).header("accessToken", "token").content(TestUtils.asJsonString(buyRequest)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").isString());
    }*/

    @Test
    void createOrderShouldReturnUnauthorizedWhenAccessTokenNotPassedInHeader() throws Exception {
        mockMvc.perform(post("/order"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getOrdersShouldReturnUnauthorizedWhenAccessTokenNotPassedInHeader() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isUnauthorized());
    }
}