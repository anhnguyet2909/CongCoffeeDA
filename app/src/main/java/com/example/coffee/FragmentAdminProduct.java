package com.example.coffee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAdminProduct extends Fragment {
    FragmentAdminProductBinding binding;
    List<Product> listCoffee, listTea, listJuice, listOthers, list;
    AdapterProduct adapterProduct;

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

        list=new ArrayList<Product>();
        listCoffee=new ArrayList<Product>();
        listJuice=new ArrayList<Product>();
        listTea=new ArrayList<Product>();
        listOthers=new ArrayList<Product>();


        binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
        loadCategoryTitle();
        loadProductList();

        binding.btnAddProduct.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAddProduct fragmentAddProduct=new FragmentAddProduct();
            fragmentAddProduct.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddProduct).commit();
        });



        return binding.getRoot();
    }

    private void loadCategoryTitle() {
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<APIInterface.ListCategory> call = apiInterface.getCategories();
        call.enqueue(new Callback<APIInterface.ListCategory>() {
            @Override
            public void onResponse(Call<APIInterface.ListCategory> call, Response<APIInterface.ListCategory> response) {
                APIInterface.ListCategory data = response.body();
                List<APIInterface.Category> categories = data.categories;
                for (APIInterface.Category cat : categories) {
                    Log.d("TAG123", "onResponse: cat:"+cat.name+"-->"+cat.id);
                }
            }

            @Override
            public void onFailure(Call<APIInterface.ListCategory> call, Throwable t) {
                Log.d("TAG", "onFailure: Load category fail:"+t.getMessage());
            }
        });
    }


    private void loadProductList() {
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<APIInterface.ListProduct> call=apiInterface.getListProduct();
        call.enqueue(new Callback<APIInterface.ListProduct>() {
            @Override
            public void onResponse(Call<APIInterface.ListProduct> call, Response<APIInterface.ListProduct> response) {
                APIInterface.ListProduct content = response.body();
                list=content.items;
                Log.d("TAG123", "onResponse: items.size:"+content.items.size());
                Log.d("TAG123", "onResponse: code:"+response.code());

                // cho nay tach category nhu the nay khong on
                // 1. categoryId co the tahy doi
                // 2. category name do admin qd va co the chinh sua
                // khong phai categoryId luon la 1 2 3 4, co the la so khac
                // tuy theo viec chinh sua category o tren database

                for(Product i:list){
                    switch (i.getCategoryId()){
                        case 1:
                            Log.d("TAG123", i.getCategoryId()+"");
                            listCoffee.add(i);
                            break;
                        case 2:
                            Log.d("TAG123", i.getCategoryId()+"");
                            listTea.add(i);
                            break;
                        case 3:
                            Log.d("TAG123", i.getCategoryId()+"");
                            listJuice.add(i);
                            break;
                        case 4:
                            Log.d("TAG123", i.getCategoryId()+"");
                            listOthers.add(i);
                            break;
                    }
                }
                adapterProduct=new AdapterProduct(listCoffee);
                showProductList();
                allAdapter();
            }

            @Override
            public void onFailure(Call<APIInterface.ListProduct> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Test", t.getMessage());//
            }
        });
    }

    public void allAdapter(){
        //Coffee
        binding.btnCoffee.setOnClickListener(v->{
            adapterProduct=new AdapterProduct(listCoffee);
            binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
            binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            showProductList();
        });
        binding.btnJuice.setOnClickListener(v->{
            adapterProduct=new AdapterProduct(listJuice);
            binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
            binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            showProductList();
        });
        binding.btnTea.setOnClickListener(v->{
            adapterProduct=new AdapterProduct(listTea);
            binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
            binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            showProductList();
        });
        binding.btnOthers.setOnClickListener(v->{
            adapterProduct=new AdapterProduct(listOthers);
            binding.btnOthers.setTextColor(ContextCompat.getColor(getContext(),R .color.textColor));
            binding.btnTea.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnJuice.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            binding.btnCoffee.setTextColor(ContextCompat.getColor(getContext(),R .color.bg_view));
            showProductList();
        });


        Log.d("TAG123", "allAdapter: listCoffee.size():"+listCoffee.size());
        Log.d("TAG123", "allAdapter: listTea.size():"+listTea.size());
        Log.d("TAG123", "allAdapter: listJuice.size():"+listJuice.size());
        Log.d("TAG123", "allAdapter: listOthers.size():"+listOthers.size());
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
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
                editor.putInt("product_type", product.getCategoryId());
                editor.commit();
                Bundle bundle=new Bundle();
                FragmentEditProduct fragmentEditProduct=new FragmentEditProduct();
                fragmentEditProduct.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditProduct).commit();
            }
        });

    }
}
