package com.furkanbalci.mesat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.user.UserService;
import com.furkanbalci.mesat.utils.ButtonUtils;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        //Login button.
        this.findViewById(R.id.register_activity_login_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
        });

        this.findViewById(R.id.register_activity_register_button).setOnClickListener(v -> {

            TextView name = this.findViewById(R.id.editTextTextPersonName);
            TextView surname = this.findViewById(R.id.editTextTextPersonSurName);
            TextView phone = this.findViewById(R.id.editTextTextPersonPhoneNumber);
            TextView mail = this.findViewById(R.id.editTextTextPersonEMail);
            TextView pass = this.findViewById(R.id.editTextTextPersonPassword);
            CheckBox register_check_box = this.findViewById(R.id.register_check_box);

            if (name.getText().toString().isEmpty() || surname.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || register_check_box.getText().toString().isEmpty()) {
                Toast.makeText(this, "Lütfen boş alan bırakmayın!", Toast.LENGTH_LONG).show();
                return;
            }

            User user = new User(0, name.getText().toString(), surname.getText().toString(), mail.getText().toString(), phone.getText().toString(), pass.getText().toString());

            UserService userService = Service.getRetrofit().create(UserService.class);

            userService.register(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull retrofit2.Response<User> response) {

                    System.out.println("TEST 1");
                    if (response.message().equals("MAIL_OR_PHONE_ALREADY_TAKEN")) {
                        Toast.makeText(RegisterActivity.this, "Bu e-posta veya telefon ile zaten kayıt olunmuş.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    RegisterActivity.this.startActivity(intent);
                    RegisterActivity.this.finish();
                    Toast.makeText(RegisterActivity.this, "Başarıyla kayıt oldunuz...", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                    System.out.println("TEST 2");
                    Toast.makeText(RegisterActivity.this, "Bir hata meydana geldi!", Toast.LENGTH_LONG).show();
                    t.printStackTrace();

                }
            });


        });
    }
}