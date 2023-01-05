package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.LoginDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.dto.UserDataDTO;
import com.example.bookstoreapp.email.EmailService;
import com.example.bookstoreapp.entity.UserData;
import com.example.bookstoreapp.exception.CustomException;
import com.example.bookstoreapp.repository.UserDataRepository;
import com.example.bookstoreapp.utility.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService implements UserDataImp{
    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    EmailService emailService;
    //saving a specific record by using the method save() of jpaRepository
    @Override
    public String addUserData(UserDataDTO userDataDTO) {
        UserData userData=new UserData(userDataDTO);
        userDataRepository.save(userData);
        String token = tokenGenerator.createToken(userData.getUserID());
        emailService.SendEmail(userDataDTO.getEmail(), "Account Sign-up Successful", "Hello " + userData.getFirstName() + "Your account has been created. Your Token is " + token + " .Keep this token safe to access your account in future.");
        return token;

    }

    //getting all users record by using the method findaAll() of JpaRepository
    @Override
    public List<UserData> getAllUSerData(String token) {
        userDataRepository.findAll();
        if (getUserDataById(token).isAdmin()) {
            return userDataRepository.findAll();

        } else throw new CustomException("No Users in the list.");
    }

    //getting a specific record by using the method findById() of JpaRepository
    @Override
    public UserData getUserDataById(String token) {
        int UserID = tokenGenerator.decodeToken(token);
        return userDataRepository.findById(UserID).orElseThrow(() -> new CustomException("User  with id " + UserID + " does not exist in database..!"));


    }

    //updating a specific record  by using the method save() of JpaRepositoty

    @Override
    public UserData updateUSerData(int userID, UserDataDTO userDataDTO) {
      Optional<UserData> userData= userDataRepository.findById(userID);
        if(userData.isPresent()){
            UserData userData1=new UserData(userID,userDataDTO);
            userData1.setUserID(userID);
            return userDataRepository.save(userData1);

        }else{
            throw new CustomException("user data with id=" + " " + userID+ " " + " is not founded");

        }
    }

    public String loginUser(LoginDTO loginDTO) {
        Optional<UserData> existing = userDataRepository.findByLoginById(loginDTO.getLoginID());
        if (existing.isPresent()) {
            if (existing.get().getPassword().equals(loginDTO.getPassword())) {
                System.out.println("Login Successful");
                return "Login Successful";
            }
        }
        return "Login Credential Wrong";
    }
//deleting a specific record by using the method deleteById() of JpaRepository

    @Override
    public void deleteUserById(int userID) {
        Optional<UserData> userData= userDataRepository.findById(userID);
        if(userData.isPresent()) {
            userDataRepository.deleteById(userID);
        }else{
            throw new CustomException("User data to be deleted with id =" + userID+ " " + "is not founded");

        }
    }
}
