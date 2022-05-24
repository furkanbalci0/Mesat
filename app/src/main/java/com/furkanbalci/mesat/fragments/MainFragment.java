package com.furkanbalci.mesat.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.adapter.item.ItemAdapter;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.ui.ListActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        //Support button.
        viewGroup.findViewById(R.id.main_activity_car_button).setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), ListActivity.class);
            LocalDataManager.setString(viewGroup.getContext(), "key", "araba");
            this.startActivity(intent);
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collection = db.collection("auctions");

        collection.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                List<Auction> auctions = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Auction auction = new Auction(
                            document.getString("id"),
                            document.getDate("starting_time"),
                            document.getLong("owner_id") == null ? 0 : document.getLong("owner_id"),
                            document.getLong("starting_price") == null ? 0 : document.getLong("starting_price"),
                            document.getBoolean("sold") == null ? false : document.getBoolean("sold"),
                            document.getString("title"),
                            document.getString("category"),
                            document.getDate("end_time"),
                            document.getString("showcase_photo"),
                            document.getString("content"));
                    auctions.add(auction);
                }

                //Liste yapısını çağırıyoruz.
                RecyclerView recyclerView = viewGroup.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext(), LinearLayoutManager.HORIZONTAL, false));
                ItemAdapter adapter = new ItemAdapter(auctions, viewGroup.getContext());

                //Verileri listeye yerleştiriyoruz.
                recyclerView.setAdapter(adapter);

            }
        });


        return viewGroup;
    }
}