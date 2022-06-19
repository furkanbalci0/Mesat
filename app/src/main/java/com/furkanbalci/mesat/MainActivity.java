package com.furkanbalci.mesat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanbalci.mesat.adapter.item.ItemAdapter;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.fragments.AuctionCreateFragment;
import com.furkanbalci.mesat.fragments.MainFragment;
import com.furkanbalci.mesat.models.auction.Auction;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.auction.AuctionService;
import com.furkanbalci.mesat.ui.ListActivity;
import com.furkanbalci.mesat.utils.FooterButtonUtils;
import com.furkanbalci.mesat.utils.HeaderButtonUtils;
import com.furkanbalci.mesat.utils.MenuButtonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
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

    @SuppressLint("ResourceType")
    private void initialize() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame, new MainFragment());
        fragmentTransaction.commit();

    }

}