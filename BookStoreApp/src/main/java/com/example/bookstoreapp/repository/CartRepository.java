package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
