package com.example.bookstoreapp.entity;

import com.example.bookstoreapp.dto.LoginDTO;
import com.example.bookstoreapp.dto.UserDataDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor

public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    @Column(name = "login_id", nullable = false)
    private String loginID;
    private String password;
    private boolean isAdmin=true;

    public UserData(int id, UserDataDTO userDataDTO) {
        this.userID = id;
        this.firstName = userDataDTO.getFirstName();
        this.lastName = userDataDTO.getLastName();
        this.email = userDataDTO.getEmail();
        this.address = userDataDTO.getAddress();
        this.loginID = userDataDTO.getLoginID();
        this.password = userDataDTO.getPassword();
        this.isAdmin = userDataDTO.isAdmin();
    }


    public UserData(UserDataDTO userDataDTO) {
        this.firstName = userDataDTO.getFirstName();
        this.lastName = userDataDTO.getLastName();
        this.email = userDataDTO.getEmail();
        this.address = userDataDTO.getAddress();
        this.loginID = userDataDTO.getLoginID();
        this.password = userDataDTO.getPassword();
        this.isAdmin = userDataDTO.isAdmin();
    }

    public UserData(LoginDTO loginDTO) {
        this.loginID = loginDTO.getLoginID();
        this.password = loginDTO.getPassword();
    }
}



