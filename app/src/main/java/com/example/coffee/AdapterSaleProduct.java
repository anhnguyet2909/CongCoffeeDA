package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import Object.*;

public class AdapterSaleProduct extends RecyclerView.Adapter<AdapterSaleProduct.ViewHolder> {
    List<Product> list;
    onSaleProduct onSaleProduct;

    public AdapterSaleProduct(List<Product> list) {
        this.list = list;
    }

    public void setOnSaleProduct(onSaleProduct onSaleProduct) {
        this.onSaleProduct = onSaleProduct;
    }

    @NonNull
    @Override
    public AdapterSaleProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_sale_product, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSaleProduct.ViewHolder holder, int position) {
        final Product product=list.get(position);
        Picasso.get().load(product.getImage()).fit().into(holder.imgProductImage);
        holder.tvSaleName.setText(product.getName());
        holder.tvSalePrice.setText(product.getPrice()+"");
        holder.llProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaleProduct.onShowProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductImage, btnAddToOrder;
        TextView tvSaleName, tvSalePrice;
        LinearLayout llProductInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImage=itemView.findViewById(R.id.imgProductImage);
            tvSaleName=itemView.findViewById(R.id.tvSaleName);
            tvSalePrice=itemView.findViewById(R.id.tvSalePrice);
            llProductInfo=itemView.findViewById(R.id.llProductInfo);
        }
    }
}
