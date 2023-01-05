package com.example.bookstoreapp.entity;

import com.example.bookstoreapp.dto.BookDataDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor

public class BookData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", nullable = false)
    private int bookID;
    private String bookName;
    private String author;
    private float price;
    private LocalDate arrivalDate;

    private String coverImage;
    @JsonDeserialize
    private int quantity;


    public BookData(BookDataDTO bookDataDTO) {
        this.bookName = bookDataDTO.getBookName();
        this.author = bookDataDTO.getAuthor();
        this.price = bookDataDTO.getPrice();
        this.arrivalDate = bookDataDTO.getArrivalDate();
        this.coverImage = bookDataDTO.getCoverImage();
        this.quantity = bookDataDTO.getQuantity();
    }

    public BookData(int id, BookDataDTO bookDataDTO) {
        this.bookID = id;
        this.bookName = bookDataDTO.getBookName();
        this.author = bookDataDTO.getAuthor();
        this.price = bookDataDTO.getPrice();
        this.arrivalDate = bookDataDTO.getArrivalDate();
        this.coverImage = bookDataDTO.getCoverImage();
        this.quantity = bookDataDTO.getQuantity();
    }

    public BookData(BookData bookData) {
        this.bookID = bookData.bookID;
        this.bookName = bookData.getBookName();
        this.author = bookData.getAuthor();
        this.price = bookData.getPrice();
        this.arrivalDate = bookData.getArrivalDate();
        this.coverImage = bookData.getCoverImage();
        this.quantity = bookData.getQuantity();
    }
}
