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

import com.example.coffee.databinding.FragmentStaffCartBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Object.*;

import org.json.JSONException;
import org.json.JSONObject;
// Parse Dependencies
import com.parse.ParseObject;
import com.parse.ParsePush;

public class FragmentStaffCart extends Fragment {
    FragmentStaffCartBinding binding;
    AdapterOrderList adapterOrderList;
    List<ProductOrder> list;

    public static FragmentStaffCart newInstance() {

        Bundle args = new Bundle();

        FragmentStaffCart fragment = new FragmentStaffCart();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_staff_cart, container, false);

        binding.btnBack.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            FragmentStaffHome fragment = new FragmentStaffHome();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.staff_fragment, fragment).commit();
        });

        list=Singleton.getUniqInstance().getList();
        adapterOrderList=new AdapterOrderList(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.rvCart.setAdapter(adapterOrderList);
        binding.rvCart.setLayoutManager(layoutManager);
        adapterOrderList.setOnOrderChange(new onOrderChange() {
            @Override
            public void onSubtract(ProductOrder productOrder, String s, int i) {
                int count=Integer.parseInt(s);
                productOrder=new ProductOrder(productOrder.getId(), productOrder.getName(),
                        productOrder.getImage(), productOrder.getPrice(), count);
                if(count==0){
                    list.remove(i);
                    adapterOrderList.notifyItemChanged(i);
                }
                else{
                    list.set(i, productOrder);
                }
                Singleton.getUniqInstance().setList(list);
            }

            @Override
            public void onAdd(ProductOrder productOrder, String s, int i) {
                productOrder=new ProductOrder(productOrder.getId(), productOrder.getName(),
                        productOrder.getImage(), productOrder.getPrice(), Integer.parseInt(s));
                list.set(i, productOrder);
                Singleton.getUniqInstance().setList(list);
            }
        });
        List<JSONObject> jsonObjects=new ArrayList<>();
        binding.btnOrder.setOnClickListener(v->{
            Log.d("PUSH", "btnOrder: Click Okkkkkkk");
            String key= Constant.randomName(10);

            Gson gson=new Gson();
            //List Objects->String
            String listString=gson.toJson(list, new TypeToken<ArrayList<ProductOrder>>(){}.getType());
            Log.d("jsonList", listString);

            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference().child("orders");
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            String staffName=sharedPreferences.getString("staffName", "");
            Order order=new Order(key, "Bàn 3", staffName, "", listString);
            databaseReference.child(key).setValue(order);

            //String->List Objects
            TypeToken<List<ProductOrder>> listTypeToken=new TypeToken<List<ProductOrder>>(){};
            List<ProductOrder> productOrders=gson.fromJson(listString, listTypeToken.getType());
            for(int i=0; i<productOrders.size(); i++){
                Log.d("jsonList", productOrders.get(i).toString());
            }

            ParseObject firstObject = new  ParseObject("FirstClass");
            firstObject.put("message","Hey ! First message from android. Parse is now connected");
            firstObject.saveInBackground(e -> {
                if (e != null){
                    Log.e("MainActivity", e.getLocalizedMessage());
                }else{
                    Log.d("MainActivity","Object saved.");
                }
            });

            JSONObject data = new JSONObject();
// Put data in the JSON object
            try {
                data.put("alert", "Back4App Rocks!");
                data.put("title", "Hello from Device");
            } catch ( JSONException e) {
                // should not happen
                throw new IllegalArgumentException("unexpected parsing error", e);
            }
// Configure the push
            ParsePush push = new ParsePush();
            push.setChannel("News");
            push.setData(data);
            Log.d("PUSH", "onCreateView: Push runnnnnninggg");
            push.sendInBackground();
        });

        return binding.getRoot();
    }

}
