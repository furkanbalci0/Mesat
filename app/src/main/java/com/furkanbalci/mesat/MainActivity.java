package com.furkanbalci.mesat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanbalci.mesat.adapter.item.ItemAdapter;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.auction.AuctionService;
import com.furkanbalci.mesat.ui.ListActivity;
import com.furkanbalci.mesat.ui.LoginActivity;
import com.furkanbalci.mesat.ui.SearchActivity;
import com.furkanbalci.mesat.ui.SupportActivity;
import com.furkanbalci.mesat.utils.ButtonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        this.initialize();
        ButtonUtils buttonUtils = new ButtonUtils();
        buttonUtils.setupButtons(this);
    }

    @SuppressLint("ResourceType")
    private void initialize() {


        AuctionService auctionService = Service.getRetrofit().create(AuctionService.class);

        Call<List<Auction>> auctions = auctionService.getAll();

        auctions.enqueue(new Callback<List<Auction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Auction>> call, @NonNull Response<List<Auction>> response) {

                List<Auction> auctions = response.body();

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                ItemAdapter adapter = new ItemAdapter(auctions, getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Auction>> call, Throwable t) {

                System.out.println("İLGİNÇ");
                t.printStackTrace();
            }
        });


    }

}