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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartServiceImp{
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    BookDataRepository bookDataRepository;
    @Autowired
    TokenGenerator tokenGenerator;
    //saving a specific record by using the method save() of jpaRepository
    @Override
    public Cart addInCart(CartDTO cartDTO,String token) {
        int userId=tokenGenerator.decodeToken(token);
        Optional<UserData> userData = userDataRepository.findById(userId);
        Optional<BookData> bookData =bookDataRepository.findById(cartDTO.getBookId());
        if (bookData.isPresent() && userData.isPresent() && bookData.get().getQuantity() >= cartDTO.getQuantity()) {
            List<Cart> cartDataList = cartRepository.findAll();
            for (Cart data : cartDataList) {
                if (data.getCartId()==(cartDTO.getBookId())) {
                    throw (new CustomException("Cart is already existing"));
                }
            }
            double bookPrice = bookData.get().getPrice() * cartDTO.getQuantity();
            Cart cart = new Cart(userData.get(), bookData.get(), cartDTO.getQuantity(),bookPrice);
            return cartRepository.save(cart);
        } else {
            throw new CustomException("Book or user data is not available to add in cart");
        }

    }
    //getting all cards record by using the method findaAll() of JpaRepository
    @Override
    public Optional<Cart> getCartData(String token) {
        int userId = tokenGenerator.decodeToken(token);
       if(userDataRepository.findById(userId).isPresent()){
       return cartRepository.findById(userId);

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
    //updating a record
    @Override
    public Cart updateCartByToken(CartDTO cartDTO, String token, int cartId) {
        int userId = tokenGenerator.decodeToken(token);
        Optional<Cart> cart = cartRepository.findById(cartId);
        Optional<BookData> bookData = bookDataRepository.findById(cartDTO.getBookId());
        Optional<UserData> userData = userDataRepository.findById(userId);
        if (cart.isEmpty()) {
            throw new CustomException("Cart does not exist");
        } else {
            if (bookData.isPresent() && userData.isPresent()) {
                double bookPrice = bookData.get().getPrice() * cartDTO.getQuantity();
                Cart cartData1 = new Cart(userData.get(), bookData.get(), cartDTO.getQuantity(), bookPrice);
                return cartRepository.save(cartData1);
            } else {
                throw new CustomException("Book data or user token is not available in database");
            }
        }
    }
}
