package com.example.mycart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.R;
import com.example.mycart.model.BookItem;

import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.BookItemViewHolder> {

    private List<BookItem> bookItemList;

    private BookClickedListeners bookClickedListeners;


    public BookItemAdapter(BookClickedListeners bookClickedListeners){
        this.bookClickedListeners = bookClickedListeners;
    }

    public void setBookItemList(List<BookItem> bookItemList){
        this.bookItemList = bookItemList;
    }


    @NonNull
    @Override
    public BookItemAdapter.BookItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_book,parent,false);
        return new BookItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemAdapter.BookItemViewHolder holder, int position) {
        BookItem bookItem = bookItemList.get(position);
        holder.bookNameTv.setText(bookItem.getBookName());
        holder.bookPriceTv.setText(String.format("₹%.2f", bookItem.getBookPrice()));
        holder.bookImageView.setImageResource(bookItem.getBookImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookClickedListeners.onCardClicked(bookItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (bookItemList == null){
            return 0;
        }else{
            return bookItemList.size();
        }
    }

    public class BookItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookImageView;
        private TextView bookNameTv, bookPriceTv;
        private CardView cardView;
        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachBookCardView);
            bookImageView = itemView.findViewById(R.id.eachBookIv);
            bookNameTv = itemView.findViewById(R.id.eachBookName);
            bookPriceTv = itemView.findViewById(R.id.eachBookPriceTv);

        }
    }
    public interface BookClickedListeners{
        void onCardClicked(BookItem shoe);
    }
}
