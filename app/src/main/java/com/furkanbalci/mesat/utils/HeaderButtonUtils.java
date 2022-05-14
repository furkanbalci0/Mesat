package com.furkanbalci.mesat.utils;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.ui.SearchActivity;

public class HeaderButtonUtils {

    public void setupButtons(AppCompatActivity activity) {

        activity.findViewById(R.id.image_menu_icon).setOnClickListener(v -> {
            View include = activity.findViewById(R.id.menu_include);
            include.setVisibility(View.VISIBLE);
        });

        activity.findViewById(R.id.image_search_icon).setOnClickListener(v -> {
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
        });

    }
}
