package com.example.coffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentStaffCartBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Object.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Parse Dependencies

public class FragmentStaffCart extends Fragment {
    FragmentStaffCartBinding binding;
    AdapterOrderList adapterOrderList;
    List<ProductOrder> list;
    APIInterface apiClient;
    String tenBan = "";
    int orderId;

    public static FragmentStaffCart newInstance() {

        Bundle args = new Bundle();

        FragmentStaffCart fragment = new FragmentStaffCart();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_cart, container, false);

        binding.btnBack.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            FragmentStaffHome fragment = new FragmentStaffHome();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.staff_fragment, fragment).commit();
        });


        list = Singleton.getUniqInstance().getList();
        binding.tvPrice.setText(getTotalPrice() + "");
        adapterOrderList = new AdapterOrderList(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.rvCart.setAdapter(adapterOrderList);
        binding.rvCart.setLayoutManager(layoutManager);
        adapterOrderList.setOnOrderChange(new onOrderChange() {
            @Override
            public void onSubtract(ProductOrder productOrder, String s, int i) {
                int count = Integer.parseInt(s);
                productOrder = new ProductOrder(productOrder.getId(), productOrder.getName(), "",
                        productOrder.getImage(), productOrder.getPrice(), count);
                if (count == 0) {
                    list.remove(i);
                    adapterOrderList.notifyItemChanged(i);
                } else {
                    list.set(i, productOrder);
                }
                binding.tvPrice.setText(getTotalPrice() + "");
                Singleton.getUniqInstance().setList(list);
            }

            @Override
            public void onAdd(ProductOrder productOrder, String s, int i) {
                productOrder = new ProductOrder(productOrder.getId(), productOrder.getName(), "",
                        productOrder.getImage(), productOrder.getPrice(), Integer.parseInt(s));
                list.set(i, productOrder);
                Singleton.getUniqInstance().setList(list);
                binding.tvPrice.setText(getTotalPrice() + "");
            }
        });
        List<JSONObject> jsonObjects = new ArrayList<>();
        apiClient = APIClient.getClient().create(APIInterface.class);

        binding.btnOrder.setOnClickListener(v -> {
            Log.d("PUSH", "btnOrder: Click Okkkkkkk");
            // tạo listProduct json
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String listProduct = gson.toJson(list);
            Log.d("TAG123", " listtpro:" + listProduct);
            //creat Order
            User user = Singleton.getUniqInstance().getUser();

            //create Order
            if (!tenBan.trim().equals("")) {
                Order order = new Order(tenBan, user.name, user.phone,
                        binding.etNote.getText().toString().trim(), listProduct);
                Call<APIInterface.OrderCreateResponse> call = apiClient.createOrder(order);
                call.enqueue(new Callback<APIInterface.OrderCreateResponse>() {
                    @Override
                    public void onResponse(Call<APIInterface.OrderCreateResponse> call, Response<APIInterface.OrderCreateResponse> response) {
                        if (response.code() == 200) {
                            Log.d("TAG123", "onResponse: Tao order thanh cong");
                            Toast.makeText(getContext(), "Tạo order thành công", Toast.LENGTH_SHORT).show();
                            Log.d("TAG123", response.body().message);
                            int[]orderIds=new int[]{response.body().order.id};
                            Receipt receipt=new Receipt(user.name, user.phone, 0, 0, 0, 0,orderIds);
                            Call<APIInterface.ReceiptCreateResponse> call1 = apiClient.createReceipt(receipt);
                            call1.enqueue(new Callback<APIInterface.ReceiptCreateResponse>() {
                                @Override
                                public void onResponse(Call<APIInterface.ReceiptCreateResponse> call, Response<APIInterface.ReceiptCreateResponse> response) {
                                    if (response.code() == 200) {
                                        Log.d("TAG123", "onResponse: Tao receipt thanh cong");
                                    } else {
                                        Log.d("TAG123", "onResponse: Tao receipt that bai");
                                    }
                                }
                                @Override
                                public void onFailure(Call<APIInterface.ReceiptCreateResponse> call, Throwable t) {
                                    Log.d("TAG123", "onResponse: Tao receipt that bai" + t.getMessage());
                                }
                            });
                        } else {
                            Log.d("TAG123", "onResponse: Tao order that bai");
                        }
                    }

                    @Override
                    public void onFailure(Call<APIInterface.OrderCreateResponse> call, Throwable t) {
                        Log.d("TAG123", "onResponse: Tao order that bai: " + t.getMessage());
                    }
                });
            } else {
                alertDialog();
                Toast.makeText(getContext(), "Nhập số bàn để tạo order", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void alertDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_input_table, null, false);
        EditText etTableName = view.findViewById(R.id.etTableName);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tableName = etTableName.getText().toString().trim();
                        if (!tableName.equals("")) {
                            tenBan = "Bàn " + etTableName.getText().toString().trim();
                        }
                    }
                })
                .create();
        builder.show();
    }

    private int getTotalPrice() {
        int t = 0;
        for (ProductOrder i : list) {
            t = t + i.getCount() * i.getPrice();
        }
        return t;
    }

}
