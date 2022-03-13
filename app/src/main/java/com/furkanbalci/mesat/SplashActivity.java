package com.furkanbalci.mesat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

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

        //Starting the handler.
        handler.postDelayed(() -> {

            //Starting main activity.
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

            //Stopped this activity.
            SplashActivity.this.finish();
        }, 3000);

    }
}