package com.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class LoginDTO {
    private String loginID;
    private String password;

}
