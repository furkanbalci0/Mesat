package com.furkanbalci.mesat.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.databinding.FragmentDetailsBinding;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.utils.GlideUtil;
import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private final String selectedItemId;

    public DetailsFragment(String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = FragmentDetailsBinding.inflate(super.getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CollectionReference collection = FirebaseFirestore.getInstance().collection("auctions");
        collection.whereEqualTo("id", this.selectedItemId).get().addOnSuccessListener(querySnapshot -> {
            if (querySnapshot.getDocuments().size() == 0) {
                Snackbar.make(binding.getRoot(), "No such auction", Snackbar.LENGTH_SHORT).show();
                return;
            }

            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

            this.binding.detailsTitle.setText(document.getString("title"));
            GlideUtil.downloadAndShow(inflater.getContext(), document.getString("showcase_photo"), this.binding.detailsImage);
            this.binding.detailsContent.setText(document.getString("content"));

            this.binding.detailsImage.setOnClickListener(v -> {
                Intent fullImageIntent = new Intent(container.getContext(), FullScreenImageViewActivity.class);

                Uri uri = Uri.parse(document.getString("showcase_photo"));
                System.out.println(uri);
                ArrayList<String> uriString = new ArrayList<>();
                uriString.add(uri.toString());
                fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, 0);
                fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, uriString);

                startActivity(fullImageIntent);
            });


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = db.collection("offers");

            collectionReference.whereEqualTo("auction_id", this.selectedItemId).orderBy("bid_amount", Query.Direction.DESCENDING).get().addOnSuccessListener(querySnapshot1 -> {

                DocumentSnapshot documentSnapshot = querySnapshot1.getDocuments().get(0);
                if (documentSnapshot != null) {
                    this.binding.currentBidderText.setText(documentSnapshot.getString("user_name"));
                    this.binding.currentBidderAmountText.setText(documentSnapshot.getLong("bid_amount") + "₺");
                    this.binding.minAmountToBid.setText((documentSnapshot.getLong("bid_amount") + 1) + "₺");
                }

            });

            this.binding.bidButton.setOnClickListener(v -> {

                User user = LocalDataManager.get(container.getContext(), "user_object", User.class);

                if (user == null) {
                    Snackbar.make(v, "Lütfen giriş yapınız.", Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show();
                    return;
                }

                int amount = Integer.parseInt(this.binding.minAmountToBid.getText().toString().replace("₺", ""));

                collectionReference.whereEqualTo("auction_id", this.selectedItemId).orderBy("bid_amount", Query.Direction.DESCENDING).get().addOnSuccessListener(querySnapshot1 -> {

                    long currentAmount = 0;
                    if (querySnapshot1.getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = querySnapshot1.getDocuments().get(0);
                        currentAmount = documentSnapshot.getLong("bid_amount");
                    }

                    if (amount > user.getCredit()) {
                        Snackbar.make(v, "Hesabınızda yeterli kredi yok! Lütfen kredi yükleyin.", Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show();
                        return;
                    }

                    if (amount <= currentAmount) {
                        Snackbar.make(v, "En yüksek fiyatdan daha yüksek bir fiyat veriniz.", Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show();
                        return;
                    }


                    if (amount > currentAmount) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("auction_id", this.selectedItemId);
                        map.put("bid_amount", amount);
                        map.put("create_date", new Date());
                        map.put("user_id", user.getId());
                        map.put("user_name", LocalDataManager.getString(container.getContext(), "name", "Furkan"));
                        collectionReference.add(map);

                        user.setCredit(user.getCredit() - amount);
                        LocalDataManager.put(container.getContext(), "user_object", user);
                        db.collection("users").document(user.getId()).set(user);


                        Snackbar.make(container, "Teklif kaydedildi!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.GREEN).show();

                        final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        // R.id.fragment_layout is your fragment container view
                        ft.replace(R.id.frame, new DetailsFragment(this.selectedItemId));
                        ft.commit();

                        //TODO: update the amount in the local data manager
                    }
                });
            });

        });


        return this.binding.getRoot();
    }
}