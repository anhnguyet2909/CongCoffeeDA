package com.example.coffee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.coffee.databinding.FragmentStaffNotificationsBinding;

public class FragmentStaffNotification extends Fragment {
    FragmentStaffNotificationsBinding binding;
    public static FragmentStaffNotification newInstance() {

        Bundle args = new Bundle();

        FragmentStaffNotification fragment = new FragmentStaffNotification();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_staff_notifications, container, false);
        return binding.getRoot();
    }
}
