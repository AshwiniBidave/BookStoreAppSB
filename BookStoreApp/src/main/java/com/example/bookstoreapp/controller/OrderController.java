package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("order")
public class OrderController{
    @Autowired
    OrderService orderService;
    @PostMapping("/add")
    ResponseEntity<ResponseDTO> addOrder(@RequestBody OrderDTO orderDTO, @RequestParam String token) {
    ResponseDTO response = new ResponseDTO("Product Added To Cart ", orderService.addOrderDetails(orderDTO,token));
    return new ResponseEntity<>(response, HttpStatus.CREATED);
}

        @PutMapping("/cancel/{id}")
        ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int id) {
            ResponseDTO response = new ResponseDTO("Order Cancelled Successfully ",orderService.deleteOrder(id));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @GetMapping("/get/{id}")
        ResponseEntity<ResponseDTO> getById(@PathVariable int id) {
            ResponseDTO response = new ResponseDTO("Product Added To Cart ", orderService.getOrderById(id));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @GetMapping("/get-all")
        ResponseEntity<ResponseDTO> getAllOrders() {
            ResponseDTO response = new ResponseDTO("Product Added To Cart ", orderService.getAllDetails());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
}