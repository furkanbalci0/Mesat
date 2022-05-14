package com.furkanbalci.mesat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.user.UserService;
import com.furkanbalci.mesat.utils.FooterButtonUtils;
import com.furkanbalci.mesat.utils.GlideUtil;
import com.furkanbalci.mesat.utils.HeaderButtonUtils;
import com.furkanbalci.mesat.utils.MenuButtonUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);
        FooterButtonUtils footerButtonUtils = new FooterButtonUtils();
        footerButtonUtils.setupButtons(this);
        HeaderButtonUtils headerButtonUtils = new HeaderButtonUtils();
        headerButtonUtils.setupButtons(this);
        MenuButtonUtils menuButtonUtils = new MenuButtonUtils();
        menuButtonUtils.setupButtons(this);
        View include = this.findViewById(R.id.menu_include);
        include.setVisibility(View.INVISIBLE);
        this.initialize();
    }

    private void initialize() {

        String id = LocalDataManager.getString(this, "id", "0");
        long lastLogin = LocalDataManager.getLong(this, "last_login", 0);

        ImageView profileImage = this.findViewById(R.id.profile_button_logout);

        profileImage.setOnClickListener(v -> {
            LocalDataManager.clear(this);
            Toast.makeText(this, "Başarıyla çıkış yapıldı!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
        });

        User user = new Gson().fromJson(LocalDataManager.getString(this, "object", null), User.class);
        System.out.println("***************************************");
        System.out.println(id);
        System.out.println(lastLogin);
        System.out.println(user);
        if (id.equals("0") || user == null) {
            Toast.makeText(this, "Bir hata meydana geldi!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
            return;
        }

        EditText mailText = this.findViewById(R.id.profile_text_mail);
        mailText.setText(user.getMail());

        EditText nameText = this.findViewById(R.id.profile_text_name);
        nameText.setText(user.getName());
        nameText.setEnabled(false);

        EditText surnameText = this.findViewById(R.id.profile_text_surname);
        surnameText.setText(user.getSurname());
        surnameText.setEnabled(false);

        EditText cityText = this.findViewById(R.id.profile_text_city);
        cityText.setText(user.getCity());

        @SuppressLint("CutPasteId") ImageView imageView = this.findViewById(R.id.profile_image_photo);

        GlideUtil.downloadAndShow(this, user.getProfile_photo_id(), imageView);

        EditText passwordText = this.findViewById(R.id.profile_text_password);

        Button button = this.findViewById(R.id.profile_button_update);
        button.setOnClickListener(v -> {

            String pass = passwordText.getText().toString();
            if (pass.isEmpty()){
                Toast.makeText(this, "Lütfen önce şifrenizi giriniz!", Toast.LENGTH_LONG).show();
                return;
            }

            if (!pass.equals(user.getPassword())){
                Toast.makeText(this, "Şifrenizi hatalı girdiniz!", Toast.LENGTH_LONG).show();
                return;
            }

            if (mailText.getText().toString().isEmpty()){
                Toast.makeText(this, "Lütfen mail kısmını boş bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            if (cityText.getText().toString().isEmpty()){
                Toast.makeText(this, "Lütfen şehir kısmını boş bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            user.setMail(mailText.getText().toString());
            user.setCity(cityText.getText().toString());

            //Yeni bir API servisi oluşturuyoruz.
            UserService userService = Service.getRetrofit().create(UserService.class);

            //Kayıt isteğine API'ye gönderiyoruz.
            userService.update(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull retrofit2.Response<User> response) {

                    //TODO: Mail alınmış mı kontrolü yap.

                    LocalDataManager.setString(ProfileActivity.this, "id", user.getId());
                    LocalDataManager.setString(ProfileActivity.this, "mail", user.getMail());
                    LocalDataManager.setLong(ProfileActivity.this, "last_login", System.currentTimeMillis());
                    LocalDataManager.setString(ProfileActivity.this, "object", new Gson().toJson(user));

                    //Giriş yaptınız mesajı gönderiyoruz.
                    Toast.makeText(ProfileActivity.this, "Bilgilerinizi başarıyla güncellendi!", Toast.LENGTH_LONG).show();
                    ProfileActivity.this.recreate();
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                    //Kayıt esnasında API'den farklı bir istek gelirse hata mesajı gönderiyoruz.
                    Toast.makeText(ProfileActivity.this, "Bir hata meydana geldi!", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });

            

        });

    }
}