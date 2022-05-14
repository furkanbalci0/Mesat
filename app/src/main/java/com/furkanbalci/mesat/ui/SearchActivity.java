package com.furkanbalci.mesat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.utils.FooterButtonUtils;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_search);
        this.initialize();

        //Header olmadığı için sadece footer bölgesinin butonlarını aktif ediyoruz.
        FooterButtonUtils footerButtonUtils = new FooterButtonUtils();
        footerButtonUtils.setupButtons(this);
    }

    private void initialize() {

        ImageView imageView = this.findViewById(R.id.search_page_search_button);

        //Butona tıklandığında çalışacaklar.
        imageView.setOnClickListener(v -> {

            EditText editText = this.findViewById(R.id.edit_search_text);
            String key = editText.getText().toString();

            //Eğer ki bir kelime yazılmışsa, yani boş değilse.
            if (key.isEmpty()) {
                //Ekranda mesaj gösterme.
                Toast.makeText(this, "Lütfen bir kelime girin!", Toast.LENGTH_LONG).show();
                return;
            }

            //Aratılan kelimeyi hafızaya kaydediyor ve yeni ekrana aktarıyor.
            Intent intent = new Intent(this, ListActivity.class);
            LocalDataManager.setString(this, "key", key);
            this.startActivity(intent);

        });
    }
}