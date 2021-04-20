package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Object.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterDelivery extends RecyclerView.Adapter<AdapterDelivery.ViewHolder> {
    List<Order> list;
    onOrderClick onOrderClick;

    public AdapterDelivery(List<Order> list) {
        this.list = list;
    }

    public void setOnOrderClick(onOrderClick onOrderClick) {
        this.onOrderClick = onOrderClick;
    }

    @NonNull
    @Override
    public AdapterDelivery.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_delivery, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDelivery.ViewHolder holder, int position) {
        final Order order =list.get(position);
        holder.tvTableID.setText(order.getTableID()+"");
        holder.tvTableName.setText(order.getName());
        holder.tvStaffOrder.setText(order.getNote());
//        holder.tvTotalPrice.setText("");
        holder.llDeliveryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderClick.onShowListProduct(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llDeliveryItem;
        TextView tvTableID, tvTableName, tvStaffOrder, tvTotalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llDeliveryItem=itemView.findViewById(R.id.llDeliveryItem);
            tvTableID=itemView.findViewById(R.id.tvTableID);
            tvTableName=itemView.findViewById(R.id.tvTableName);
            tvStaffOrder=itemView.findViewById(R.id.tvStaffOrder);
            tvTotalPrice=itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
