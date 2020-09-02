package com.wadhavekar.nasainfo.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {
    public static final String BASE_URL = "https://images-api.nasa.gov";
    public static final String BASE_URL_APOD = "https://api.nasa.gov";
    public static Retrofit retrofit = null;
    public static Retrofit retrofitAPOD = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getAPOD(){
        if (retrofitAPOD == null){
            retrofitAPOD = new Retrofit.Builder()
                    .baseUrl(BASE_URL_APOD)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitAPOD;
    }
}
