package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Object.Material;

public class AdapterMaterial extends RecyclerView.Adapter<AdapterMaterial.ViewHolder> {
    List<Material> list;
    onMaterialClick onMaterialClick;

    public AdapterMaterial(List<Material> list) {
        this.list = list;
    }

    public void setOnMaterialClick(onMaterialClick onMaterialClick) {
        this.onMaterialClick = onMaterialClick;
    }

    @NonNull
    @Override
    public AdapterMaterial.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_material, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMaterial.ViewHolder holder, int position) {
        final Material material=list.get(position);
        holder.llViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMaterialClick.onClickMaterial(material);
            }
        });
        holder.tvMaterialID.setText((position+1)+"");
        holder.tvMaterialName.setText(material.getName());
        holder.tvMaterialAmount.setText(material.getAmount()+"");
        holder.tvMaterialUnit.setText(material.getUnit());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llViewMaterial;
        TextView tvMaterialID, tvMaterialName, tvMaterialAmount, tvMaterialUnit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llViewMaterial=itemView.findViewById(R.id.llViewMaterial);
            tvMaterialID=itemView.findViewById(R.id.tvMaterialID);
            tvMaterialName=itemView.findViewById(R.id.tvMaterialName);
            tvMaterialAmount=itemView.findViewById(R.id.tvMaterialAmount);
            tvMaterialUnit=itemView.findViewById(R.id.tvMaterialUnit);
        }
    }
}
