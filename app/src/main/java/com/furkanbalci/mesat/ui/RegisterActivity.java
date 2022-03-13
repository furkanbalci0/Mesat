package com.furkanbalci.mesat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.furkanbalci.mesat.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initialize();
    }

    private void initialize() {

        //Login button.
        this.findViewById(R.id.register_activity_login_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
        });

    }
}