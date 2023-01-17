package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.email.EmailService;
import com.example.bookstoreapp.entity.BookData;
import com.example.bookstoreapp.entity.Cart;
import com.example.bookstoreapp.entity.OrderData;
import com.example.bookstoreapp.entity.UserData;
import com.example.bookstoreapp.exception.CustomException;
import com.example.bookstoreapp.repository.CartRepository;
import com.example.bookstoreapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    UserDataService userDataService;
    @Autowired
    BookService bookService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    EmailService emailService;
    @Autowired
    OrderRepository orderRepository;



    @Override
    public OrderData addOrderDetails(OrderDTO orderDTO, String token) {
        ArrayList<BookData> bookList = new ArrayList<>();
        UserData userData = userDataService.getUserDataById(token);
        List<Integer> bookIdList = orderDTO.bookId;
        List<Integer> quantities = orderDTO.quantity;
        float totalPrice = 0;
        for (int i = 0; i < bookIdList.size(); i++) {

            if (quantities.get(i) <= bookService.getById(bookIdList.get(i)).getQuantity()) {
                bookList.add(bookService.getById(bookIdList.get(i)));
                totalPrice += bookService.getById(bookIdList.get(i)).getPrice() * (quantities.get(i));
                bookService.updateQuantityById(bookIdList.get(i), bookService.getById(bookIdList.get(i)).getQuantity() - (quantities.get(i)), token);

            } else
                throw new CustomException("Please select a small quantity to order as stocks are limited: Current stock for book id: " + bookIdList.get(i) + " is " + bookService.getById(bookIdList.get(i)).getQuantity() + ".");
        }
        List<String> nameList = bookList.stream().map(BookData::getBookName).toList();
        OrderData orderData = new OrderData(userData, orderDTO.getBookId(), orderDTO.getQuantity(), orderDTO.address);

        emailService.SendEmail(userData.getEmail(), "Order Created Successfully on ", "Order placed on" + orderDTO.getOrderDate() + " for books" + nameList + ". Total price is " + totalPrice);
        return orderRepository.save(orderData);
    }


    @Override
    public List<OrderData> getAllDetails() {
        if (orderRepository.findAll().isEmpty()) {
            throw new CustomException("No Orders existing.");
        } else return orderRepository.findAll();
    }


    @Override
    public OrderData getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new CustomException("Order  with id " + orderId + " does not exist in database..!"));

    }

    @Override
    public OrderData deleteOrder(int orderId) {
        OrderData order = getOrderById(orderId);
        order.isActive = false;
        return order;

    }

    @Override
    public OrderData placeorder(String token) {

        List<BookData> bookList1 = new ArrayList<>();
        UserData userData = userDataService.getUserDataById(token);
        Cart cart = cartRepository.findCartByUserId(userData.getUserID());
        List<Integer> bookIdLists = cart.getBookData();
        List<Integer> quantity = cart.getQuantity();
        float totalPrice = 0;
        for (int i = 0; i < bookIdLists.size(); i++) {

            if (quantity.get(i) <= bookService.getById(bookIdLists.get(i)).getQuantity()) {
                bookList1.add(bookService.getById(bookIdLists.get(i)));
                totalPrice += bookService.getById(bookIdLists.get(i)).getPrice() * (quantity.get(i));
                bookService.updateQuantityById(bookIdLists.get(i), bookService.getById(bookIdLists.get(i)).getQuantity() - (quantity.get(i)), token);

            } else
                throw new CustomException("Please select a small quantity to order as stocks are limited: Current stock for book id: " + bookIdLists.get(i) + " is " + bookService.getById(bookIdLists.get(i)).getQuantity() + ".");
        }
        List<String> nameList = bookList1.stream().map(BookData::getBookName).toList();
        OrderData orderData = new OrderData(userData, cart.getBookData(), cart.getQuantity(), userData.getAddress());
        emailService.SendEmail(userData.getEmail(), "Order Created Successfully on ", "Order placed on" + orderData.getOrderDate() + " for books" + nameList + ". Total price is " + totalPrice);

        return orderRepository.save(orderData);


    }


}
