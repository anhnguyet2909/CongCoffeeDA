package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import Object.*;

public class AdapterOrderList extends RecyclerView.Adapter<AdapterOrderList.ViewHolder> {
    List<ProductOrder> list;
    onOrderChange onOrderChange;
    int count;

    public AdapterOrderList(List<ProductOrder> list) {
        this.list = list;
    }

    public void setOnOrderChange(onOrderChange onOrderChange) {
        this.onOrderChange = onOrderChange;
    }

    @NonNull
    @Override
    public AdapterOrderList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.dialog_show_product, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderList.ViewHolder holder, int position) {
        final ProductOrder productOrder=list.get(position);

        Picasso.get().load(productOrder.getImage()).fit().into(holder.imgShowProduct);
        holder.tvShowProductName.setText(productOrder.getName());
        holder.tvShowProductPrice.setText(productOrder.getPrice()+"");
        holder.tvCount.setText(productOrder.getCount()+"");
        count=Integer.parseInt(holder.tvCount.getText().toString());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=count+1;
                holder.tvCount.setText(count+"");
                onOrderChange.onAdd(productOrder, count+"", position);
            }
        });
        holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=count-1;
                holder.tvCount.setText(count+"");
                onOrderChange.onSubtract(productOrder,count+"", position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgShowProduct;
        TextView tvShowProductName, tvShowProductPrice, btnAdd, btnSubtract, tvCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShowProduct=itemView.findViewById(R.id.imgShowProduct);
            tvShowProductName=itemView.findViewById(R.id.tvShowProductName);
            tvShowProductPrice=itemView.findViewById(R.id.tvShowProductPrice);
            btnAdd=itemView.findViewById(R.id.btnAdd);
            btnSubtract=itemView.findViewById(R.id.btnSubtract);
            tvCount=itemView.findViewById(R.id.tvCount);
        }
    }
}
