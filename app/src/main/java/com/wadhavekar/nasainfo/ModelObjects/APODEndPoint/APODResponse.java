package com.wadhavekar.nasainfo.ModelObjects.APODEndPoint;

import com.google.gson.annotations.SerializedName;

public class APODResponse {
    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("media_type")
    private String mediaType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
