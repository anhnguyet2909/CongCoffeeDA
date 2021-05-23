package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import Object.*;

import java.util.List;

public class AdapterBartenderList extends RecyclerView.Adapter<AdapterBartenderList.ViewHolder> {
    List<ShowOrderByTable> list;
    onCompleteClick onCompleteClick;

    public void setOnCompleteClick(com.example.coffee.onCompleteClick onCompleteClick) {
        this.onCompleteClick = onCompleteClick;
    }

    public AdapterBartenderList(List<ShowOrderByTable> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterBartenderList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_bartender_list, parent, false);
        AdapterBartenderList.ViewHolder viewHolder = new AdapterBartenderList.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBartenderList.ViewHolder holder, int position) {
        final ShowOrderByTable productOrder = list.get(position);
        holder.bar_table_name_tv.setText(productOrder.getTableName());
        Picasso.get().load(productOrder.getImage()).into(holder.bar_product_img);
        holder.bar_product_name_tv.setText(productOrder.getName());
        holder.bar_quantity_tv.setText("Số lượng: " + productOrder.getCount());
        holder.bar_complete_tv.setOnClickListener(v -> onCompleteClick.onComplete(productOrder));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bar_product_name_tv, bar_quantity_tv, bar_complete_tv, bar_table_name_tv;
        ImageView bar_product_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bar_complete_tv = itemView.findViewById(R.id.bar_complete_tv);
            bar_product_img = itemView.findViewById(R.id.bar_product_img);
            bar_quantity_tv = itemView.findViewById(R.id.bar_quantity_tv);
            bar_product_name_tv = itemView.findViewById(R.id.bar_product_name_tv);
            bar_table_name_tv = itemView.findViewById(R.id.bar_table_name_tv);
        }
    }

}
