package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.OrderDTO;
import com.example.bookstoreapp.email.EmailService;
import com.example.bookstoreapp.entity.BookData;
import com.example.bookstoreapp.entity.OrderData;
import com.example.bookstoreapp.entity.UserData;
import com.example.bookstoreapp.exception.CustomException;
import com.example.bookstoreapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp{
    @Autowired
    UserDataService userDataService;
    @Autowired
    BookService bookService;
    @Autowired
    EmailService emailService;
    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderData addOrderDetails(OrderDTO orderDTO,String token) {
        ArrayList<BookData> bookList=new ArrayList<>();
        UserData userData =userDataService.getUserDataById(token);
        List<Integer> bookIdList = orderDTO.bookId;
        List<Integer> quantities = orderDTO.quantity;
        float totalPrice = 0;
        for (int i = 0; i < bookIdList.size(); i++) {

            if (quantities.get(i) <= bookService.getById(bookIdList.get(i)).getQuantity()) {
                bookList.add(bookService.getById(bookIdList.get(i)));
                totalPrice += bookService.getById(bookIdList.get(i)).getPrice() * (quantities.get(i));
                bookService.updateQuantityById(bookIdList.get(i), bookService.getById(bookIdList.get(i)).getQuantity() - (quantities.get(i)),token );

            }  else
                throw new CustomException("Please select a small quantity to order as stocks are limited: Current stock for book id: " + bookIdList.get(i) + " is " + bookService.getById(bookIdList.get(i)).getQuantity() + ".");
        }
        List<String> nameList = bookList.stream().map(BookData::getBookName).toList();
        OrderData order = new OrderData(userData, orderDTO.getBookId(), orderDTO.getQuantity(), orderDTO.address);

        emailService.SendEmail(userData.getEmail(), "Order Created Successfully on ", "Order placed on" + orderDTO.getOrderDate() + " for books" + nameList + ". Total price is " + totalPrice);
        return orderRepository.save(order);
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
}
