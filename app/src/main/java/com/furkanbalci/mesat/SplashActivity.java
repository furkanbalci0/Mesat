package com.furkanbalci.mesat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.furkanbalci.mesat.utils.AlertUtil;
import com.furkanbalci.mesat.utils.NetworkUtil;

/**
 * Splash activity class.
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.initialize();
    }

    /**
     * Initialize method.
     */
    private void initialize() {

        //Create new handler.
        final Handler handler = new Handler();

        if (!NetworkUtil.checkConnection(this)) {
            AlertUtil.showAlert(SplashActivity.this,
                    R.string.no_internet_connection_title,
                    R.string.no_internet_connection_content,
                    new AlertUtil.DialogListener(R.string.no_internet_connection_negative_button, (dialog, which) -> this.finish()),
                    new AlertUtil.DialogListener(R.string.no_internet_connection_positive_button, (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS))));
            return;
        }

        //Starting the handler.
        handler.postDelayed(() -> {

            //Starting main activity.
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

            //Stopped this activity.
            SplashActivity.this.finish();
        }, 3000);

    }

}