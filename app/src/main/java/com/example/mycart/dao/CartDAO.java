package com.example.mycart.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycart.model.BookCart;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insertCartItem(BookCart bookCart);

    @Query("SELECT * FROM  book_table")
    LiveData<List<BookCart>> getAllCartItems();

    @Delete
    void deleteCartItem(BookCart bookCart);

    @Query("UPDATE book_table SET quantity=:quantity WHERE id=:id")
    void updateQuantity(int id , int quantity);

    @Query("UPDATE book_table SET totalItemPrice=:totalItemPrice WHERE id=:id")
    void updatePrice(int id , double totalItemPrice);

    @Query("DELETE FROM book_table")
    void deleteAllItems();

}
