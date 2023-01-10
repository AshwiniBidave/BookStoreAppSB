package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.BookDataDTO;
import com.example.bookstoreapp.entity.BookData;

import java.util.List;


public interface BookServiceImp  {
    BookData addBook(String token,BookDataDTO bookDataDTO);

    BookData getById(int id);

    List<BookData> getBookList();

    String deleteById(int id);

    BookData updateById(int id, BookDataDTO bookDataDTO,String token);

    List<BookData> findBookByName(String bName);

    List<BookData> sortByName();

    List<BookData> sortByPrice();
    BookData updateQuantityById(int id, int quantity, String token);

}
