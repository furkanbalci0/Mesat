package com.furkanbalci.mesat.utils;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;

public class MenuButtonUtils {

    public void setupButtons(AppCompatActivity activity) {

        activity.findViewById(R.id.menu_close_button).setOnClickListener(v -> {
            View include = activity.findViewById(R.id.menu_include);
            include.setVisibility(View.INVISIBLE);
        });

        //Profile button.
        activity.findViewById(R.id.menu_button_home).setOnClickListener(v -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        });



    }
}
