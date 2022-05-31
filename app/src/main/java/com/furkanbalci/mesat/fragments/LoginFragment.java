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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

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
            if (mail.getText() == null || password.getText() == null) {
                Toast.makeText(viewGroup.getContext(), "Lütfen boş alan bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }


            db.collection("users")
                    .limit(1)
                    .whereEqualTo("mail", mail.getText().toString())
                    .whereEqualTo("password", password.getText().toString())
                    .get()
                    .addOnCompleteListener(task -> {

                        //İlk veriyi çekiyoruz.
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        User user = new User(
                                document.getId(),
                                document.getString("name"),
                                document.getString("surname"),
                                document.getString("mail"),
                                document.getString("password"),
                                document.getString("city"),
                                document.getString("phone"));

                        //içindeki şifre aynı mı diye kontrol ediyoruz.
                        if (!user.getPassword().contentEquals(password.getText().toString())) {
                            Toast.makeText(viewGroup.getContext(), "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        //Kullanıcı giriş yaptığında, bir daha giriş yapmasına gerek kalmasın
                        //diye verilerini önbelleğe kaydediyoruz.
                        System.out.println("DEBUG: 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        LocalDataManager.setString(viewGroup.getContext(), "id", user.getId());
                        LocalDataManager.setString(viewGroup.getContext(), "mail", user.getMail());
                        LocalDataManager.setString(viewGroup.getContext(), "name", user.getName());
                        LocalDataManager.setLong(viewGroup.getContext(), "last_login", System.currentTimeMillis());
                        LocalDataManager.setString(viewGroup.getContext(), "object", new Gson().toJson(user));

                        System.out.println("DEBUG: 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        System.out.println(LocalDataManager.getString(viewGroup.getContext(), "id", "-1"));
                        System.out.println(LocalDataManager.getLong(viewGroup.getContext(), "last_login", -1));

                        //Send message.
                        Toast.makeText(viewGroup.getContext(), "Başarıyla giriş yaptınız!", Toast.LENGTH_LONG).show();

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