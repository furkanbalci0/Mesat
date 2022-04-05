package com.furkanbalci.mesat.utils;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.furkanbalci.mesat.MainActivity;
import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.ui.ListActivity;
import com.furkanbalci.mesat.ui.LoginActivity;
import com.furkanbalci.mesat.ui.SearchActivity;
import com.furkanbalci.mesat.ui.SupportActivity;

public class ButtonUtils {

    public void setupButtons(AppCompatActivity activity) {

        System.out.println("NULL CHEEECKKK: " + activity);
        //Profile button.
        activity.findViewById(R.id.footer_icon_profile).setOnClickListener(v -> {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        });

        //Profile button.
        activity.findViewById(R.id.footer_icon_home).setOnClickListener(v -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        });

        //Profile button.
        activity.findViewById(R.id.footer_icon_add).setOnClickListener(v -> {
            Intent intent = new Intent(activity, ListActivity.class);
            activity.startActivity(intent);
        });

        //Search button.
        activity.findViewById(R.id.footer_icon_search).setOnClickListener(v -> {
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
        });

        //Support button.
        activity.findViewById(R.id.footer_icon_support).setOnClickListener(v -> {
            Intent intent = new Intent(activity, SupportActivity.class);
            activity.startActivity(intent);
        });
    }
}
