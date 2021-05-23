package com.example.coffee;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentStaffNotificationsBinding;

import java.util.List;
import Object.ShowOrderByTable;
import Object.Singleton;


public class FragmentStaffNotification extends Fragment {
    FragmentStaffNotificationsBinding binding;
    AdapterStaffNotification adapterStaffNotification;
    List<ShowOrderByTable> list;
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

        list= Singleton.getUniqInstance().getListTableOrder();
        adapterStaffNotification=new AdapterStaffNotification(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.rvNotification.setLayoutManager(layoutManager);
        binding.rvNotification.setAdapter(adapterStaffNotification);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                list=Singleton.getUniqInstance().getListTableOrder();

                if (list != null && list.size() > 0) {
                    if (adapterStaffNotification != null && adapterStaffNotification.getItemCount() > 0) {
                        adapterStaffNotification.notifyDataSetChanged();
                    }
                }


                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);

        return binding.getRoot();
    }
}
