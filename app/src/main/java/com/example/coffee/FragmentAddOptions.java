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

import com.example.coffee.databinding.FragmentAddOptionsBinding;

public class FragmentAddOptions extends Fragment {
    FragmentAddOptionsBinding binding;

    public static FragmentAddOptions newInstance() {

        Bundle args = new Bundle();

        FragmentAddOptions fragment = new FragmentAddOptions();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_add_options, container, false);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                Context.MODE_PRIVATE);
        int key=sharedPreferences.getInt("key", 0);
        binding.btnBackAddOptions.setOnClickListener(v->{
            if(key==1){
                Bundle bundle=new Bundle();
                FragmentAddProduct fragmentAddProduct=new FragmentAddProduct();
                fragmentAddProduct.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddProduct).commit();
            }
            else{
                Bundle bundle=new Bundle();
                FragmentEditProduct fragmentEditProduct=new FragmentEditProduct();
                fragmentEditProduct.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditProduct).commit();
            }
        });
        binding.tvAddMaterialOption.setOnClickListener(v->{

        });
        return binding.getRoot();
    }
}
