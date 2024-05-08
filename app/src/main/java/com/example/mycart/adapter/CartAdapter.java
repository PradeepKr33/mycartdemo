package com.example.mycart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.R;
import com.example.mycart.model.BookCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private CartClickedListeners cartClickedListeners;
    private List<BookCart> bookCartList;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setBookCartList(List<BookCart> bookCartList) {
        this.bookCartList = bookCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        BookCart bookCart = bookCartList.get(position);
        holder.bookImageView.setImageResource(bookCart.getBookImage());
        holder.bookNameTv.setText(bookCart.getBookName());
        holder.bookQuantity.setText(bookCart.getQuantity() + "");
//        holder.bookPriceTv.setText(String.format("₹%s", bookCart.getTotalItemPrice()));
        holder.bookPriceTv.setText(String.format("₹%.2f", bookCart.getTotalItemPrice()));


        holder.deleteBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(bookCart);
            }
        });

        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(bookCart);
            }
        });

        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(bookCart);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (bookCartList == null) {
            return 0;
        } else {
            return bookCartList.size();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView bookNameTv, bookPriceTv, bookQuantity;
        private ImageView deleteBookBtn;
        private ImageView bookImageView;
        private ImageButton addQuantityBtn, minusQuantityBtn;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            bookNameTv = itemView.findViewById(R.id.eachCartItemName);
            bookPriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteBookBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            bookImageView = itemView.findViewById(R.id.eachCartItemIV);
            bookQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);

        }
    }
    public interface CartClickedListeners {
        void onDeleteClicked(BookCart bookCart);

        void onPlusClicked(BookCart bookCart);

        void onMinusClicked(BookCart bookCart);
    }
}
