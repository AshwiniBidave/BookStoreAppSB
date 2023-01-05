package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.BookDataDTO;

import com.example.bookstoreapp.email.EmailService;
import com.example.bookstoreapp.entity.BookData;
import com.example.bookstoreapp.entity.UserData;
import com.example.bookstoreapp.exception.CustomException;
import com.example.bookstoreapp.repository.BookDataRepository;
import com.example.bookstoreapp.utility.TokenGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceImp {
    @Autowired
    BookDataRepository bookDataRepository;
    @Autowired
    UserDataService userDataService;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    EmailService emailService;
    //saving a specific record by using the method save() of jpaRepository
    @Override
    public BookData addBook(String token, BookDataDTO bookDataDTO) {
        UserData userData = userDataService.getUserDataById(token);
        if (userData.isAdmin()) {
           List<BookData> bookDataList = bookDataRepository.findAll();
            for (BookData bookData : bookDataList) {
                if (bookData.getBookName().equals(bookDataDTO.getBookName())) {
                    throw new CustomException("This is duplicate value");
                }
            }
            BookData bookData1 = new BookData(bookDataDTO);
            emailService.SendEmail(userData.getEmail(), "Book Created successfully!!", "User " + userData.getFirstName() + " has added new book successfully " + bookData1.getBookName() + ".");
            return bookDataRepository.save(bookData1);

        } else {
            throw new CustomException("you have no rights");

        }

    }

//getting a specific record by using the method findById() of JpaRepository

    @Override
    public BookData getById(int id) {
        return bookDataRepository.findById(id).orElseThrow(() -> new CustomException("Book  with id " + id + " does not exist in database..!"));
    }
    //getting all books record by using the method findaAll() of jpaRepository
    @Override
    public List<BookData> getBookList() {
        if (bookDataRepository.findAll().isEmpty()) {
            throw new CustomException("No Books in the bookshelf.");
        } else return bookDataRepository.findAll();

    }
    //deleting a specific record by using the method deleteById() of JpaRepository
    @Override
    public String deleteById(int id) {
        if (bookDataRepository.findById(id).isPresent()) {
            bookDataRepository.deleteById(id);
            return "Book with ID: " + id + " is Deleted Successfully!!";
        } else throw new CustomException("No book matches with the given ID");
    }


    @Override
    public BookData updateById(int id, BookDataDTO bookDataDTO,String token) {
        UserData userData = userDataService.getUserDataById(token);
        if (bookDataRepository.findById(id).isPresent()&&userData.isAdmin()){
            BookData bookData = new BookData(id, bookDataDTO);
            emailService.SendEmail(userData.getEmail(), "Book Updated successfully!!", "User " + userData.getFirstName() + " has updated book successfully " + bookData + ".");
            return bookDataRepository.save(bookData);
        } else throw new CustomException("No book matches with the given ID");
    }


    @Override
    public List<BookData> findBookByName(String bookName) {
        List<BookData> bookData = bookDataRepository.findByName(bookName);
        return bookData;

    }

    @Override
    public List<BookData> sortByName() {
        return bookDataRepository.findAll().stream().sorted(Comparator.comparing(BookData::getBookName)).collect(Collectors.toList());
    }

    @Override
    public List<BookData> sortByPrice() {
        return bookDataRepository.findAll().stream().sorted(Comparator.comparing(BookData::getPrice)).collect(Collectors.toList());
    }

    @Override
    public BookData updateQuantityById(int id, int quantity) {
        if (bookDataRepository.findById(id).isPresent()) {
            BookData book = bookDataRepository.updateQuantityByID(id, quantity);
            System.out.println(bookDataRepository.findAll());
            return bookDataRepository.save(book);
        } else {
            throw (new CustomException("This id is not present"));
        }

    }


}