package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.entity.OrderData;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderServiceImp {
    OrderData addOrderDetails(OrderDTO orderDTO,String token);
    List<OrderData> getAllDetails();
    OrderData getOrderById(int orderId);
    OrderData deleteOrder(int orderId);
    OrderData placeorder(String token);
}
