package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.dto.UserDataDTO;
import com.example.bookstoreapp.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartServiceImp {
    Cart addInCart(CartDTO cartDTO,String token);
    List<Cart> getCartData(String token);
    Cart dataById(int cartId, String token);
    Cart removeItemsFromCart(int CartId,String token);


}
