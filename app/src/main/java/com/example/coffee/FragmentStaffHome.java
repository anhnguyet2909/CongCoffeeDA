package com.example.coffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee.databinding.FragmentStaffHomeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import Object.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStaffHome extends Fragment {
    FragmentStaffHomeBinding binding;
    ImageAdapter imageAdapter;
    List<Image> imageList;
    List<Product> listCoffee, listTea, listJuice, listOthers, list;
    AdapterSaleProduct adapterCoffee, adapterTea, adapterJuice, adapterOthers;
    Timer timer;
    String product_id, product_name, product_image;
    int product_price;
    TextView tvShowProductName, tvShowProductPrice, btnSubtract, btnAdd, tvCount;
    ImageView imgShowProduct;
    int count;
    List<ProductOrder> listProductOrder;

    public static FragmentStaffHome newInstance() {

        Bundle args = new Bundle();

        FragmentStaffHome fragment = new FragmentStaffHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_staff_home, container, false);

        listProductOrder =Singleton.getUniqInstance().getList();
        imageList=getListImage();
        imageAdapter=new ImageAdapter(getContext(), imageList);
        binding.viewPhoto.setAdapter(imageAdapter);
        binding.circleIndicator.setViewPager(binding.viewPhoto);
        imageAdapter.registerDataSetObserver(binding.circleIndicator.getDataSetObserver());
        autoSlide();

        list=new ArrayList<Product>();
        listCoffee=new ArrayList<Product>();
        listJuice=new ArrayList<Product>();
        listTea=new ArrayList<Product>();
        listOthers=new ArrayList<Product>();
        loadCategoryTitle();
        loadProductList();
        return binding.getRoot();
    }

    private void loadCategoryTitle() {
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<APIInterface.ListCategory> call = apiInterface.getCategories();
        call.enqueue(new Callback<APIInterface.ListCategory>() {
            @Override
            public void onResponse(Call<APIInterface.ListCategory> call, Response<APIInterface.ListCategory> response) {
                APIInterface.ListCategory data = response.body();
                List<APIInterface.Category> categories = data.categories;
                for (APIInterface.Category cat : categories) {
                    Log.d("TAG123", "onResponse: cat:"+cat.name+"-->"+cat.id);
                }
            }

            @Override
            public void onFailure(Call<APIInterface.ListCategory> call, Throwable t) {
                Log.d("TAG", "onFailure: Load category fail:"+t.getMessage());
            }
        });
    }


    private void loadProductList() {
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<APIInterface.ListProduct> call=apiInterface.getListProduct();
        call.enqueue(new Callback<APIInterface.ListProduct>() {
            @Override
            public void onResponse(Call<APIInterface.ListProduct> call, Response<APIInterface.ListProduct> response) {
                APIInterface.ListProduct content = response.body();
                list=content.items;
                Log.d("TAG123", "onResponse: items.size:"+content.items.size());
                Log.d("TAG123", "onResponse: code:"+response.code());

                // cho nay tach category nhu the nay khong on
                // 1. categoryId co the tahy doi
                // 2. category name do admin qd va co the chinh sua
                // khong phai categoryId luon la 1 2 3 4, co the la so khac
                // tuy theo viec chinh sua category o tren database

                for(Product i:list){
                    switch (i.getCategoryId()){
                        case 1:
                            listCoffee.add(i);
                            break;
                        case 2:
                            listTea.add(i);
                            break;
                        case 3:
                            listJuice.add(i);
                            break;
                        case 4:
                            listOthers.add(i);
                            break;
                    }
                }
                allAdapter();
            }

            @Override
            public void onFailure(Call<APIInterface.ListProduct> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Test", t.getMessage());//
            }
        });
    }

    public void alertDialog(){
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.dialog_show_product, null, false);
        tvShowProductName=view.findViewById(R.id.tvShowProductName);
        tvShowProductPrice= view.findViewById(R.id.tvShowProductPrice);
        imgShowProduct=view.findViewById(R.id.imgShowProduct);
        btnAdd=view.findViewById(R.id.btnAdd);
        btnSubtract=view.findViewById(R.id.btnSubtract);
        tvCount=view.findViewById(R.id.tvCount);
        tvShowProductPrice.setText(product_price+"");
        tvShowProductName.setText(product_name);
        Picasso.get().load(product_image).into(imgShowProduct);
        count=Integer.parseInt(tvCount.getText().toString());
        btnSubtract.setOnClickListener(v->{
            count-=1;
            tvCount.setText(count+"");
        });
        btnAdd.setOnClickListener(v->{
            count+=1;
            tvCount.setText(count+"");
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProductOrder productOrder=new ProductOrder(product_id, product_name, "", product_image, product_price, count);
                if(count>0){
                    listProductOrder.add(productOrder);
                   // Singleton.getUniqInstance().setList(listProductOrder); // ko can
                    Log.d("TAG123", "onClick: add product ok  -> size:"+Singleton.getUniqInstance().getList().size());
                }
            }
        })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        builder.show();
    }

    private List<Image> getListImage() {
        List<Image> imageList=new ArrayList<>();
        imageList.add(new Image(R.drawable.banner1));
        imageList.add(new Image(R.drawable.banner2));
        imageList.add(new Image(R.drawable.banner3));
        imageList.add(new Image(R.drawable.banner4));
        imageList.add(new Image(R.drawable.banner5));
        return imageList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void autoSlide() {
        if (imageList == null || imageList.isEmpty() || binding.viewPhoto == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = binding.viewPhoto.getCurrentItem();
                        int totalItem = imageList.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            binding.viewPhoto.setCurrentItem(currentItem);
                        } else {
                            binding.viewPhoto.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);

    }

    public void allAdapter(){
        //Coffee
        adapterCoffee=new AdapterSaleProduct(listCoffee);
        Log.d("TAG123", "allAdapter: listCoffee.size():"+listCoffee.size());
        Log.d("TAG123", "allAdapter: listTea.size():"+listTea.size());
        Log.d("TAG123", "allAdapter: listJuice.size():"+listJuice.size());
        Log.d("TAG123", "allAdapter: listOthers.size():"+listOthers.size());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        binding.rvCoffee.setLayoutManager(layoutManager);
        binding.rvCoffee.setAdapter(adapterCoffee);
        adapterCoffee.setOnSaleProduct(new onSaleProduct() {
            @Override
            public void onShowProduct(Product product) {
                product_id=product.getId();
                product_image=product.getFullLinkImage();
                product_name=product.getName();
                product_price=product.getPrice();
                alertDialog();
            }
        });

        //Tea
        adapterTea=new AdapterSaleProduct(listTea);
        layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        binding.rvTea.setLayoutManager(layoutManager);
        binding.rvTea.setAdapter(adapterTea);
        adapterTea.setOnSaleProduct(new onSaleProduct() {

            @Override
            public void onShowProduct(Product product) {
                product_id=product.getId();
                product_image=product.getFullLinkImage();
                product_name=product.getName();
                product_price=product.getPrice();
                alertDialog();
            }
        });

        //Juice
        adapterJuice=new AdapterSaleProduct(listJuice);
        layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        binding.rvJuice.setLayoutManager(layoutManager);
        binding.rvJuice.setAdapter(adapterJuice);
        adapterJuice.setOnSaleProduct(new onSaleProduct() {
            @Override
            public void onShowProduct(Product product) {
                product_id=product.getId();
                product_image=product.getFullLinkImage();
                product_name=product.getName();
                product_price=product.getPrice();
                alertDialog();
            }
        });

        //Others
        adapterOthers=new AdapterSaleProduct(listOthers);
        layoutManager=new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);
        binding.rvOthers.setLayoutManager(layoutManager);
        binding.rvOthers.setAdapter(adapterOthers);
        adapterOthers.setOnSaleProduct(new onSaleProduct() {
            @Override
            public void onShowProduct(Product product) {
                product_id=product.getId();
                product_image=product.getFullLinkImage();
                product_name=product.getName();
                product_price=product.getPrice();
                alertDialog();
            }
        });
    }
}
