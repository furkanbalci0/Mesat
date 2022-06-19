package com.furkanbalci.mesat.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.List;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        String id = LocalDataManager.getString(container.getContext(), "id", null);
        if (id != null) {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new ProfileFragment());
            fragmentTransaction.commit();
            return viewGroup;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Login button.
        viewGroup.findViewById(R.id.login_activity_register_button).setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new RegisterFragment());
            fragmentTransaction.commit();
        });

        //Giriş yap butonuna tıkladığında çalışacaklar.
        viewGroup.findViewById(R.id.login_activity_login_button).setOnClickListener(v -> {

            TextView mail = viewGroup.findViewById(R.id.editTextTextEmailAddress);
            TextView password = viewGroup.findViewById(R.id.editTextTextPassword);

            //Boş alan bırakmış mı kontrolü.
            if (mail.getText().toString().equals("") || password.getText().toString().equals("")) {
                Toast.makeText(viewGroup.getContext(), "Lütfen boş alan bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }


            db.collection("users")
                    .limit(1)
                    .whereEqualTo("mail", mail.getText().toString())
                    .whereEqualTo("password", password.getText().toString())
                    .get()
                    .addOnCompleteListener(task -> {

                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        if (documents.size() == 0) {
                            Toast.makeText(viewGroup.getContext(), "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        //İlk veriyi çekiyoruz.
                        DocumentSnapshot document = documents.get(0);
                        User user = new User(
                                document.getId(),
                                document.getString("name"),
                                document.getString("surname"),
                                document.getString("mail"),
                                document.getString("password"),
                                document.getString("city"),
                                document.getString("phone"),
                                document.getLong("credit"));

                        //içindeki şifre aynı mı diye kontrol ediyoruz.
                        if (!user.getPassword().contentEquals(password.getText().toString())) {
                            Toast.makeText(viewGroup.getContext(), "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        //Kullanıcı giriş yaptığında, bir daha giriş yapmasına gerek kalmasın
                        //diye verilerini önbelleğe kaydediyoruz.
                        LocalDataManager.setString(viewGroup.getContext(), "id", user.getId());
                        LocalDataManager.setString(viewGroup.getContext(), "mail", user.getMail());
                        LocalDataManager.setString(viewGroup.getContext(), "name", user.getName());
                        LocalDataManager.setLong(viewGroup.getContext(), "last_login", System.currentTimeMillis());
                        LocalDataManager.setString(viewGroup.getContext(), "object", new Gson().toJson(user));

                        LocalDataManager.put(viewGroup.getContext(), "user_object", user);

                        //Send message.
                        Snackbar.make(viewGroup, "Başarıyla giriş yaptınız!", Snackbar.LENGTH_LONG).show();

                        //Giriş yaptığında ana ekrana yönlendiriyoruz ve bu ekranı bitiriyoruz.
                        Intent intent = new Intent(viewGroup.getContext(), MainActivity.class);
                        viewGroup.getContext().startActivity(intent);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(viewGroup.getContext(), "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                    });

        });


        return viewGroup;
    }
}