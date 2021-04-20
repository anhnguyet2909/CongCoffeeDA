package com.example.coffee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentAdminStaffBinding;

import java.util.ArrayList;
import java.util.List;

import Object.*;


public class FragmentAdminStaff extends Fragment {
    FragmentAdminStaffBinding binding;
    int choose =1;
    List<User> list=new ArrayList<>();
    List<User> Lst=new ArrayList<>();
    AdapterStaff adapterStaff;
    String staff_image;
    ImageView imgStaffImage;

    public static FragmentAdminStaff newInstance() {

        Bundle args = new Bundle();

        FragmentAdminStaff fragment = new FragmentAdminStaff();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_admin_staff, container, false);

        list= Singleton.getUniqInstance().getUserList();
        adapterStaff=new AdapterStaff(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvStaff.setLayoutManager(layoutManager);
        binding.rvStaff.setAdapter(adapterStaff);
        adapterStaff.setOnStaffClick(new onStaffClick() {
            @Override
            public void onShow(User user) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("user_id", user.getId());
                editor.putString("user_name", user.getName());
                editor.putString("user_image", user.getImage());
                editor.putString("user_phone", user.getPhone());
                editor.putInt("user_type", user.getRole());
                editor.commit();
                Bundle bundle=new Bundle();
                FragmentEditStaff fragmentEditStaff=new FragmentEditStaff();
                fragmentEditStaff.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditStaff).commit();
            }
        });

        binding.btnAddStaff.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAddStaff fragmentAddStaff=new FragmentAddStaff();
            fragmentAddStaff.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddStaff).commit();
        });
        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            staff_image = cursor.getString(columnIndex);
            cursor.close();
            imgStaffImage.setImageURI(selectedImage);
        }
    }
}
