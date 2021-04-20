package com.example.coffee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coffee.databinding.FragmentAdminAccountBinding;

import java.util.ArrayList;
import java.util.List;

import Object.*;

public class FragmentAdminAccount extends Fragment {
    FragmentAdminAccountBinding binding;
    List<User> list = new ArrayList<User>();

    public static FragmentAdminAccount newInstance() {

        Bundle args = new Bundle();

        FragmentAdminAccount fragment = new FragmentAdminAccount();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_account, container, false);

        binding.tvAdminLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String image = sharedPreferences.getString("staffImage", "");
        String name = sharedPreferences.getString("staffName", "");
        Glide.with(getContext()).load(image).apply(RequestOptions.circleCropTransform()).into(binding.imgAvatar);
        binding.tvName.setText(name);

        return binding.getRoot();
    }
}
