package com.example.coffee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.coffee.databinding.FragmentAddMaterialBinding;

import Object.*;

public class FragmentAddMaterial extends Fragment {
    FragmentAddMaterialBinding binding;
//    DatabaseHandler databaseHandler;
    public static FragmentAddMaterial newInstance() {

        Bundle args = new Bundle();

        FragmentAddMaterial fragment = new FragmentAddMaterial();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_add_material, container, false);
//        databaseHandler=new DatabaseHandler(getContext());
        Bundle bundle=new Bundle();
        FragmentAdminResources fragmentAdminResources=new FragmentAdminResources();
        binding.btnBackAddMaterial.setOnClickListener(v->{
            fragmentAdminResources.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminResources).commit();
        });

        binding.btnAddMaterialConfirm.setOnClickListener(v->{
            String name=binding.etAddMaterialName.getText().toString().trim();
            String unit=binding.etAddMaterialUnit.getText().toString().trim();
            int amount=Integer.parseInt(binding.etAddMaterialAmount.getText().toString().trim());
            if (!name.equals("") && !unit.equals("")) {
                Material material = new Material("1", name, unit, amount);
//                databaseHandler.insertMaterial(material);
                fragmentAdminResources.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminResources).commit();
            }
        });

        return binding.getRoot();
    }
}
