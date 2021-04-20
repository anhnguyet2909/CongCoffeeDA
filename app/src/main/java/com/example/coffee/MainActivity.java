package com.example.coffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.coffee.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Object.*;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<User> list;
    boolean flag=true;
    User userCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                if(snapshot.hasChildren()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        User user=dataSnapshot.getValue(User.class);

                            list.add(user);

                        Singleton.getUniqInstance().setUserList(list);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnLogin.setOnClickListener(v->{
            String name=binding.etName.getText().toString().trim();
            String pass=binding.etPass.getText().toString().trim();
            if(!name.equals("") && !pass.equals("")){
                for(User user:list){
                    if(name.equals(user.getUsername()) && pass.equals(user.getPass())){
                        userCompare=new User(user.getId(), user.getName(), user.getUsername(),
                                user.getPass(), user.getPhone(), user.getImage(), user.getRole());
                        break;
                    }
                }
            }
            else{
                Toast.makeText(getBaseContext(), "Đăng nhập không thành công", Toast.LENGTH_LONG).show();
            }
            SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("staffImage", userCompare.getImage());
            editor.putString("staffName", userCompare.getName());
            editor.commit();
            if(userCompare.getRole()==10){
                Intent intent=new Intent(this, AdminLayout.class);
                startActivity(intent);
            }
            else if(userCompare.getRole()==1){
                Intent intent = new Intent(this, StaffLayout.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, BartenderLayout.class);
                startActivity(intent);
            }
        });

        binding.btnShowPass.setOnClickListener(v->{
            if(flag){
                binding.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.btnShowPass.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
                flag=false;
            }
            else{
                binding.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.btnShowPass.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_24));
                flag=true;
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}