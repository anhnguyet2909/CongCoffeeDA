package com.example.coffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.coffee.databinding.FragmentEditStaffBinding;
import com.squareup.picasso.Picasso;

public class FragmentEditStaff extends Fragment {
    FragmentEditStaffBinding binding;
    public static FragmentEditStaff newInstance() {

        Bundle args = new Bundle();

        FragmentEditStaff fragment = new FragmentEditStaff();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_staff, container, false);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String user_name=sharedPreferences.getString("user_name", "");
        String user_image=sharedPreferences.getString("user_image", "");
        String user_phone=sharedPreferences.getString("user_phone", "");
        String user_position;
        binding.tvEditStaffName.setText(user_name);
        Picasso.get().load(user_image).into(binding.imgShowStaffImage);
        binding.tvEditStaffPhone.setText(user_phone);
        binding.tvEditStaffPosition.setText("Nhân viên thu ngân");
        binding.btnBackEditStaff.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAdminStaff fragmentAdminStaff=new FragmentAdminStaff();
            fragmentAdminStaff.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminStaff).commit();
        });
        return binding.getRoot();
    }
}
