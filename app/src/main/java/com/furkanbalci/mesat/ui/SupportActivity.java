package com.furkanbalci.mesat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.models.support.Support;
import com.furkanbalci.mesat.models.user.User;
import com.furkanbalci.mesat.network.Service;
import com.furkanbalci.mesat.network.support.SupportService;
import com.furkanbalci.mesat.network.user.UserService;
import com.furkanbalci.mesat.utils.ButtonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        this.initialize();
        ButtonUtils buttonUtils = new ButtonUtils();
        buttonUtils.setupButtons(this);
    }

    private void initialize() {

        EditText editText = this.findViewById(R.id.support_activity_email);
        String mail = LocalDataManager.getString(this, "mail", null);
        editText.setText(mail);
        editText.setEnabled(false);

        Button button = this.findViewById(R.id.support_activity_send_button);
        if (mail == null) {
            button.setEnabled(false);
        }

        button.setOnClickListener(v -> {

            if (mail == null) {
                button.setEnabled(false);
                Toast.makeText(this, "Lütfen önce giriş yapın!", Toast.LENGTH_LONG).show();
                return;
            }

            TextView title = this.findViewById(R.id.support_activity_title);
            TextView message = this.findViewById(R.id.support_activity_message);

            Support support = new Support(0, LocalDataManager.getLong(this, "id", 0), title.getText().toString(), message.getText().toString());
            SupportService supportService = Service.getRetrofit().create(SupportService.class);

            supportService.add(support).enqueue(new Callback<Support>() {
                @Override
                public void onResponse(Call<Support> call, Response<Support> response) {
                    Toast.makeText(SupportActivity.this, "Destek talebiniz gönderildi!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Support> call, Throwable t) {
                    Toast.makeText(SupportActivity.this, "Talep gönderilemedi!", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}