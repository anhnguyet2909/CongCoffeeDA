package com.example.coffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentAdminResourcesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Object.Material;

public class FragmentAdminResources extends Fragment {
    FragmentAdminResourcesBinding binding;
    List<Material> listSQL;
    AdapterMaterial adapterMaterial;

    public static FragmentAdminResources newInstance() {

        Bundle args = new Bundle();

        FragmentAdminResources fragment = new FragmentAdminResources();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_resources, container, false);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("materials");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSQL=new ArrayList<>();
                Log.d("testElements", "getConnect");
                if(snapshot.hasChildren()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Log.d("testElements", "getElement");
                        Material material=dataSnapshot.getValue(Material.class);
                        Log.d("InfoMaterial", material.toString());
                        listSQL.add(material);
                    }
                }
                Log.d("testElements", "getSuccessfully");
                adapterMaterial = new AdapterMaterial(listSQL);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL, false);
                binding.rvResources.setAdapter(adapterMaterial);
                binding.rvResources.setLayoutManager(layoutManager);
//                Log.d("testElements", "getAdapterSuccessfully");
                adapterMaterial.setOnMaterialClick(new onMaterialClick() {
                    @Override
                    public void onClickMaterial(Material material) {
                        SharedPreferences sharedPreferences = getActivity().
                                getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("material_id", material.getId());
                        editor.putString("material_name", material.getName());
                        editor.putString("material_unit", material.getUnit());
                        editor.putInt("material_amount", material.getAmount());
                        editor.commit();
                        Bundle bundle = new Bundle();
                        FragmentEditMaterial fragmentEditMaterial = new FragmentEditMaterial();
                        fragmentEditMaterial.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditMaterial).commit();
                    }
                });
                adapterMaterial.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnAddResources.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAddMaterial fragmentAddMaterial=new FragmentAddMaterial();
            fragmentAddMaterial.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddMaterial).commit();
        });

        return binding.getRoot();
    }

}
