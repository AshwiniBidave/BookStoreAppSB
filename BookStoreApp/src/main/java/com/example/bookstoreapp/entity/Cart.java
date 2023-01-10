package com.example.bookstoreapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserData userData;

    @ElementCollection
    @CollectionTable(name = "cart_books",joinColumns = @JoinColumn(name = "cart_id"))
    public List<Integer> bookData;

    @ElementCollection
    @CollectionTable(name = "cart_book_quantities",joinColumns = @JoinColumn(name = "cart_id"))
    public List<Integer> quantity;

    public Cart(UserData userData, List<Integer> bookData, List<Integer> quantity) {
        this.userData = userData;
        this.bookData = bookData;
        this.quantity = quantity;

    }
    public Cart(int cartId, UserData userData, List<Integer> bookData, List<Integer> quantity) {
        this.cartId = cartId;
        this.userData = userData;
        this.bookData = bookData;
        this.quantity = quantity;
    }

    public Cart() {

    }
}



