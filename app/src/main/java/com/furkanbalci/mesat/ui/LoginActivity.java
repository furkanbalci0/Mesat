package com.furkanbalci.mesat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.user.UserService;
import com.furkanbalci.mesat.utils.ButtonUtils;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.initialize();
        ButtonUtils buttonUtils = new ButtonUtils();
        buttonUtils.setupButtons(this);
    }


    private void initialize() {

        long id = LocalDataManager.getLong(this, "id", 0);

        if (id != 0) {
            System.out.println("Zaten giriş yaptınız ve bu yüzden direkt profile yönlendirildiniz.");
            Intent intent = new Intent(this, ProfileActivity.class);
            this.startActivity(intent);
            this.finish();
            return;
        }

        //Register button.
        this.findViewById(R.id.login_activity_register_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
            this.finish();
        });

        this.findViewById(R.id.login_activity_login_button).setOnClickListener(v -> {

            TextView mail = this.findViewById(R.id.editTextTextEmailAddress);
            TextView password = this.findViewById(R.id.editTextTextPassword);

            if (mail.getText() == null || password.getText() == null) {
                Toast.makeText(this, "Lütfen boş alan bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            UserService userService = Service.getRetrofit().create(UserService.class);

            Call<User> call = userService.getByMail(mail.getText().toString());

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull retrofit2.Response<User> response) {

                    //Gets user.
                    User user = response.body();

                    if (user == null) {
                        Toast.makeText(LoginActivity.this, "Böyle bir kullanıcı bulunamadı!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!user.getPassword().contentEquals(password.getText())) {
                        Toast.makeText(LoginActivity.this, "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Get android id.
                    //String android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

                    LocalDataManager.setLong(LoginActivity.this, "id", user.getID());
                    LocalDataManager.setString(LoginActivity.this, "mail", user.getMail());
                    LocalDataManager.setLong(LoginActivity.this, "last_login", System.currentTimeMillis());

                    //Send message.
                    Toast.makeText(LoginActivity.this, "Başarıyla giriş yaptınız!", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();

                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();


                }
            });
        });
    }
}