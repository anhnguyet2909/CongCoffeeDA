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

import com.example.coffee.databinding.FragmentEditProductBinding;
import com.squareup.picasso.Picasso;

import Object.*;

public class FragmentEditProduct extends Fragment {
    FragmentEditProductBinding binding;
//    DatabaseHandler databaseHandler;
    public static FragmentEditProduct newInstance() {

        Bundle args = new Bundle();

        FragmentEditProduct fragment = new FragmentEditProduct();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_product, container, false);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                Context.MODE_PRIVATE);
        String id=sharedPreferences.getString("product_id", "");
        String product_image=sharedPreferences.getString("product_image", "");
        binding.etEditProductName.setText(sharedPreferences.getString("product_name", ""));
        int product_price=sharedPreferences.getInt("product_price", 0);
        String product_option=sharedPreferences.getString("product_option", "");
        int product_type=sharedPreferences.getInt("product_type", 0);
        binding.etEditProductPrice.setText(product_price+"");

        Picasso.get().load(product_image).fit().into(binding.imgShowProductImage);
        binding.btnBackEditProduct.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAdminProduct fragmentAdminProduct=new FragmentAdminProduct();
            fragmentAdminProduct.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminProduct).commit();
        });
        binding.btnEditOptions.setOnClickListener(v->{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("key", 2);
            editor.commit();
            Bundle bundle=new Bundle();
            FragmentAddOptions fragmentAddOptions=new FragmentAddOptions();
            fragmentAddOptions.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddOptions).commit();
        });
        binding.btnEditMaterial.setOnClickListener(v->{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("key", 2);
            editor.commit();
            Bundle bundle=new Bundle();
            FragmentEditConsumList fragmentEditConsumList=new FragmentEditConsumList();
            fragmentEditConsumList.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditConsumList).commit();
        });
        binding.btnEditProductConfirm.setOnClickListener(v->{
            String name=binding.etEditProductName.getText().toString();
            int price=Integer.parseInt(binding.etEditProductPrice.getText().toString());
            Product product=new Product(id, name, product_image, price, product_option, product_type);
//            databaseHandler.updateProduct(product);
            Bundle bundle=new Bundle();
            FragmentAdminProduct fragmentAdminProduct=new FragmentAdminProduct();
            fragmentAdminProduct.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminProduct).commit();
        });
        return binding.getRoot();
    }
}
