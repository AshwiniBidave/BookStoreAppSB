package com.example.bookstoreapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {
    @Id
    @GeneratedValue
    public int orderId;

    @ElementCollection
    @CollectionTable(name = "books", joinColumns = @JoinColumn(name = "order_id"))
    public List<Integer> bookData;

    @ElementCollection
    @CollectionTable(name = "book_quantities", joinColumns = @JoinColumn(name = "order_id"))
    public List<Integer> quantity;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserData userData;

    public String address;
    public LocalDate orderDate = LocalDate.now();

    public boolean isActive = true;


    public OrderData(UserData userData, List<Integer> bookData, List<Integer> quantity, String address) {
        this.userData = userData;
        this.bookData = bookData;
        this.quantity=quantity;
        this.address = address;
        this.orderDate = getOrderDate();
        this.isActive = isActive();
    }

    public OrderData(UserData userData, List<Integer> bookData, List<Integer> quantity, String address, boolean isActive) {
        this.userData = userData;
        this.bookData = bookData;
        this.quantity=quantity;
        this.address = address;
        this.orderDate = getOrderDate();
        this.isActive = isActive;
    }

    public OrderData(String address, Cart cart) {
        this.userData = cart.getUserData();
        this.bookData = cart.getBookData();
        this.address = address;
        this.quantity=quantity;
        this.orderDate = getOrderDate();
        this.isActive = isActive();
    }



}
