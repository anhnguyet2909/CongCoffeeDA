package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.example.coffee.databinding.ActivityBartenderLayoutBinding;

public class BartenderLayout extends AppCompatActivity {
    ActivityBartenderLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager(FragmentBartenderList.newInstance());
        binding= DataBindingUtil.setContentView(this, R.layout.activity_bartender_layout);
        getFragmentManager(FragmentBartenderList.newInstance());
        binding.llList.setOnClickListener(v->{
            getFragmentManager(FragmentBartenderList.newInstance());
            binding.view1.setVisibility(View.VISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
        });
        binding.llAccount.setOnClickListener(v->{
            getFragmentManager(FragmentAdminAccount.newInstance());
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.VISIBLE);
        });
    }

    public void getFragmentManager(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.bartender_fragment, fragment).commit();
    }
}