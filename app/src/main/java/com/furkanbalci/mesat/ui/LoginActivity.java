package com.furkanbalci.mesat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.database.DatabaseHandler;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.models.user.UserHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initialize();
    }

    private void initialize() {

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

            ResultSet resultSet = DatabaseHandler.getDatabase().selectQuery("SELECT * FROM users WHERE mail = '" + mail.getText() + "' OR phone = '" + mail.getText() + "'");
            try {
                if (resultSet == null) {
                    Toast.makeText(this, "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                    return;
                }

                //TODO: SHA-256 olarak değiştir.

                if (!resultSet.getString("password").contentEquals(password.getText())) {
                    Toast.makeText(this, "Lütfen kullanıcı adınızı veya şifrenizi kontrol ediniz!", Toast.LENGTH_LONG).show();
                    return;
                }

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                //Creates new user.
                User user = new User(id, name, surname);

                //Added the user to list of user handler.
                UserHandler.getUsers().put(id, user);

                //Send message.
                Toast.makeText(this, "Başarıyla giriş yaptınız!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                this.finish();

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });
    }
}