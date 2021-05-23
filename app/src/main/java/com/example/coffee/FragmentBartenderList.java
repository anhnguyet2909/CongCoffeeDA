package com.example.coffee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentBartenderListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Object.*;
import Object.APIInterface;
import Object.ProductOrder;
import Object.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBartenderList extends Fragment {
    FragmentBartenderListBinding binding;
    List<APIInterface.OrderInBarList> listTable;
    List<ShowOrderByTable> list;
    AdapterBartenderList adapter;
    List<ShowOrderByTable> completeList;

    public static FragmentBartenderList newInstance() {

        Bundle args = new Bundle();

        FragmentBartenderList fragment = new FragmentBartenderList();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bartender_list, container, false);

        listTable = new ArrayList<APIInterface.OrderInBarList>();
        list = new ArrayList<ShowOrderByTable>();
        completeList=new ArrayList<ShowOrderByTable>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<APIInterface.OrderToBar> call = apiInterface.getOrder();
        call.enqueue(new Callback<APIInterface.OrderToBar>() {
            @Override
            public void onResponse(Call<APIInterface.OrderToBar> call, Response<APIInterface.OrderToBar> response) {
                APIInterface.OrderToBar content = response.body();
                Log.d("jsonList", "content: " + content.items.size());
                Log.d("jsonList", "response: " + response.code());
                listTable = content.items;
                Log.d("jsonList", "items: " + listTable.size());
                for (APIInterface.OrderInBarList i : listTable) {
                    String tableName=i.name;
                    for (ProductOrder j : i.listProduct) {
                        ShowOrderByTable table=new ShowOrderByTable(j.getId(), j.getName(), j.getOption(), j.getImage(), j.getPrice(), j.getCount(), tableName);
                        list.add(table);
                        Log.d("json", j.toString());
                    }
                }
                adapter = new AdapterBartenderList(list);
                binding.rvList.setAdapter(adapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL, false);
                binding.rvList.setLayoutManager(layoutManager);
                adapter.setOnCompleteClick(v->{
                    completeList.add(v);
                    Singleton.getUniqInstance().setListTableOrder(completeList);
                    list.remove(v);
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(Call<APIInterface.OrderToBar> call, Throwable t) {
                Log.d("jsonList", t.getLocalizedMessage());
                Log.d("jsonList", t.getMessage());
            }
        });

        return binding.getRoot();
    }

}
