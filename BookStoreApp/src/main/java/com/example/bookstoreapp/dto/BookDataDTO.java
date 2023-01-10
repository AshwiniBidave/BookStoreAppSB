package com.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDataDTO {
    private String bookName;
    private String author;
    private float price;
    private LocalDate arrivalDate=LocalDate.now();
    private String coverImage;
    private int quantity;


}
