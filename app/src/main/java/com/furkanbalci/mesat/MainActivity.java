package com.furkanbalci.mesat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.furkanbalci.mesat.buttons.ButtonHandler;
import com.furkanbalci.mesat.ui.LoginActivity;
import com.furkanbalci.mesat.ui.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initialize();
    }

    @SuppressLint("ResourceType")
    private void initialize() {

        //Profile button.
        this.findViewById(R.id.footer_icon_profile).setOnClickListener(v -> {

            if (3 > 2) {
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
            } else {
                //TODO: Profile activity
            }
        });
        //Profile button.
        this.findViewById(R.id.footer_icon_home).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        });




    }

}