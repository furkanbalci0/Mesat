package com.furkanbalci.mesat.adapter.listitem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.utils.GlideUtil;

import java.text.DecimalFormat;
import java.util.List;

public class ListItemAdapter extends BaseAdapter {

    private final List<Auction> auctions;
    private final Context context;
    private LayoutInflater layoutInflater;

    public ListItemAdapter(List<Auction> auctions, Context context) {
        this.auctions = auctions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.auctions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.auction_item, null);
        }

        TextView title = convertView.findViewById(R.id.item_title);
        TextView category = convertView.findViewById(R.id.item_category);
        TextView owner = convertView.findViewById(R.id.item_owner_name);
        TextView price = convertView.findViewById(R.id.item_price);
        ImageView imageView = convertView.findViewById(R.id.item_image);

        Auction auction = this.auctions.get(position);
        title.setText(auction.getTitle());
        category.setText(auction.getCategory());
        owner.setText(auction.getOwner_id() + "");
        DecimalFormat df = new DecimalFormat("###.#");
        price.setText(df.format(auction.getStarting_price()) + "₺");
        GlideUtil.downloadAndShow(this.context, auction.getShowcase_photo(), imageView);
        return convertView;
    }
}
