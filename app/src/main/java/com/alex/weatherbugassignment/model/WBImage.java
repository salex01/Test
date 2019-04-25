package com.alex.weatherbugassignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aschwartzman on 10/27/17.
 */

public class WBImage {
    @SerializedName("title")
    private String title;

    @SerializedName("filename")
    private String filename;

    @SerializedName("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String url;

}
