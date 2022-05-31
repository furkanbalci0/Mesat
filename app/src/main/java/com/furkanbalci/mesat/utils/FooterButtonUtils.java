package com.furkanbalci.mesat.utils;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.fragments.AuctionCreateFragment;
import com.furkanbalci.mesat.fragments.LoginFragment;
import com.furkanbalci.mesat.fragments.MainFragment;
import com.furkanbalci.mesat.fragments.RegisterFragment;
import com.furkanbalci.mesat.fragments.SupportFragment;
import com.furkanbalci.mesat.ui.ListActivity;
import com.furkanbalci.mesat.ui.SearchActivity;

public class FooterButtonUtils {

    public void setupButtons(AppCompatActivity activity) {

        //Profile button.
        activity.findViewById(R.id.footer_icon_profile).setOnClickListener(v -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            String id = LocalDataManager.getString(activity, "id", "0");

            if (id.equals("0")){
                fragmentTransaction.replace(R.id.frame, new RegisterFragment());
            }else{
                fragmentTransaction.replace(R.id.frame, new LoginFragment());
            }

            fragmentTransaction.commit();
        });

        //Profile button.
        activity.findViewById(R.id.footer_icon_home).setOnClickListener(v -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new MainFragment());
            fragmentTransaction.commit();
        });



        //Search button.
        activity.findViewById(R.id.footer_icon_search).setOnClickListener(v -> {
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
        });

        //Support button.
        activity.findViewById(R.id.footer_icon_support).setOnClickListener(v -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, new SupportFragment());
            fragmentTransaction.commit();
        });

    }
}
