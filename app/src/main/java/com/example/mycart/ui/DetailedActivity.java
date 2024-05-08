package com.example.mycart.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.R;
import com.example.mycart.model.BookCart;
import com.example.mycart.model.BookItem;
import com.example.mycart.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    private ImageView bookImageView,logoutImg;
    private TextView bookNameTV, bookPriceTV,detailActivityBookDs,detailTxt,ordered;
    private Button addToCartBtn,detailBuyBtn;
    private BookItem book;
    private CartViewModel viewModel;
    private List<BookCart> bookCartList;
    private CardView cardView,cart_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        book = getIntent().getParcelableExtra("bookItem");
        initializeVariables();

        viewModel.getAllCartItems().observe(this, new Observer<List<BookCart>>() {
            @Override
            public void onChanged(List<BookCart> bookCarts) {
                bookCartList.addAll(bookCarts);
            }
        });

        if (book != null) {
            setDataToWidgets();
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToRoom();
            }
        });

        detailBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_buy.setVisibility(View.VISIBLE);
                ordered.setVisibility(View.VISIBLE);
                Toast.makeText(DetailedActivity.this, "Purchased Successfully", Toast.LENGTH_SHORT).show();
                bookImageView.setVisibility(View.INVISIBLE);
                bookNameTV.setVisibility(View.INVISIBLE);
                bookPriceTV.setVisibility(View.INVISIBLE);
                addToCartBtn.setVisibility(View.INVISIBLE);
                detailBuyBtn.setVisibility(View.INVISIBLE);
                cardView.setVisibility(View.INVISIBLE);
                detailActivityBookDs.setVisibility(View.INVISIBLE);
                detailTxt.setVisibility(View.INVISIBLE);
            }
        });

        logoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void insertToRoom(){
        BookCart bookCart = new BookCart();
        bookCart.setBookName(book.getBookName());
        bookCart.setBookPrice(book.getBookPrice());
        bookCart.setBookImage(book.getBookImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!bookCartList.isEmpty()){
            for(int i = 0; i< bookCartList.size(); i++){
                if (bookCart.getBookName().equals(bookCartList.get(i).getBookName())){
                    quantity[0] = bookCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = bookCartList.get(i).getId();
                }
            }
        }

        if (quantity[0]==1){
            bookCart.setQuantity(quantity[0]);
            bookCart.setTotalItemPrice(quantity[0]* bookCart.getBookPrice());
            viewModel.insertCartItem(bookCart);
        }else{

            viewModel.updateQuantity(id[0] ,quantity[0]);
            viewModel.updatePrice(id[0] , quantity[0]* bookCart.getBookPrice());
        }

        Toast.makeText(this, "Added in cart", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DetailedActivity.this , CartActivity.class));
    }

    private void setDataToWidgets() {
        bookNameTV.setText(book.getBookName());
//        bookPriceTV.setText("₹" +String.valueOf(book.getBookPrice()));
        bookPriceTV.setText(String.format("₹%.2f", book.getBookPrice()));
        bookImageView.setImageResource(book.getBookImage());
        detailActivityBookDs.setText(book.getBookDetails());
    }

    private void initializeVariables() {

        bookCartList = new ArrayList<>();
        bookImageView = findViewById(R.id.detailActivityBookIV);
        bookNameTV = findViewById(R.id.detailActivityBookNameTv);
        bookPriceTV = findViewById(R.id.detailActivityBookPriceTv);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);
        detailBuyBtn = findViewById(R.id.detailBuyBtn);
        cart_buy = findViewById(R.id.cart_buy);
        cardView = findViewById(R.id.cardView);
        detailActivityBookDs = findViewById(R.id.detailActivityBookDs);
        logoutImg = findViewById(R.id.logoutImg);
        ordered = findViewById(R.id.ordered);
        detailTxt = findViewById(R.id.detailTxt);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }
}