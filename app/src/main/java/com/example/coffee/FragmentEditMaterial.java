package com.example.coffee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.coffee.databinding.FragmentEditMaterialBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentEditMaterial extends Fragment {
    FragmentEditMaterialBinding binding;
//    DatabaseHandler  databaseHandler;
    String material_id;
    public static FragmentEditMaterial newInstance() {

        Bundle args = new Bundle();

        FragmentEditMaterial fragment = new FragmentEditMaterial();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_material, container, false);
        Bundle bundle=new Bundle();
        FragmentAdminResources fragmentAdminResources=new FragmentAdminResources();
//        databaseHandler=new DatabaseHandler(getContext());

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        material_id=sharedPreferences.getString("material_id", "");
        String material_name=sharedPreferences.getString("material_name", "");
        String material_unit=sharedPreferences.getString("material_unit", "");
        int material_amount=sharedPreferences.getInt("material_amount", 0);
        binding.etEditMaterialName.setText(material_name);
        binding.etEditMaterialAmount.setText(material_amount+"");
        binding.etEditMaterialUnit.setText(material_unit);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("materials");

        binding.btnBackEditMaterial.setOnClickListener(v->{
            fragmentAdminResources.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminResources).commit();
        });
        binding.btnEditMaterialConfirm.setOnClickListener(v->{
            String name=binding.etEditMaterialName.getText().toString().trim();
            String unit=binding.etEditMaterialUnit.getText().toString().trim();
            int amount= Integer.parseInt(binding.etEditMaterialAmount.getText().toString().trim());
            if(!name.equals("") && !unit.equals("")){
                databaseReference.orderByChild(material_id+"").equalTo("id").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(material_id).child("name").setValue(binding.etEditMaterialName.getText().toString());
                        databaseReference.child(material_id).child("unit").setValue(binding.etEditMaterialUnit.getText().toString());
                        databaseReference.child(material_id).child("amount").setValue(Integer.parseInt(binding.etEditMaterialAmount.getText().toString()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                fragmentAdminResources.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminResources).commit();
            }
        });
        binding.btnDeleteMaterialConfirm.setOnClickListener(v->{
            AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                    .setTitle("Bạn chắc chắn muốn xóa nguyên liệu này?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            databaseHandler.deleteMaterial(material_id);
                            fragmentAdminResources.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminResources).commit();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create();
            alertDialog.show();
        });
        return binding.getRoot();
    }
}
