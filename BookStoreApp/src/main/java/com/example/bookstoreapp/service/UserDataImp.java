package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.LoginDTO;
import com.example.bookstoreapp.dto.UserDataDTO;
import com.example.bookstoreapp.entity.UserData;

import java.util.List;

public interface UserDataImp {
String addUserData(UserDataDTO userDataDTO);
List<UserData> getAllUSerData(String token);
UserData getUserDataById(String token);
UserData updateUSerData(int userID,UserDataDTO userDataDTO);
String loginUser(LoginDTO loginDTO);
void deleteUserById(int userID);
}
