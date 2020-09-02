package com.wadhavekar.nasainfo.api;

import com.wadhavekar.nasainfo.ModelObjects.APODEndPoint.APODResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceAPOD {
    @GET("/planetary/apod")
    Call<APODResponse> getResponse(@Query("api_key") String ApiKey, @Query("date") String date);
}
