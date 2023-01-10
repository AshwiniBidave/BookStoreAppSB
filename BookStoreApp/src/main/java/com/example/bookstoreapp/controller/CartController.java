package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.entity.Cart;
import com.example.bookstoreapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//mark class as Controller
@RestController
@RequestMapping("card")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping("/saveInCart/{token}")
    public ResponseEntity<ResponseDTO> saveInCart(@RequestBody CartDTO cartDTO, @PathVariable String token) {
        ResponseDTO responseDto = new ResponseDTO("Data saved successfully", cartService.addInCart(cartDTO, token));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getData/{token}")
    public ResponseEntity<ResponseDTO> getCartData(@PathVariable String token) {
        ResponseDTO responseDto = new ResponseDTO("all cart data", cartService.getCartData(token));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/cartDataById/{cartId}/{token}")
    public ResponseEntity<ResponseDTO> dataById(@PathVariable int cartId, @PathVariable String token) {
        ResponseDTO responseDto = new ResponseDTO("Cart data founded by id", cartService.dataById(cartId, token));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/removeItem/{cartId}/{token}")
    public ResponseEntity<ResponseDTO> removeItemById(@PathVariable int cartId, @PathVariable String token) {
        ResponseDTO responseDto = new ResponseDTO("Data removed from cart", cartService.removeItemsFromCart(cartId, token));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }





}