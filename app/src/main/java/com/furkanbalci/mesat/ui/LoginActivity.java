package com.furkanbalci.mesat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.furkanbalci.mesat.R;

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
        });

    }
}