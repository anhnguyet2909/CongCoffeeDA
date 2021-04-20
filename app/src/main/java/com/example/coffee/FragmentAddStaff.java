package com.example.coffee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.coffee.databinding.FragmentAddStaffBinding;

public class FragmentAddStaff extends Fragment {
    FragmentAddStaffBinding binding;
    public static FragmentAddStaff newInstance() {

        Bundle args = new Bundle();

        FragmentAddStaff fragment = new FragmentAddStaff();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_add_staff, container, false);
        binding.btnBackAddStaff.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAdminStaff fragmentAdminStaff=new FragmentAdminStaff();
            fragmentAdminStaff.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminStaff).commit();
        });
        return binding.getRoot();
    }
}
