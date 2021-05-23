package com.example.coffee;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.coffee.databinding.FragmentAddProductBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Object.*;
import Object.Product;
import Object.ProductType;
import Object.ProductTypeDataUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddProduct extends Fragment {
    FragmentAddProductBinding binding;
    int product_price, typeID=1;
    String product_image="", product_name, product_id;
    Uri imageUri;
    APIInterface apiClient = APIClient.getClient().create(APIInterface.class);
    public static FragmentAddProduct newInstance() {

        Bundle args = new Bundle();

        FragmentAddProduct fragment = new FragmentAddProduct();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false);
        binding.imgAddProductImage.setOnClickListener(v->{
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            startActivityForResult(i, 1);
        });
        binding.btnBackAddProduct.setOnClickListener(v->{
            Bundle bundle=new Bundle();
            FragmentAdminProduct fragmentAdminProduct=new FragmentAdminProduct();
            fragmentAdminProduct.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminProduct).commit();
        });
        binding.btnAddOptions.setOnClickListener(v->{
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("key", 1);
            editor.commit();
            Bundle bundle=new Bundle();
            FragmentAddOptions fragmentAddOptions=new FragmentAddOptions();
            fragmentAddOptions.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAddOptions).commit();
        });
        binding.btnAddMaterial.setOnClickListener(v->{
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("key", 1);
            editor.commit();
            Bundle bundle=new Bundle();
            FragmentEditConsumList fragmentEditConsumList=new FragmentEditConsumList();
            fragmentEditConsumList.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentEditConsumList).commit();
        });
        ProductType[] type= ProductTypeDataUtils.getProductType();
        ArrayAdapter<ProductType> adapter=new ArrayAdapter<ProductType>(getContext(),
                R.layout.spinner_layout, type);
        binding.spType.setAdapter(adapter);
        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeID=type[i].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeID=1;
            }
        });


        binding.btnAddProductConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_id=Constant.randomName(10);
                product_name=binding.etAddProductName.getText().toString();
                product_price=Integer.parseInt(binding.etAddProductPrice.getText().toString());
                List<Options> list=new ArrayList<Options>();
                Options options=new Options(0, "Take away", product_price);
                list.add(options);
                if (typeID==1){
                    product_image="/api/images/product/cycvI6M50oHBegj.jpg";
                }
                else if(typeID==2){
                    product_image="/api/images/product/mDlnfluVhyvrYH2.jpg";
                }
                else if(typeID==4){
                    product_image="/api/images/product/sbASLK5btE1kTQa.jpg";
                }
                if(!product_image.equals("")){
                    Product product=new Product(product_name, product_image, list, typeID);
                    Call<APIInterface.ProductCreateResponse> call1=apiClient.createProduct(product);
                    call1.enqueue(new Callback<APIInterface.ProductCreateResponse>() {
                        @Override
                        public void onResponse(Call<APIInterface.ProductCreateResponse> call, Response<APIInterface.ProductCreateResponse> response) {
                            Log.d("CreateProduct", "Successful");
                            Log.d("CreateProduct", "onResponse: "+product.toString());
                        }

                        @Override
                        public void onFailure(Call<APIInterface.ProductCreateResponse> call, Throwable t) {
                            Log.d("CreateProduct", "Failed");
                            Log.d("CreateProduct", "onFailed: "+product.toString());
                        }
                    });

                    Bundle bundle=new Bundle();
                    FragmentAdminProduct fragmentAdminProduct=new FragmentAdminProduct();
                    fragmentAdminProduct.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.admin_fragment, fragmentAdminProduct).commit();
                }
                else{
                    Toast.makeText(getContext(), "Thêm hình ảnh sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && null != data) {
            try{
                imageUri = data.getData();
                InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                binding.imgAddProductImage.setImageBitmap(selectedImage);
                binding.imgCamera.setVisibility(View.INVISIBLE);
                product_image=imageUri.toString();

                String s=getRealPathFromURI(imageUri);
                Log.d("Image", s);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
                Log.d("testImage", "load anh: "+e.getLocalizedMessage());
            }
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
// can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery( contentUri,
                proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

}
