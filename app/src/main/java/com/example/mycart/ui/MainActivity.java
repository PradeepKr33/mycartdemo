package com.example.mycart.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycart.R;
import com.example.mycart.adapter.BookItemAdapter;
import com.example.mycart.model.BookCart;
import com.example.mycart.model.BookItem;
import com.example.mycart.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookItemAdapter.BookClickedListeners {

    private RecyclerView recyclerView;
    private List<BookItem> bookItemList;
    private BookItemAdapter adapter;
    private CartViewModel viewModel;
    private List<BookCart> bookCartList;
    private ImageView cartImageView,logoutImg;
    private TextView item_count;
    Dialog dialog;
    AppCompatButton yesBtn, noBtn;
    TextView marqueeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        setUpList();

        adapter.setBookItemList(bookItemList);
        recyclerView.setAdapter(adapter);

        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        viewModel.getAllCartItems().observe(this, new Observer<List<BookCart>>() {
//            @Override
//            public void onChanged(List<BookCart> bookCarts) {
//                bookCartList.addAll(bookCarts);
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getAllCartItems().observe(this, new Observer<List<BookCart>>() {
            @Override
            public void onChanged(List<BookCart> bookCarts) {
                bookCartList.clear();
                bookCartList.addAll(bookCarts);
                updateItemCount();
            }
        });
    }

    private void setUpList() {
        bookItemList.add(new BookItem("C Programming", R.drawable.book_cpp, 150.98,"This is best C Programming book"));
        bookItemList.add(new BookItem("Data Analytics", R.drawable.book_da, 201.00,"Best book for beginners"));
        bookItemList.add(new BookItem(".NET", R.drawable.book_dotnet, 189.00,"Good to learn .NET"));
        bookItemList.add(new BookItem("SQL", R.drawable.book_sql, 672.5,"A dedicated Database learning book"));
        bookItemList.add(new BookItem("Machine Learning", R.drawable.book_ml, 916.5,"Machine Learning - For intermediate"));
        bookItemList.add(new BookItem("Web Development", R.drawable.book_wcd, 316.5,"Good book for web developers"));
        bookItemList.add(new BookItem("JavaScript", R.drawable.book_cwj, 316.5,"Best content of JavaScript"));
    }

    private void initializeVariables() {
        cartImageView = findViewById(R.id.cartIv);
        RelativeLayout relativeLayout = findViewById(R.id.root);
        item_count = findViewById(R.id.list_item_count);
        logoutImg = findViewById(R.id.logoutImg);
        marqueeText = findViewById(R.id.marqueeText);


        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_alert_dialogbox);
        //first LayoutParams for width  and second for height
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //background of my logout layout(CardView), I make it transparent
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);
        yesBtn = dialog.findViewById(R.id.yesBtn);
        noBtn = dialog.findViewById(R.id.noBtn);

        bookCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        bookItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new BookItemAdapter(this);


        //runnable text
        marqueeText.setSelected(true);

        logoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(MainActivity.this, "Exit Successfully!", Toast.LENGTH_SHORT).show();
            }
        });


    }




// Using this taking cart_item*quantity

//    private void updateItemCount() {
//        int totalQuantity = 0;
//        for (BookCart bookCart : bookCartList) {
//            totalQuantity += bookCart.getQuantity();
//        }
//        item_count.setText(String.valueOf(totalQuantity));
//    }
    private void updateItemCount() {
        int itemCount = bookCartList.size();
        item_count.setText(String.valueOf(itemCount));
    }

    @Override
    public void onCardClicked(BookItem bookItem) {
        Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
        intent.putExtra("bookItem", bookItem);
        startActivity(intent);
    }
}
