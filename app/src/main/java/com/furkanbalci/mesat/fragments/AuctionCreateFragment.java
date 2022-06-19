package com.furkanbalci.mesat.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.databinding.FragmentAuctionCreateBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        EditText nameEditText = binding.inputName.getEditText();
        EditText categoryEditText = binding.inputCategory.getEditText();
        EditText priceEditText = binding.inputPrice.getEditText();
        EditText contentEditText = binding.inputExplanation.getEditText();

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameEditText.setTextColor(Color.GREEN);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nameEditText.getText().toString().length() > 100) {
                    nameEditText.setTextColor(Color.RED);
                    return;
                }

                if (nameEditText.getText().toString().length() < 3) {
                    nameEditText.setTextColor(Color.RED);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        categoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                categoryEditText.setTextColor(Color.GREEN);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (categoryEditText.getText().toString().length() > 100) {
                    categoryEditText.setTextColor(Color.RED);
                    return;
                }

                if (categoryEditText.getText().toString().length() < 3) {
                    categoryEditText.setTextColor(Color.RED);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                priceEditText.setTextColor(Color.GREEN);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (Integer.parseInt(priceEditText.getText().toString()) < 1) {
                        priceEditText.setTextColor(Color.RED);
                        return;
                    }
                } catch (Exception e) {
                    priceEditText.setTextColor(Color.RED);
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                contentEditText.setTextColor(Color.GREEN);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (contentEditText.getText().toString().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
                    contentEditText.setTextColor(Color.RED);
                    return;
                }

                if (contentEditText.getText().toString().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")) {
                    contentEditText.setTextColor(Color.RED);
                    return;
                }
                if (contentEditText.getText().toString().matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$")) {
                    contentEditText.setTextColor(Color.RED);
                    return;
                }
                if (contentEditText.getText().toString().matches("^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$")) {
                    contentEditText.setTextColor(Color.RED);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.loadButton.setOnClickListener(v -> {


            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://mesat-cc215.appspot.com");

            String name = nameEditText.getText().toString();
            String category = categoryEditText.getText().toString();
            String price = priceEditText.getText().toString();
            String content = contentEditText.getText().toString();

            if (name.matches(".*\\d.*") || !name.matches(".*[^a-zA-Z0-9].*")) {
                Toast.makeText(this.getContext(), "İsim alanında özel karakterler ve sayılar kullanmayınız!", Toast.LENGTH_LONG).show();
                return;
            }

            if (nameEditText.getText().toString().isEmpty() || categoryEditText.getText().toString().isEmpty() || priceEditText.getText().toString().isEmpty() || contentEditText.getText().toString().isEmpty()) {
                Snackbar.make(v, "Lütfen tüm alanları doldurun!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (nameEditText.getText().toString().length() < 3) {
                Snackbar.make(v, "Lütfen en az 3 karakterden oluşan bir başlık girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (category.length() < 3) {
                Snackbar.make(v, "Lütfen en az 3 karakterden oluşan bir kategori girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            int priceInt = Integer.parseInt(priceEditText.getText().toString());
            if (priceInt < 0) {
                Snackbar.make(v, "Lütfen 0'dan büyük bir fiyat girin!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
                return;
            }

            if (contentEditText.getText().toString().length() < 10) {
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
            key.put("category", categoryEditText.getText().toString());
            key.put("end_time", new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)));
            key.put("showcase_photo", imageId);
            key.put("sold", false);
            key.put("starting_price", priceInt);
            key.put("starting_time", new Date());
            key.put("title", nameEditText.getText().toString());

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