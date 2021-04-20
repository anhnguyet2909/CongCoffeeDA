package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import Object.*;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Product> list;
    onProductClick onProductClick;

    public void setOnProductClick(onProductClick onProductClick) {
        this.onProductClick = onProductClick;
    }

    public AdapterProduct(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct.ViewHolder holder, int position) {
        final Product product=list.get(position);
//        Picasso.get().load(R.drawable.banner1).fit().into(holder.imgProduct);
        if(product.getImage()!=null){
            Picasso.get().load(product.getImage()).fit().into(holder.imgProduct);
        }
        else{
            Picasso.get().load(R.drawable.ic_launcher_background).fit().into(holder.imgProduct);
        }
        holder.tvProductName.setText(product.getName());
        String type="";
        ProductType[] a= ProductTypeDataUtils.getProductType();
        for(int i=0; i<a.length; i++){
            if(product.getTypeID()==a[i].getId())
                type=a[i].getName();
        }
        holder.tvProductType.setText(type);
        holder.tvProductPrice.setText(product.getPrice()+"");
//        holder.tvProductQuantity.setText(product.getQuantity()+"");
        holder.tvDelete.setOnClickListener(v->{
            onProductClick.onDelete(product);
        });
        holder.tvEdit.setOnClickListener(v->{
            onProductClick.onUpdate(product);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDelete, tvEdit, tvProductName, tvProductPrice, tvProductQuantity, tvProductType;
        ImageView imgProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDelete=itemView.findViewById(R.id.tvDelete);
            tvEdit=itemView.findViewById(R.id.tvEdit);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvProductPrice=itemView.findViewById(R.id.tvProductPrice);
//            tvProductQuantity=itemView.findViewById(R.id.tvProductQuantity);
            tvProductType=itemView.findViewById(R.id.tvProductType);
            imgProduct=itemView.findViewById(R.id.imgProduct);
        }
    }
}
