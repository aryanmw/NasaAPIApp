package com.wadhavekar.nasainfo.api;

import com.wadhavekar.nasainfo.ModelObjects.AssetEndPoint.AssetResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceAsset {
    @GET("/asset/{nasa_id}")
    Call<AssetResponse> getAssetResponse(@Path("nasa_id") String nasa_id);
}
