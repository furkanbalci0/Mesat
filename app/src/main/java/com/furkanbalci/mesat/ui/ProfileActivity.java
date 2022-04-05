package com.furkanbalci.mesat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.utils.ButtonUtils;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);
        ButtonUtils buttonUtils = new ButtonUtils();
        buttonUtils.setupButtons(this);
        this.initialize();
    }

    private void initialize() {
        Button button = this.findViewById(R.id.logout_button);

        button.setOnClickListener(v -> {
            LocalDataManager.clear(this);
        });
    }
}