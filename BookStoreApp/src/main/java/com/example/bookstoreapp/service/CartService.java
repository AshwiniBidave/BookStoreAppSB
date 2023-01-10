package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.CartDTO;
import com.example.bookstoreapp.entity.BookData;
import com.example.bookstoreapp.entity.Cart;
import com.example.bookstoreapp.entity.UserData;
import com.example.bookstoreapp.exception.CustomException;
import com.example.bookstoreapp.repository.BookDataRepository;
import com.example.bookstoreapp.repository.CartRepository;
import com.example.bookstoreapp.repository.UserDataRepository;
import com.example.bookstoreapp.utility.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.logback.ColorConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements CartServiceImp{
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    BookDataRepository bookDataRepository;
    @Autowired
    BookService bookService;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    UserDataService userDataService;
    //saving a specific record by using the method save() of jpaRepository
    @Override
    public Cart addInCart(CartDTO cartDTO,String token) {
       UserData userData= userDataService.getUserDataById(token);
        ArrayList<BookData> bookList = new ArrayList<>();
        int userID = userData.getUserID();
        int cartId= 0;
        if (cartRepository.findCartByUserId(userID)==null) {
            List<Integer> bookIdList =cartDTO.bookId;
            List<Integer> quantities =cartDTO.quantity;
            float totalPrice = 0;
           for (int i = 0; i < bookIdList.size(); i++) {

                if (quantities.get(i) <= bookService.getById(bookIdList.get(i)).getQuantity()) {
                    bookList.add(bookService.getById(bookIdList.get(i)));
                    totalPrice += bookService.getById(bookIdList.get(i)).getPrice() * (quantities.get(i));

                }  else
                    throw new CustomException("Please select a small quantity to order as stocks are limited: Current stock for book id: " + bookIdList.get(i) + " is " + bookService.getById(bookIdList.get(i)).getQuantity() + ".");
            }}
         Cart cart = new Cart(userData, cartDTO.getBookId(), cartDTO.getQuantity());


        return cartRepository.save(cart);
    }


    //getting all cards record by using the method findaAll() of JpaRepository
    @Override
    public List<Cart> getCartData(String token) {
        int userId = tokenGenerator.decodeToken(token);
       if(userDataRepository.findById(userId).isPresent()){
           return cartRepository.findAll();
       }else{
           throw new CustomException("This user id is not present");
       }

    }
    //getting a specific record by using the method findById() of JpaRepository
    @Override
    public Cart dataById(int cartId, String token) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CustomException("Employee with this cart id=" + " " + cartId + " " + " is not available in the database"));
    }
    //deleting a specific record by using the method deleteById() of JpaRepository
    @Override
    public Cart removeItemsFromCart(int CartId, String token) {
        Cart cart=new Cart();
        Optional<Cart> deleteData = cartRepository.findById(CartId);
        if (deleteData.isPresent()) {
            cartRepository.delete(cart);
            return cart;
        } else {
            throw new CustomException(" Did not get any cart for specific cart id ");
        }    }





}
