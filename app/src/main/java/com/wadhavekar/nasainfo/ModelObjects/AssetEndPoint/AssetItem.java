package com.wadhavekar.nasainfo.ModelObjects.AssetEndPoint;

import com.google.gson.annotations.SerializedName;

public class AssetItem {
    @SerializedName("href")
    String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
