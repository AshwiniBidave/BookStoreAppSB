package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.LoginDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.dto.UserDataDTO;
import com.example.bookstoreapp.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//mark class as Controller
@RestController
@RequestMapping("user")
public class UserDataController {
    //autowire the UserService class
    @Autowired
    UserDataService userDataService;

    /**
     * //creating post mapping that post the user detail in the database
     * @param userDataDTO
     * @return
     */
    @PostMapping("create")
    public ResponseEntity<ResponseDTO> CreateUserData(@RequestBody  UserDataDTO userDataDTO) {
      ResponseDTO responseDTO=new ResponseDTO("user data added successfully",userDataService.addUserData(userDataDTO));
      return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * //creating a get mapping that retrieves the detail of a specific user
     * @param token
     * @return detail of a specific user
     */
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getById(@RequestParam String token) {
        ResponseDTO responseDTO = new ResponseDTO("UserData Retrieved Successfully", userDataService.getUserDataById(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * //creating a get mapping that retrieves all the user detail from the database
     * @param token
     * @return list of users
     */
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getUserList(@RequestParam String token) {
        ResponseDTO responseDTO = new ResponseDTO("UserData List Retrieved Successfully", userDataService.getAllUSerData(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * creating a get mapping that retrieves the user login and password fron the database
     * @param loginDTO
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        ResponseDTO responseDTO = new ResponseDTO("UserData Retrieved Successfully", userDataService.loginUser(loginDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
