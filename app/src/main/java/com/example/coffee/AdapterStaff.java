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
import Object.*;

import java.util.List;

public class AdapterStaff extends RecyclerView.Adapter<AdapterStaff.ViewHolder> {
    List<User> list;
    onStaffClick onStaffClick;

    public void setOnStaffClick(com.example.coffee.onStaffClick onStaffClick) {
        this.onStaffClick = onStaffClick;
    }

    public AdapterStaff(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterStaff.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_staff, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStaff.ViewHolder holder, int position) {
        final User user=list.get(position);
        Picasso.get().load(user.getImage()).into(holder.imgStaff);
//        Picasso.get().load(R.drawable.lucky).into(holder.imgStaff);
        holder.tvStaffName.setText(user.getName());
        String type="";
        StaffType[] a= StaffTypeDataUtils.getProductType();
        for(int i=0; i<a.length; i++){
            if(a[i].getId()==user.getRole())
                type=a[i].getName();
        }
        holder.tvStaffPosition.setText(type);
        holder.llStaffInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStaffClick.onShow(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tvStaffName, tvStaffPosition;
        ImageView imgStaff;
        LinearLayout llStaffInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStaffName=itemView.findViewById(R.id.tvStaffName);
            tvStaffPosition=itemView.findViewById(R.id.tvStaffPosition);
            imgStaff=itemView.findViewById(R.id.imgStaff);
            llStaffInfo=itemView.findViewById(R.id.llStaffInfo);
        }
    }
}
