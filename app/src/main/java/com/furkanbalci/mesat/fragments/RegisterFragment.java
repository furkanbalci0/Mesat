package com.furkanbalci.mesat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.utils.StringUtil;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);

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
        viewGroup.findViewById(R.id.register_activity_login_button2).setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new LoginFragment());
            fragmentTransaction.commit();
        });

        viewGroup.findViewById(R.id.register_activity_register_button3).setOnClickListener(v -> {

            TextView nameView = viewGroup.findViewById(R.id.editTextTextPersonName);
            TextView surnameView = viewGroup.findViewById(R.id.editTextTextPersonSurName);
            TextView phoneView = viewGroup.findViewById(R.id.editTextTextPersonPhoneNumber);
            TextView mailView = viewGroup.findViewById(R.id.editTextTextPersonEMail);
            TextView passView = viewGroup.findViewById(R.id.editTextTextPersonPassword);
            CheckBox register_check_boxView = viewGroup.findViewById(R.id.register_check_box);

            //B??t??n alanlar doldurulmu?? mu kontrol??.
            if (nameView.getText().toString().isEmpty() || surnameView.getText().toString().isEmpty() || phoneView.getText().toString().isEmpty() || mailView.getText().toString().isEmpty() || passView.getText().toString().isEmpty() || register_check_boxView.getText().toString().isEmpty()) {
                Toast.makeText(viewGroup.getContext(), "L??tfen bo?? alan b??rakmay??n!", Toast.LENGTH_LONG).show();
                return;
            }

            String name = nameView.getText().toString();
            String surname = surnameView.getText().toString();
            String phone = phoneView.getText().toString();
            String mail = mailView.getText().toString();
            String pass = passView.getText().toString();

            if (name.matches(".*\\d.*") || !name.matches(".*[^a-zA-Z0-9].*")){
                Toast.makeText(viewGroup.getContext(), "??sim alan??nda ??zel karakterler ve say??lar kullanmay??n??z!", Toast.LENGTH_LONG).show();
                return;
            }

            if (name.length() < 2){
                Toast.makeText(viewGroup.getContext(), "??sim 1 harf olamaz.", Toast.LENGTH_LONG).show();
                return;
            }

            if (surname.matches(".*\\d.*") || !surname.matches(".*[^a-zA-Z0-9].*")){
                Toast.makeText(viewGroup.getContext(), "Soyisim alan??nda ??zel karakterler ve say??lar kullanmay??n??z!", Toast.LENGTH_LONG).show();
                return;
            }

            if (surname.length() < 2){
                Toast.makeText(viewGroup.getContext(), "Soyisim 1 harf olamaz.", Toast.LENGTH_LONG).show();
                return;
            }

            if (phone.length() != 10){
                Toast.makeText(viewGroup.getContext(), "Telefon numaras?? 10 haneli olmal??d??r.", Toast.LENGTH_LONG).show();
                return;
            }

            if (phone.startsWith("0")){
                Toast.makeText(viewGroup.getContext(), "Telefon numaras?? 0 ile ba??lamaz.", Toast.LENGTH_LONG).show();
                return;
            }

            //Mail check @
            if (!mail.contains("@")){
                Toast.makeText(viewGroup.getContext(), "Mail adresi ge??ersiz.", Toast.LENGTH_LONG).show();
                return;
            }

            if (!StringUtil.checkString(pass)){
                Toast.makeText(viewGroup.getContext(), "??ifre en az 1 k??????k harf, 1 b??y??k harf ve 1 rakam i??ermelidir.", Toast.LENGTH_LONG).show();
                return;
            }

            if (!register_check_boxView.isChecked()){
                Toast.makeText(viewGroup.getContext(), "Kullan??c?? s??zle??mesini okudu??unuzu onaylay??n??z.", Toast.LENGTH_LONG).show();
                return;
            }

            //TextViewlerin i??inde yazan veriler ile yeni bir user olu??turuyoruz.
            User user = new User("0", name, surname, mail, pass, "Bilinmiyor", phone);

            db.collection("users").add(user).addOnCompleteListener(task -> {
                LocalDataManager.setString(viewGroup.getContext(), "id", user.getId());
                LocalDataManager.setString(viewGroup.getContext(), "mail", user.getMail());
                LocalDataManager.setString(viewGroup.getContext(), "name", user.getName());
                LocalDataManager.setLong(viewGroup.getContext(), "last_login", System.currentTimeMillis());
                LocalDataManager.setString(viewGroup.getContext(), "object", new Gson().toJson(user));

                LocalDataManager.put(viewGroup.getContext(), "user_object", user);

                //Kullan??c??y?? ana ekrana aktar??yoruz.
                //todo: fragment ile ana ekrana aktarma

                //Giri?? yapt??n??z mesaj?? g??nderiyoruz.
                Toast.makeText(viewGroup.getContext(), "Ba??ar??yla kay??t oldunuz...", Toast.LENGTH_LONG).show();
            }).addOnFailureListener(e -> {

                Toast.makeText(viewGroup.getContext(), "Bir hata meydana geldi!", Toast.LENGTH_LONG).show();
            });


        });

        return viewGroup;
    }

}