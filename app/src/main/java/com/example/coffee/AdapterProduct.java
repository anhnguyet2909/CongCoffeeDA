package com.example.coffee;

import android.util.Log;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import Object.APIInterface;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Product> list;
    onProductClick onProductClick;
    APIInterface.Category[] cate;

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
        Picasso.get().load(product.getFullLinkImage()).fit().into(holder.imgProduct);
        holder.tvProductName.setText(product.getName());
//        loadCategoryTitle();
//        String type="";
//        for (APIInterface.Category category : cate) {
//            if (product.getCategoryId() == category.id)
//                type = category.name;
//        }
//        holder.tvProductType.setText(type);
        holder.tvProductPrice.setText(product.getPrice()+"");
        holder.tvDelete.setOnClickListener(v->{
            onProductClick.onDelete(product);
        });
        holder.tvEdit.setOnClickListener(v->{
            onProductClick.onUpdate(product);
        });
    }

//    private void loadCategoryTitle() {
//        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
//        Call<APIInterface.ListCategory> call = apiInterface.getCategories();
//        call.enqueue(new Callback<APIInterface.ListCategory>() {
//            @Override
//            public void onResponse(Call<APIInterface.ListCategory> call, Response<APIInterface.ListCategory> response) {
//                APIInterface.ListCategory data = response.body();
//                List<APIInterface.Category> categories = data.categories;
//                cate=categories.toArray(new APIInterface.Category[0]);
//                for (APIInterface.Category cat : categories) {
//                    Log.d("ADT", "onResponse: cat:"+cat.name+"-->"+cat.id);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<APIInterface.ListCategory> call, Throwable t) {
//                Log.d("TAG", "onFailure: Load category fail:"+t.getMessage());
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDelete, tvEdit, tvProductName, tvProductPrice, tvProductType;
        ImageView imgProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDelete=itemView.findViewById(R.id.tvDelete);
            tvEdit=itemView.findViewById(R.id.tvEdit);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvProductPrice=itemView.findViewById(R.id.tvProductPrice);
            tvProductType=itemView.findViewById(R.id.tvProductType);
            imgProduct=itemView.findViewById(R.id.imgProduct);
        }
    }
}
