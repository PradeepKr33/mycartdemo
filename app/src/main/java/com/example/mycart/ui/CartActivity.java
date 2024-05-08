package com.example.mycart.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.R;
import com.example.mycart.adapter.CartAdapter;
import com.example.mycart.model.BookCart;
import com.example.mycart.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartClickedListeners {

    private RecyclerView recyclerView;
    private CartViewModel cartViewModel;
    private TextView totalCartPriceTv, textView;
    private Button checkoutBtn;
    private CartAdapter cartAdapter;
    CardView cardViewPO;
    ImageView logoutImg;
    RelativeLayout cartRel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeVariables();

        cartViewModel.getAllCartItems().observe(this, new Observer<List<BookCart>>() {
            @Override
            public void onChanged(List<BookCart> bookCarts) {
                double price = 0;
                cartAdapter.setBookCartList(bookCarts);
                for (int i = 0; i< bookCarts.size(); i++){
                    price = price + bookCarts.get(i).getTotalItemPrice();
                }
//                totalCartPriceTv.setText(String.valueOf(price));
//                totalCartPriceTv.setText("₹" + String.valueOf(price));
                totalCartPriceTv.setText(String.format("₹%.2f", price));
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.deleteAllCartItems();
                Toast.makeText(CartActivity.this, "ordered successfully", Toast.LENGTH_SHORT).show();
                cardViewPO.setVisibility(View.VISIBLE);
                checkoutBtn.setVisibility(View.INVISIBLE);
                totalCartPriceTv.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                cartRel.setVisibility(View.INVISIBLE);
            }
        });

        logoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
    }


    private void initializeVariables() {


        cartAdapter = new CartAdapter(this);
        textView = findViewById(R.id.textView2);
        cardViewPO = findViewById(R.id.cart_order);
        logoutImg = findViewById(R.id.logoutImg);
        cartRel = findViewById(R.id.cartRel);
        totalCartPriceTv = findViewById(R.id.cartActivityTotalPriceTv);
        checkoutBtn = findViewById(R.id.cartActivityCheckoutBtn);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(cartAdapter);


    }

    @Override
    public void onDeleteClicked(BookCart bookCart) {
        cartViewModel.deleteCartItem(bookCart);
        Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlusClicked(BookCart bookCart) {
        int quantity = bookCart.getQuantity() + 1;
        cartViewModel.updateQuantity(bookCart.getId() , quantity);
        cartViewModel.updatePrice(bookCart.getId() , quantity* bookCart.getBookPrice());
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusClicked(BookCart bookCart) {
        int quantity = bookCart.getQuantity() - 1;
        if (quantity != 0){
            cartViewModel.updateQuantity(bookCart.getId() , quantity);
            cartViewModel.updatePrice(bookCart.getId() , quantity* bookCart.getBookPrice());
            cartAdapter.notifyDataSetChanged();
        }else{
            cartViewModel.deleteCartItem(bookCart);
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
        }

    }
}