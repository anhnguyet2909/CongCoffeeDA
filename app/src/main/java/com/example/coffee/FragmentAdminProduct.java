package com.example.coffee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentAdminProductBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import Object.*;

public class FragmentAdminProduct extends Fragment {
    FragmentAdminProductBinding binding;
    List<Product> listCoffee, listTea, listJuice, listOthers;
    AdapterProduct adapterProduct;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference().child("products");
    public static FragmentAdminProduct newInstance() {

        Bundle args = new Bundle();

        FragmentAdminProduct fragment = new FragmentAdminProduct();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_admin_product, container, false);


        binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCoffee=new ArrayList<>();
                listTea=new ArrayList<>();
                listJuice=new ArrayList<>();
                listOthers=new ArrayList<>();
                if(snapshot.hasChildren()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Product product=dataSnapshot.getValue(Product.class);
                        if(product.getTypeID()==1){
                            listCoffee.add(product);
                        }
                        else if(product.getTypeID()==2){
                            listTea.add(product);
                        }
                        else if(product.getTypeID()==3){
                            listJuice.add(product);
                        }
                        else{
                            listOthers.add(product);
                        }
                    }

                    adapterProduct=new AdapterProduct(listCoffee);
                    showProductList();
                    binding.btnCoffee.setOnClickListener(v->{
                        binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
                        binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        adapterProduct=new AdapterProduct(listCoffee);
                        showProductList();
                    });

                    binding.btnTea.setOnClickListener(v->{
                        binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
                        binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        adapterProduct=new AdapterProduct(listTea);
                        showProductList();
                    });

                    binding.btnJuice.setOnClickListener(v->{
                        binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
                        binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        adapterProduct=new AdapterProduct(listJuice);
                        showProductList();
                    });

                    binding.btnOthers.setOnClickListener(v->{
                        binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
                        binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
                        adapterProduct=new AdapterProduct(listOthers);
                        showProductList();
                    });

                    binding.btnAddProduct.setOnClickListener(v->{
                        Bundle bundle=new Bundle();
                        FragmentAddProduct fragmentAddProduct=new FragmentAddProduct();
                        fragmentAddProduct.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddProduct).commit();
                    });

                    adapterProduct.notifyDataSetChanged();
                };
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }

    public void showProductList(){
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.rvProduct.setAdapter(adapterProduct);
        binding.rvProduct.setLayoutManager(layoutManager);
        adapterProduct.setOnProductClick(new onProductClick() {
            @Override
            public void onDelete(Product product) {
                AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                        .setTitle("Bạn chắc chắn muốn xóa sản phẩm này?")
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.child(product.getId()).removeValue();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                databaseHandler.deleteProduct(product);
                            }
                        })
                        .create();
                alertDialog.show();
            }

            @Override
            public void onUpdate(Product product) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("product_id", product.getId());
                editor.putInt("product_price", product.getPrice());
                editor.putString("product_name", product.getName());
                editor.putString("product_image", product.getImage());
                editor.putString("product_option", product.getOptions());
                editor.putInt("product_type", product.getTypeID());
                editor.commit();
                Bundle bundle=new Bundle();
                FragmentEditProduct fragmentEditProduct=new FragmentEditProduct();
                fragmentEditProduct.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditProduct).commit();
            }
        });

    }
}
