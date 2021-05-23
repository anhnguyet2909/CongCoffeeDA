package com.example.coffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentAdminDeliveryBinding;

import java.util.ArrayList;
import java.util.List;
import Object.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAdminDelivery extends Fragment {
    FragmentAdminDeliveryBinding binding;
    List<APIInterface.ReceiptOnList> list=new ArrayList<>();
    List<ProductOrder> productList=new ArrayList<>();
    AdapterDelivery adapterDelivery;
    AdapterShowBill adapterShowBill;
    List<APIInterface.OrderInBarList> listTable;
    String table_name, staff_order;
    TextView tvTableNameDialog, tvStaffOrderDialog;
    RecyclerView rvShowListOrder;
    APIInterface apiInterface=APIClient.getClient().create(APIInterface.class);
    public static FragmentAdminDelivery newInstance() {

        Bundle args = new Bundle();

        FragmentAdminDelivery fragment = new FragmentAdminDelivery();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_admin_delivery, container, false);


        listTable=new ArrayList<APIInterface.OrderInBarList>();

        getReceiptList();

        return binding.getRoot();
    }

    public void getReceiptList(){
        Call<APIInterface.ReceiptListResponse> call=apiInterface.getListReceipt();
        call.enqueue(new Callback<APIInterface.ReceiptListResponse>() {
            @Override
            public void onResponse(Call<APIInterface.ReceiptListResponse> call, Response<APIInterface.ReceiptListResponse> response) {
                APIInterface.ReceiptListResponse content=response.body();
                Log.d("getReceipt", "content: " + content.totalItems);
                Log.d("getReceipt", "response: " + response.code());
                list=content.items;
                Toast.makeText(getContext(), "totalItems"+list.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<APIInterface.ReceiptListResponse> call, Throwable t) {

            }
        });
    }

    public void alertDialog(){
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.dialog_show_bill, null, false);
        tvTableNameDialog=view.findViewById(R.id.tvTableNameDialog);
        tvStaffOrderDialog=view.findViewById(R.id.tvStaffOrderDialog);
        rvShowListOrder=view.findViewById(R.id.rvShowListOrder);
        tvTableNameDialog.setText(table_name);
        tvStaffOrderDialog.setText(staff_order);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        rvShowListOrder.setLayoutManager(layoutManager);
        rvShowListOrder.setAdapter(adapterShowBill);

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.create();
        builder.show();
    }
}
