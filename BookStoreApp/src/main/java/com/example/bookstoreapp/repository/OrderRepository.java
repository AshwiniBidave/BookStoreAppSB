package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <OrderData ,Integer> {
}
