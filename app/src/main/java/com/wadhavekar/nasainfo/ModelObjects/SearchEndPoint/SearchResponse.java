package com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("collection")
    @Expose
    Collection collection;


    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }


}
