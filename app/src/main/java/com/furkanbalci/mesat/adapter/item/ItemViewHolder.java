package com.furkanbalci.mesat.adapter.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanbalci.mesat.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView category;
    public TextView owner;
    public TextView price;
    public ImageView imageView;
    private ItemAdapter itemAdapter;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.item_title);
        this.category = itemView.findViewById(R.id.item_category);
        this.owner = itemView.findViewById(R.id.item_owner_name);
        this.price = itemView.findViewById(R.id.item_price);
        this.imageView = itemView.findViewById(R.id.item_image);

        View view = itemView.findViewById(R.id.item_card);
        view.setOnClickListener(v -> {
            System.out.println(this.title.getText());
        });
    }

    public ItemViewHolder linkAdapter(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
        return this;
    }
}
