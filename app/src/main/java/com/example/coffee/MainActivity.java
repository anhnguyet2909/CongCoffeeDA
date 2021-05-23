package com.example.coffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Object.*;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    boolean flag=true;
    String email, password;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnLogin.setOnClickListener(v->{
            Log.d("test", "Clicked");
            if (checkValidation()) {
                if (CommonMethod.isNetworkAvailable(MainActivity.this))
                    loginRetrofit2Api(email , password);
                else
                    CommonMethod.showAlert("Internet Connectivity Failure", MainActivity.this);
            }
        });
        binding.etEmail.setText("nguyet@gmail.com");
        binding.etPass.setText("12345678");

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

    private void loginRetrofit2Api(String email, String password) {
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPass.getText().toString().trim();
        final User login = new User(email, password);
        apiInterface=APIClient.getClient().create(APIInterface.class);
        Call<User> call1 = apiInterface.createUser(login);
        call1.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                User user = response.body();

                Log.e("test", "loginResponse 1 --> " + user);
                    if (response.code()==400) {
                        Toast.makeText(MainActivity.this, "Invalid Login Details \n Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                        Singleton.getUniqInstance().setUser(user);
                        if(user.getRole()==2){
                            Intent i=new Intent(getBaseContext(), StaffLayout.class);
                            startActivity(i);
                        }
                        else if(user.getRole()==1){
                            Intent i=new Intent(getBaseContext(), AdminLayout.class);
                            startActivity(i);
                        }
                        else if(user.getRole()==3){
                            Intent i=new Intent(getBaseContext(), BartenderLayout.class);
                            startActivity(i);
                        }
                    }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("test", t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }

        });
    }


    public boolean checkValidation() {
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPass.getText().toString().trim();

        Log.e("test", "email -> " + email);
        Log.e("test", "password -> " + password);

        if (binding.etEmail.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("UserId Cannot be left blank", MainActivity.this);
            return false;
        } else if (binding.etPass.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("password Cannot be left blank", MainActivity.this);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}