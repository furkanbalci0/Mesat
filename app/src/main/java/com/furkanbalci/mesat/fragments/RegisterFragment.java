package com.furkanbalci.mesat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);

        String id = LocalDataManager.getString(container.getContext(), "id", null);
        if (id != null){
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new ProfileFragment());
            fragmentTransaction.commit();
            return viewGroup;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //Login button.
        viewGroup.findViewById(R.id.register_activity_login_button2).setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new LoginFragment());
            fragmentTransaction.commit();
        });

        viewGroup.findViewById(R.id.register_activity_register_button3).setOnClickListener(v -> {

            TextView name = viewGroup.findViewById(R.id.editTextTextPersonName);
            TextView surname = viewGroup.findViewById(R.id.editTextTextPersonSurName);
            TextView phone = viewGroup.findViewById(R.id.editTextTextPersonPhoneNumber);
            TextView mail = viewGroup.findViewById(R.id.editTextTextPersonEMail);
            TextView pass = viewGroup.findViewById(R.id.editTextTextPersonPassword);
            CheckBox register_check_box = viewGroup.findViewById(R.id.register_check_box);

            //Bütün alanlar doldurulmuş mu kontrolü.
            if (name.getText().toString().isEmpty() || surname.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || register_check_box.getText().toString().isEmpty()) {
                Toast.makeText(viewGroup.getContext(), "Lütfen boş alan bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            //TextViewlerin içinde yazan veriler ile yeni bir user oluşturuyoruz.
            User user = new User("0", name.getText().toString(), surname.getText().toString(), mail.getText().toString(), pass.getText().toString(), "Bilinmiyor", phone.getText().toString());

            db.collection("users").add(user).addOnCompleteListener(task -> {
                LocalDataManager.setString(viewGroup.getContext(), "id", user.getId());
                LocalDataManager.setString(viewGroup.getContext(), "mail", user.getMail());
                LocalDataManager.setString(viewGroup.getContext(), "name", user.getName());
                LocalDataManager.setLong(viewGroup.getContext(), "last_login", System.currentTimeMillis());
                LocalDataManager.setString(viewGroup.getContext(), "object", new Gson().toJson(user));

                //Kullanıcıyı ana ekrana aktarıyoruz.
                //todo: fragment ile ana ekrana aktarma

                //Giriş yaptınız mesajı gönderiyoruz.
                Toast.makeText(viewGroup.getContext(), "Başarıyla kayıt oldunuz...", Toast.LENGTH_LONG).show();
            }).addOnFailureListener(e -> {

                Toast.makeText(viewGroup.getContext(), "Bir hata meydana geldi!", Toast.LENGTH_LONG).show();
            });


        });

        return viewGroup;
    }
}