package com.wadhavekar.nasainfo.ModelObjects.AssetEndPoint;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssetCollection {
    @SerializedName("items")
    List<AssetItem> assetItemList;

    public List<AssetItem> getAssetItemList() {
        return assetItemList;
    }

    public void setAssetItemList(List<AssetItem> assetItemList) {
        this.assetItemList = assetItemList;
    }
}
