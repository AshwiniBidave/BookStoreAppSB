package com.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CartDTO {
    public int userId;
    public int bookId;
    public int quantity;
    public  double bookPrice;

}
