package com.example.coffee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import Object.*;

public interface GetAPI {
    @GET("getUsers")
    Call<List<User>> getUser();

    @GET("getTea")
    Call<List<Product>> getTea();

    @GET("getCoffee")
    Call<List<Product>> getCoffee();

    @GET("getJuice")
    Call<List<Product>> getJuice();

    @GET("getOthers")
    Call<List<Product>> getOthers();

    @GET("getMaterial")
    Call<List<Material>> getMaterial();
}
