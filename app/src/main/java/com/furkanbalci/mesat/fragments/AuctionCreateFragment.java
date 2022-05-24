package com.furkanbalci.mesat.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.databinding.FragmentAuctionCreateBinding;
import com.furkanbalci.mesat.utils.GlideUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AuctionCreateFragment extends Fragment {

    private ActivityResultLauncher<String> takePhoto;
    private FragmentAuctionCreateBinding binding;
    private ImageView lastClickedImage;
    private Map<String, Uri> imageUris = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAuctionCreateBinding.inflate(super.getLayoutInflater());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        this.takePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result == null) {
                Snackbar.make(container, "Lütfen bir resim seçin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }
            lastClickedImage.setImageURI(result);
            imageUris.put(lastClickedImage == binding.imageView1 ? "0" : lastClickedImage == binding.imageView2 ? "1" : lastClickedImage == binding.imageView3 ? "2" : "3", result);

            Snackbar.make(container, "Resim başarıyla seçildi!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.GREEN).show();

        });

        for (ImageView imageView : Arrays.asList(binding.imageView1, binding.imageView2, binding.imageView3, binding.imageView4)) {
            imageView.setOnClickListener(v -> {
                takePhoto.launch("image/*");
                lastClickedImage = imageView;

            });
        }

        binding.loadButton.setOnClickListener(v -> {

            EditText name = binding.inputName.getEditText();
            EditText category = binding.inputCategory.getEditText();
            EditText price = binding.inputPrice.getEditText();
            EditText content = binding.inputExplanation.getEditText();

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://mesat-cc215.appspot.com");


            if (name == null || category == null || price == null || content == null) {
                Snackbar.make(v, "Lütfen tüm alanları doldurun!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (name.getText().toString().isEmpty() || category.getText().toString().isEmpty() || price.getText().toString().isEmpty() || content.getText().toString().isEmpty()) {
                Snackbar.make(v, "Lütfen tüm alanları doldurun!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (name.getText().toString().length() < 3) {
                Snackbar.make(v, "Lütfen en az 3 karakterden oluşan bir başlık girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (category.getText().toString().length() < 3) {
                Snackbar.make(v, "Lütfen en az 3 karakterden oluşan bir kategori girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            int priceInt = Integer.parseInt(price.getText().toString());
            if (priceInt < 0) {
                Snackbar.make(v, "Lütfen 0'dan büyük bir fiyat girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (content.getText().toString().length() < 10) {
                Snackbar.make(v, "Lütfen en az 10 karakterden oluşan bir açıklama girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (lastClickedImage == null) {
                Snackbar.make(v, "Lütfen en az bir resim seçin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }


            String imageId = UUID.randomUUID().toString();
            StorageReference mountainsRef = storageRef.child("images/" + imageId);
            mountainsRef.putFile(Objects.requireNonNull(imageUris.get("0")));


            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference collection = firebaseFirestore.collection("auctions");

            Map<String, Object> key = new HashMap<>();
            key.put("category", category.getText().toString());
            key.put("end_time", new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)));
            key.put("showcase_photo", imageId);
            key.put("sold", false);
            key.put("starting_price", priceInt);
            key.put("starting_time", new Date());
            key.put("title", name.getText().toString());

            collection.add(key).addOnSuccessListener(documentReference -> {
                Snackbar.make(v, "İhale başarıyla oluşturuldu!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.GREEN).show();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame, new MainFragment());
                fragmentTransaction.commit();
            });

        });


        return binding.getRoot();
    }

}