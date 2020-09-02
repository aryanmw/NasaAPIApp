package com.wadhavekar.nasainfo.ModelObjects.SearchEndPoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("nasa_id")
    @Expose
    private String nasaId;

    @SerializedName("media_type")
    @Expose
    private String mediaType;

    public Data(String title, String nasaId, String mediaType) {
        this.title = title;
        this.nasaId = nasaId;
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
