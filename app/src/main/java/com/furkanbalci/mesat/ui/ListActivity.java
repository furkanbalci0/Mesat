package com.furkanbalci.mesat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.adapter.listitem.ListItemAdapter;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.databinding.ActivityListBinding;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.auction.AuctionService;
import com.furkanbalci.mesat.utils.ButtonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private String key;
    ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(super.getLayoutInflater());
        this.setContentView(binding.getRoot());
        this.key = LocalDataManager.getString(this, "key", "test");
        this.initialize();
        ButtonUtils buttonUtils = new ButtonUtils();
        buttonUtils.setupButtons(this);
    }

    private void initialize() {

        TextView textView = this.findViewById(R.id.search_key);
        textView.setText(this.key);

        AuctionService auctionService = Service.getRetrofit().create(AuctionService.class);

        Call<List<Auction>> auctions = auctionService.findAuctionsByNameOrDescription(this.key);

        auctions.enqueue(new Callback<List<Auction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Auction>> call, @NonNull Response<List<Auction>> response) {

                List<Auction> auctions = response.body();

                ListItemAdapter adapter = new ListItemAdapter(auctions, getApplicationContext());
                binding.gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Auction>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}