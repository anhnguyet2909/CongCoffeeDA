package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.coffee.databinding.ActivityAdminLayoutBinding;

public class AdminLayout extends AppCompatActivity {
    ActivityAdminLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_admin_layout);
        getFragment(FragmentAdminProduct.newInstance());
        binding.llProduct.setOnClickListener(v->{
            binding.view1.setVisibility(View.VISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
            binding.view5.setVisibility(View.INVISIBLE);
            getFragment(FragmentAdminProduct.newInstance());
        });
        binding.llStaff.setOnClickListener(v->{
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.VISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
            binding.view5.setVisibility(View.INVISIBLE);
            getFragment(FragmentAdminStaff.newInstance());
        });
        binding.llResources.setOnClickListener(v->{
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.VISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
            binding.view5.setVisibility(View.INVISIBLE);
            getFragment(FragmentAdminResources.newInstance());
        });
        binding.llDelivery.setOnClickListener(v->{
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.VISIBLE);
            binding.view5.setVisibility(View.INVISIBLE);
            getFragment(FragmentAdminDelivery.newInstance());
        });
        binding.llAccount.setOnClickListener(v->{
            binding.view1.setVisibility(View.INVISIBLE);
            binding.view2.setVisibility(View.INVISIBLE);
            binding.view3.setVisibility(View.INVISIBLE);
            binding.view4.setVisibility(View.INVISIBLE);
            binding.view5.setVisibility(View.VISIBLE);
            getFragment(FragmentAdminAccount.newInstance());
        });
    }
    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragment).commit();
    }
}