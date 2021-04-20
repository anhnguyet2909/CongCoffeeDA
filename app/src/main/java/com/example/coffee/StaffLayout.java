package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.coffee.databinding.ActivityStaffLayoutBinding;
import com.parse.ParseObject;

public class StaffLayout extends AppCompatActivity {
    ActivityStaffLayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_staff_layout);

        getFragment(FragmentStaffHome.newInstance());
        binding.llAccount.setOnClickListener(v->{
            getFragment(FragmentAdminAccount.newInstance());
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.VISIBLE);
        });
        binding.llHome.setOnClickListener(v->{
            getFragment(FragmentStaffHome.newInstance());
            binding.view1.setVisibility(View.VISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
        });
        binding.llCart.setOnClickListener(v->{
            getFragment(FragmentStaffCart.newInstance());
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.VISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
        });
        binding.llNotification.setOnClickListener(v->{
            getFragment(FragmentStaffNotification.newInstance());
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.VISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
        });
    }
    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.staff_fragment, fragment).commit();
    }
}