package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.BookDataDTO;
import com.example.bookstoreapp.dto.ResponseDTO;

import com.example.bookstoreapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//mark class as Controller
@RestController
@RequestMapping("book")
public class BookDataController {
    //autowire the BooksService class
    @Autowired
    BookService bookService;

    /**
     * //creating post mapping that post the book detail in the database
     * @param bookDataDTO
     * @param token
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody BookDataDTO bookDataDTO,@RequestParam String token) {
        ResponseDTO responseDTO = new ResponseDTO("Book Added Successfully", bookService.addBook(token,bookDataDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * //creating a get mapping that retrieves the detail of a specific book
     * @param id
     * @return detail of a specific book,msg
     */

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable int id) {
        ResponseDTO responseDTO = new ResponseDTO("Book Retrieved Successfully", bookService.getById(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * //creating a get mapping that retrieves all the books detail from the database
     * @return list of books
     */
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getById() {
        ResponseDTO responseDTO = new ResponseDTO("Book List Retrieved Successfully", bookService.getBookList());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * //creating a delete mapping that deletes a specified book
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int id) {
        ResponseDTO responseDTO = new ResponseDTO("Book Deleted Successfully", bookService.deleteById(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * //creating put mapping that updates the book detail
     * @param id
     * @param bookDataDTO
     * @param token
     * @return
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable int id, @RequestBody BookDataDTO bookDataDTO,@RequestParam String token) {
        ResponseDTO responseDTO = new ResponseDTO("Book Updated Successfully", bookService.updateById(id, bookDataDTO,token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getByName/{bookName}")
    public ResponseEntity<ResponseDTO> getByName(@PathVariable String bookName) {
        ResponseDTO responseDTO = new ResponseDTO("Book Retrieved Successfully", bookService.findBookByName(bookName));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping(value = {"/bookName"})
    public ResponseEntity<ResponseDTO> sortByBookName() {
        ResponseDTO responseDto = new ResponseDTO("Book data sorted by Book name", bookService.sortByName());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping(value = {"/Price"})
    public ResponseEntity<ResponseDTO> sortByPriceAscending() {
        ResponseDTO responseDto = new ResponseDTO("Book data sorted by Book price",bookService.sortByPrice());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<ResponseDTO> updateQuantityById(@PathVariable int id, @PathVariable int quantity,@RequestParam String token) {
        ResponseDTO responseDTO = new ResponseDTO("Book Quantity Updated Successfully", bookService.updateQuantityById(id,quantity,token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
