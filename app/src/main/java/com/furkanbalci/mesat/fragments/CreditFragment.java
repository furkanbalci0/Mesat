package com.furkanbalci.mesat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.furkanbalci.mesat.R;
import com.furkanbalci.mesat.data.LocalDataManager;
import com.furkanbalci.mesat.databinding.FragmentCreditBinding;
import com.furkanbalci.mesat.models.user.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreditFragment extends Fragment {

    //Binding fragment.
    private FragmentCreditBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreditBinding.inflate(inflater, container, false);

        User user = LocalDataManager.get(container.getContext(), "user_object", User.class);
        if (user == null) {
            Snackbar.make(binding.getRoot(), "Kullanıcı bilgileri alınamadı. Lütfen tekrar deneyiniz. 1", Snackbar.LENGTH_LONG).show();
            return binding.getRoot();
        }

        Button button = binding.profileButtonUpdate;

        button.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").whereEqualTo("mail", user.getMail()).get().addOnSuccessListener(querySnapshot -> {

                if (querySnapshot.getDocuments().size() == 0) {
                    Snackbar.make(binding.getRoot(), "Kullanıcı bilgileri alınamadı. Lütfen tekrar deneyiniz. 2", Snackbar.LENGTH_LONG).show();
                    return;
                }

                TextInputLayout textInputLayout = binding.inputPrice;

                int amount = Integer.parseInt(textInputLayout.getEditText().getText().toString());

                user.setCredit(user.getCredit() + amount);
                db.collection("users").document(user.getId()).set(user);

                Snackbar.make(binding.getRoot(), "Kredi başarıyla eklendi.", Snackbar.LENGTH_LONG).show();


                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame, new ProfileFragment());
                fragmentTransaction.commit();


            });
        });

        return binding.getRoot();
    }
}