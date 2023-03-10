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
    public List<Integer> bookId;
    public List<Integer> quantity;

}
