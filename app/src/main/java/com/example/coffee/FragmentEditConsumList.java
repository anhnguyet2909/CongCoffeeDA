package com.example.coffee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentEditConsumListBinding;

import java.util.ArrayList;
import java.util.List;

import Object.*;

public class FragmentEditConsumList extends Fragment {
    FragmentEditConsumListBinding binding;

    List<String> listMaterialName;
//    DatabaseHandler databaseHandler;
    AdapterMaterialSelected adapterMaterialSelected;
    List<Consum> list;
    int material_id, key;


    public static FragmentEditConsumList newInstance() {

        Bundle args = new Bundle();

        FragmentEditConsumList fragment = new FragmentEditConsumList();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_consum_list, container, false);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                Context.MODE_PRIVATE);
        key=sharedPreferences.getInt("key", 0);
        int product_id=sharedPreferences.getInt("product_id", 0);
        list=new ArrayList<>();
//        databaseHandler=new DatabaseHandler(getContext());
//        listMaterialName=databaseHandler.getMaterialName();
        boolean[] booleans=new boolean[listMaterialName.size()];
        String[] lst=new String[listMaterialName.size()];
        for(int i=0; i<listMaterialName.size(); i++){
            booleans[i]=false;
            lst[i]=listMaterialName.get(i);
        }
        binding.tvAddConsumProduct.setOnClickListener(v->{

            AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                    .setTitle("Chọn nguyên liệu")
                    .setSingleChoiceItems(lst, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            material_id=i+1;
                            Consum consum=new Consum(1,material_id, product_id, 0, "");

                            list.add(consum);
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adapterMaterialSelected=new AdapterMaterialSelected(list);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),
                                    RecyclerView.VERTICAL, false);
                            binding.rvMaterialProduct.setLayoutManager(layoutManager);
                            binding.rvMaterialProduct.setAdapter(adapterMaterialSelected);
                            adapterMaterialSelected.setOnTextChanged(new onTextChanged() {
                                @Override
                                public void onAddText(String s) {
                                    Consum consum=new Consum(1, i+1, product_id,
                                            Float.parseFloat(s), "");
                                    list.set(i+1, consum);
//                                    if(!databaseHandler.checkConsum(material_id, 1))
//                                        databaseHandler.insertConsum(consum);
//                                    databaseHandler.updateConsum(material_id, Float.parseFloat(s));
                                }
                            });
                        }
                    })
                    .create();
            alertDialog.show();
        });
        binding.btnSaveMaterialProduct.setOnClickListener(v->{
            back();
        });
        binding.btnBackAddConsum.setOnClickListener(v->{
            back();
        });
        listMaterialName=new ArrayList<>();


        return binding.getRoot();
    }

    public void back(){
        if(key==1){
            Bundle bundle=new Bundle();
            FragmentAddProduct fragmentAddProduct=new FragmentAddProduct();
            fragmentAddProduct.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddProduct).commit();
        }
        else{
            Bundle bundle=new Bundle();
            FragmentEditProduct fragmentEditProduct=new FragmentEditProduct();
            fragmentEditProduct.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditProduct).commit();
        }
    }
}
