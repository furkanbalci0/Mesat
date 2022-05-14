package com.furkanbalci.mesat.adapter.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.utils.GlideUtil;

import java.text.DecimalFormat;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private final List<Auction> auctions;
    private final Context context;

    public ItemAdapter(List<Auction> auctions, Context context) {
        this.auctions = auctions;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auction_item, parent, false);
        return new ItemViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Auction auction = this.auctions.get(position);
        holder.title.setText(auction.getTitle());
        holder.category.setText(auction.getCategory());
        holder.owner.setText(auction.getOwner_id() + "");
        DecimalFormat df = new DecimalFormat("###.#");
        holder.price.setText(df.format(auction.getStarting_price()) + "₺");
        GlideUtil.downloadAndShow(this.context, auction.getShowcase_photo(), holder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.auctions.size();
    }
}

