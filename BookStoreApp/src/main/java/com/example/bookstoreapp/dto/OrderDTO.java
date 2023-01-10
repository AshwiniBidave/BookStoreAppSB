package com.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    public String address;

    public int userId;
    public List<Integer> bookId;
    public List<Integer> quantity;
    public LocalDate orderDate = LocalDate.now();
}
