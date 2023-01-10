package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart,Integer>{
    @Query(value = "SELECT * FROM cart c where c.user_Id = :userID", nativeQuery = true)
Cart findCartByUserId(int userID);

        Cart getById(int cartId);
}
