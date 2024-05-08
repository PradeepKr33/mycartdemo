package com.example.mycart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mycart.model.BookCart;
import com.example.mycart.repository.CartRepo;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepo cartRepo;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }

    public LiveData<List<BookCart>> getAllCartItems() {
        return cartRepo.getAllCartItemsLiveData();
    }

    public void insertCartItem(BookCart bookCart) {
        cartRepo.insertCartItem(bookCart);
    }

    public void updateQuantity(int id, int quantity) {
        cartRepo.updateQuantity(id, quantity);
    }

    public void updatePrice(int id, double price) {
        cartRepo.updatePrice(id, price);
    }

    public void deleteCartItem(BookCart bookCart) {
        cartRepo.deleteCartItem(bookCart);
    }

    public void deleteAllCartItems() {
        cartRepo.deleteAllCartItems();
    }
}

