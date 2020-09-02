package com.wadhavekar.nasainfo.ModelObjects.AssetEndPoint;

import com.google.gson.annotations.SerializedName;

public class AssetResponse {
    @SerializedName("collection")
    AssetCollection assetCollection;

    public AssetCollection getAssetCollection() {
        return assetCollection;
    }

    public void setAssetCollection(AssetCollection assetCollection) {
        this.assetCollection = assetCollection;
    }
}
