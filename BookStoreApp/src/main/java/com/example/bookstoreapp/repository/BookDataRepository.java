package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.BookData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface BookDataRepository extends JpaRepository<BookData,Integer> {
    @Query(value = "SELECT * FROM book_data b WHERE b.book_name=:bookName", nativeQuery = true)

    List<BookData> findByName(@Param("bookName") String name);
    @Modifying
    @Transactional

    @Query(value = "update book_data set quantity=:quantity where book_id=:id", nativeQuery = true)
    BookData updateQuantityByID(int id, int quantity);
}
