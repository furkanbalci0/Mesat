package com.furkanbalci.mesat.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SupportFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_support, container, false);

        EditText editText = viewGroup.findViewById(R.id.support_activity_email3);
        //Kullanıcı eğer ki giriş yapmışsa, mail adresini alıyor.
        String mail = LocalDataManager.getString(viewGroup.getContext(), "mail", null);

        //Kullanıcının mail adresi textbox'un içerisine yazılıyor.
        editText.setText(mail);
        editText.setEnabled(false);

        //Eğer ki kullanıcı giriş yapmamışsa buton de-aktif oluyor.
        Button button = viewGroup.findViewById(R.id.support_activity_send_button3);
        if (mail == null) {
            button.setEnabled(false);
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView title = viewGroup.findViewById(R.id.support_activity_title3);
        TextView message = viewGroup.findViewById(R.id.support_activity_message3);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                title.setTextColor(Color.GREEN);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (title.getText().toString().length() < 5){
                    title.setTextColor(Color.RED);
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                message.setTextColor(Color.GREEN);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (message.getText().toString().length() > 5000){
                    message.setTextColor(Color.RED);
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(v -> {

            //Ekstra güvenlik.
            if (mail == null) {
                button.setEnabled(false);
                Toast.makeText(viewGroup.getContext(), "Lütfen önce giriş yapın!", Toast.LENGTH_LONG).show();
                return;
            }

            if (title.getText().toString().isEmpty()) {
                Toast.makeText(viewGroup.getContext(), "Lütfen mail adresinizi girin!", Toast.LENGTH_LONG).show();
                title.setTextColor(Color.RED);
                return;
            }

            if (message.getText().toString().isEmpty()) {
                Toast.makeText(viewGroup.getContext(), "Lütfen mesajınızı girin!", Toast.LENGTH_LONG).show();
                message.setTextColor(Color.RED);
                return;
            }

            Map<String, Object> object = new HashMap<>();
            object.put("content_title", title.getText().toString());
            object.put("content_message", message.getText().toString());
            object.put("sender_id", LocalDataManager.getString(viewGroup.getContext(), "id", "0"));

            db.collection("supports").add(object)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(viewGroup.getContext(), "Destek talebiniz gönderildi!", Toast.LENGTH_LONG).show();
                        this.onCreateView(inflater, container, savedInstanceState);

                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.frame, new MainFragment());
                        fragmentTransaction.commit();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(viewGroup.getContext(), "Talep gönderilemedi! -> " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        return viewGroup;
    }
}