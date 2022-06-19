package com.furkanbalci.mesat.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.utils.GlideUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        String id = LocalDataManager.getString(container.getContext(), "id", "0");
        long lastLogin = LocalDataManager.getLong(container.getContext(), "last_login", 0);

        ImageView profileImage = viewGroup.findViewById(R.id.profile_button_logout);

        profileImage.setOnClickListener(v -> {
            LocalDataManager.clear(container.getContext());
            Snackbar.make(container, "Başarıyla çıkış yapıldı!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.GREEN).show();

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new MainFragment());
            fragmentTransaction.commit();
        });

        User user = new Gson().fromJson(LocalDataManager.getString(container.getContext(), "object", null), User.class);

        if (id.equals("0") || user == null) {
            Snackbar.make(container, "Bir hata meydana geldi!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.RED).show();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new MainFragment());
            fragmentTransaction.commit();
            return viewGroup;
        }

        EditText mailText = viewGroup.findViewById(R.id.profile_text_mail);
        mailText.setText(user.getMail());

        EditText nameText = viewGroup.findViewById(R.id.profile_text_name);
        nameText.setText(user.getName());
        nameText.setEnabled(false);

        EditText surnameText = viewGroup.findViewById(R.id.profile_text_surname);
        surnameText.setText(user.getSurname());
        surnameText.setEnabled(false);

        EditText cityText = viewGroup.findViewById(R.id.profile_text_city);
        cityText.setText(user.getCity());

        @SuppressLint("CutPasteId") ImageView imageView = viewGroup.findViewById(R.id.profile_image_photo);

        GlideUtil.downloadAndShow(container.getContext(), user.getProfile_photo_id(), imageView);

        EditText passwordText = viewGroup.findViewById(R.id.profile_text_password);

        Button creditButton = viewGroup.findViewById(R.id.profile_button_credit);
        creditButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new CreditFragment());
            fragmentTransaction.commit();
        });

        Button updateButton = viewGroup.findViewById(R.id.profile_button_update);
        updateButton.setOnClickListener(v -> {

            String pass = passwordText.getText().toString();
            if (pass.isEmpty()){
                Toast.makeText(container.getContext(), "Lütfen önce şifrenizi giriniz!", Toast.LENGTH_LONG).show();
                return;
            }

            if (!pass.equals(user.getPassword())){
                Toast.makeText(container.getContext(), "Şifrenizi hatalı girdiniz!", Toast.LENGTH_LONG).show();
                return;
            }

            if (mailText.getText().toString().isEmpty()){
                Toast.makeText(container.getContext(), "Lütfen mail kısmını boş bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            if (cityText.getText().toString().isEmpty()){
                Toast.makeText(container.getContext(), "Lütfen şehir kısmını boş bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            user.setMail(mailText.getText().toString());
            user.setCity(cityText.getText().toString());

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("users").document(user.getId()).set(user).addOnSuccessListener(unused -> {
                LocalDataManager.setString(container.getContext(), "id", user.getId());
                LocalDataManager.setString(container.getContext(), "mail", user.getMail());
                LocalDataManager.setLong(container.getContext(), "last_login", System.currentTimeMillis());
                LocalDataManager.setString(container.getContext(), "object", new Gson().toJson(user));

                //Giriş yaptınız mesajı gönderiyoruz.
                Toast.makeText(container.getContext(), "Bilgilerinizi başarıyla güncellendi!", Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame, new ProfileFragment());
                fragmentTransaction.commit();
            }).addOnFailureListener(e -> {
                Toast.makeText(container.getContext(), "Bir hata meydana geldi!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            });


        });
        
        return viewGroup;
    }
}