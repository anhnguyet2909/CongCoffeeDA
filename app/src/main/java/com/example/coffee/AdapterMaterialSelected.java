package com.example.coffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Object.*;

public class AdapterMaterialSelected extends RecyclerView.Adapter<AdapterMaterialSelected.ViewHolder> {
    List<Consum> list;
//    DatabaseHandler databaseHandler;
    onTextChanged onTextChanged;

    public AdapterMaterialSelected(List<Consum> list) {
        this.list = list;
    }

    public void setOnTextChanged(com.example.coffee.onTextChanged onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    @NonNull
    @Override
    public AdapterMaterialSelected.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
//        databaseHandler=new DatabaseHandler(parent.getContext());
        View view=inflater.inflate(R.layout.item_material_add_list, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMaterialSelected.ViewHolder holder, int position) {
        final Consum consum=list.get(position);
        Material material=null;
        if(material!=null){
            holder.tvMaterialNameSelected.setText(material.getName());
            holder.etAmountMaterialSelected.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    onTextChanged.onAddText(holder.etAmountMaterialSelected.getText().toString());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaterialNameSelected;
        EditText etAmountMaterialSelected;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaterialNameSelected=itemView.findViewById(R.id.tvMaterialNameSelected);
            etAmountMaterialSelected=itemView.findViewById(R.id.etAmountMaterialSelected);
        }
    }
}
