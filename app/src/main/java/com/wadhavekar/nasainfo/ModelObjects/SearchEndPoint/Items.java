package com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint.Data;

import java.util.List;

public class Items {
    @SerializedName("data")
    @Expose
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
