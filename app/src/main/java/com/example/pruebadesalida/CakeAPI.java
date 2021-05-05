package com.example.pruebadesalida;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CakeAPI {
    @GET
    public Call<List<Cake>> getCakes(@Url String url);

    @GET
    public Call<CakeDetails> getCakeDetails(@Url String url);
}
