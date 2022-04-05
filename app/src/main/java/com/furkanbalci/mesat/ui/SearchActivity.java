package com.furkanbalci.mesat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.utils.ButtonUtils;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_search);
        this.initialize();
        ButtonUtils buttonUtils = new ButtonUtils();
        buttonUtils.setupButtons(this);
    }

    private void initialize() {

        ImageView imageView = this.findViewById(R.id.search_page_search_button);
        imageView.setOnClickListener(v -> {

            EditText editText = this.findViewById(R.id.edit_search_text);
            String key = editText.getText().toString();

            if (key.isEmpty()) {
                Toast.makeText(this, "Lütfen bir kelime girin!", Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(this, ListActivity.class);
            LocalDataManager.setString(this, "key", key);
            this.startActivity(intent);

        });
    }

}