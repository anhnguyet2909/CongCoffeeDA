package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import Object.*;

public class AdapterShowBill extends RecyclerView.Adapter<AdapterShowBill.ViewHolder> {
    List<OrderList> list;
    List<Order> orderList;
    List<Product> productList;

    public AdapterShowBill(List<Product> list) {
        this.productList = list;
    }

    @NonNull
    @Override
    public AdapterShowBill.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_show_list_order, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShowBill.ViewHolder holder, int position) {
//        final OrderList orderList1=list.get(position);
        final Product product=productList.get(position);

        holder.tvSTT.setText(product.getId()+"");
        holder.tvOrderName.setText(product.getName());
        holder.tvOrderPrice.setText(product.getPrice()+"");
        holder.tvCount.setText(product.getTypeID()+"");
        holder.tvOrderTotalPrice.setText((product.getPrice()*product.getTypeID())+"");
//        for(Order i:orderList){
//            if(i.getTableID()==orderList1.getOrderID()) {
//                order=i;
//            }
//        }
//        if(order!=null){
//            holder.tvSTT.setText(position+"");
//            for (Product i:productList){
//                if(i.getId()==orderList1.getProductID()){
//                    product=i;
//                }
//            }
//            if(product!=null){
//                holder.tvOrderName.setText(product.getName());
//                holder.tvOrderPrice.setText(product.getPrice());
//                holder.tvOrderTotalPrice.setText((orderList1.getCount()*product.getPrice())+"");
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSTT, tvOrderName, tvOrderPrice, tvOrderTotalPrice, tvCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSTT=itemView.findViewById(R.id.tvSTT);
            tvOrderName=itemView.findViewById(R.id.tvOrderName);
            tvOrderPrice=itemView.findViewById(R.id.tvOrderPrice);
            tvOrderTotalPrice=itemView.findViewById(R.id.tvOrderTotalPrice);
            tvCount=itemView.findViewById(R.id.tvCount);
        }
    }
}
