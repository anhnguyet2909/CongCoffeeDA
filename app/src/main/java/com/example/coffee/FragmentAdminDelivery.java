package com.example.coffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class FragmentAdminDelivery extends Fragment {
    FragmentAdminDeliveryBinding binding;
    List<Order> list=new ArrayList<>();
    List<OrderList> orderLists=new ArrayList<>();
    List<Product> productList=new ArrayList<>();
    AdapterDelivery adapterDelivery;
    AdapterShowBill adapterShowBill;
    String table_name, staff_order;
    TextView tvTableNameDialog, tvStaffOrderDialog;
    RecyclerView rvShowListOrder;
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

//        Order o1=new Order(1, "Bàn 1", 100000, "An Huệ Hân");
//        Order o2=new Order(2, "Bàn 2", 150000, "Trần Văn Nam");
//        Order o3=new Order(3, "Bàn 3", 110000, "Bùi Tú Anh");

//        list.add(o1);
//        list.add(o2);
//        list.add(o3);
        adapterDelivery=new AdapterDelivery(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.rvDelivery.setAdapter(adapterDelivery);
        binding.rvDelivery.setLayoutManager(layoutManager);
        adapterDelivery.setOnOrderClick(new onOrderClick() {
            @Override
            public void onShowListProduct(Order order) {
                table_name=order.getName();
//                staff_order=order.getStaffID()+"";
                alertDialog();

            }
        });
        return binding.getRoot();
    }

    public void alertDialog(){
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.dialog_show_bill, null, false);
        tvTableNameDialog=view.findViewById(R.id.tvTableNameDialog);
        tvStaffOrderDialog=view.findViewById(R.id.tvStaffOrderDialog);
        rvShowListOrder=view.findViewById(R.id.rvShowListOrder);
        tvTableNameDialog.setText(table_name);
        tvStaffOrderDialog.setText(staff_order);
//        OrderList ol1=new OrderList(1,1,4);
//        OrderList ol2=new OrderList(1,3,5);
//        OrderList ol3=new OrderList(1,2,1);
//        OrderList ol4=new OrderList(2,1,2);
//        OrderList ol5=new OrderList(2,3,1);
//        orderLists.add(ol1);
//        orderLists.add(ol2);
//        orderLists.add(ol3);
//        orderLists.add(ol4);
//        orderLists.add(ol5);
        tvTableNameDialog.setText("2");

        tvStaffOrderDialog.setText("Trần Văn Nam");
//        adapterShowBill=new AdapterShowBill(orderLists);
        adapterShowBill=new AdapterShowBill(productList);
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
