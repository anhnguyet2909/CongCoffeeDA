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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import Object.*;

public class FragmentAddProduct extends Fragment {
    FragmentAddProductBinding binding;
    int product_price, typeID=1;
    String product_image="", product_name, product_id;
    Uri imageUri;
    String nameUpload;
    FirebaseStorage firebaseStorage;
    StorageReference reference;
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

        if(typeID==1){
            product_image="https://i.imgur.com/5QvoRZs.jpg";
        }
        else if(typeID==2){
            product_image="https://i.imgur.com/4AcFqaj.jpg";
        }
        else if(typeID==3){
            product_image="https://i.imgur.com/soziymr.jpg";
        }
        else{
            product_image="https://i.imgur.com/rme5ZXi.jpg";
        }

        binding.btnAddProductConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_id=Constant.randomName(10);
                product_name=binding.etAddProductName.getText().toString();
                product_price=Integer.parseInt(binding.etAddProductPrice.getText().toString());
                if(!product_image.equals("")){
                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference=firebaseDatabase.getReference("products").child(product_id);
                    Product product=new Product(product_id, product_name, product_image, product_price, "", typeID);
                    databaseReference.setValue(product);

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
                uploadToFirebase();
                Log.d("imagePath", product_image);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
                Log.d("testImage", "load anh: "+e.getLocalizedMessage());
            }

        }
    }

    private void uploadToFirebase() {
        nameUpload= Constant.randomName(30);
        Log.d("testimage", "start");
        final ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setTitle("File Uploader");
        dialog.show();

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("nameUpload", nameUpload);
        editor.commit();

        firebaseStorage=FirebaseStorage.getInstance();
        reference=firebaseStorage.getReference("ProductImages").child(nameUpload);
        if(imageUri!=null){
            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            dialog.dismiss();
                            Toast.makeText(getContext(),"File Uploaded",Toast.LENGTH_LONG).show();
//                            Task<Uri> downloadURL = taskSnapshot.getStorage().getDownloadUrl();
//                            if(downloadURL.isSuccessful()){
//                                product_image=downloadURL.getResult().toString();
//                            }
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            float percent=(100f*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded: "+(int)percent+" %");


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Fail", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Log.d("testimage", e.getLocalizedMessage());

                        }
                    });
            Log.d("testimage", imageUri.toString());
        }
        else{
            Log.d("testimage", "File is null");
        }
        Log.d("testimage", "finish");
    }

}
