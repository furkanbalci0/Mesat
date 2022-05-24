package com.furkanbalci.mesat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.adapter.item.ItemAdapter;
import com.furkanbalci.mesat.adapter.listitem.ListItemAdapter;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.databinding.ActivityListBinding;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.auction.AuctionService;
import com.furkanbalci.mesat.utils.FooterButtonUtils;
import com.furkanbalci.mesat.utils.HeaderButtonUtils;
import com.furkanbalci.mesat.utils.MenuButtonUtils;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
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

        //Local datadan arama kelimesini alıyor.
        this.key = LocalDataManager.getString(this, "key", "test");
        this.initialize();

        //Footer ve header'da bulunan butonların işlevli hale gelmesini sağlıyor.
        FooterButtonUtils footerButtonUtils = new FooterButtonUtils();
        footerButtonUtils.setupButtons(this);
        HeaderButtonUtils headerButtonUtils = new HeaderButtonUtils();
        headerButtonUtils.setupButtons(this);
        MenuButtonUtils menuButtonUtils = new MenuButtonUtils();
        menuButtonUtils.setupButtons(this);

        //Menünün ilk başta görünmez olmasını sağlamak için invisible yapıyoruz.
        View include = this.findViewById(R.id.menu_include);
        include.setVisibility(View.INVISIBLE);
    }

    private void initialize() {

        TextView textView = this.findViewById(R.id.search_key);
        textView.setText(this.key);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collection = db.collection("auctions");

        collection.whereEqualTo("category", this.key).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                List<Auction> auctions = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Auction auction = new Auction(
                            document.getString("id"),
                            document.getDate("starting_time"),
                            document.getLong("owner_id"),
                            document.getLong("starting_price"),
                            document.getBoolean("sold"),
                            document.getString("title"),
                            document.getString("category"),
                            document.getDate("end_time"),
                            document.getString("showcase_photo"),
                            document.getString("content"));
                    auctions.add(auction);
                }

                //Liste yapısını çağırıyoruz.
                ListItemAdapter adapter = new ListItemAdapter(auctions, getApplicationContext());
                for (int i = 0; i < adapter.getCount(); i++) {
                    Object item = adapter.getItem(i);
                }

                //Adaptere kayıt ediyor.
                binding.gridView.setAdapter(adapter);
            }
        });
    }
}