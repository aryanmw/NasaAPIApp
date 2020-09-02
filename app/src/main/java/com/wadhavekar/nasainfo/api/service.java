package com.wadhavekar.nasainfo.api;

import com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface service {
    @GET("/search")
    Call<SearchResponse> getItems(@Query("q") String q);

}
