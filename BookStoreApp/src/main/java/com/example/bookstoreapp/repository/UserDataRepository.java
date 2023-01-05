package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserDataRepository extends JpaRepository<UserData,Integer> {
    @Query(value = "select * from user_data where login_Id= :loginID", nativeQuery = true)

    Optional<UserData> findByLoginById(String loginID);
}
