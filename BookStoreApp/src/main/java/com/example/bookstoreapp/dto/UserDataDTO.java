package com.example.bookstoreapp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    @Column(name = "login_id", nullable = false)
    private String loginID;
    private String password;
    private boolean isAdmin=true;


}
