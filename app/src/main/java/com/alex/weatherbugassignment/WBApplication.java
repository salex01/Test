package com.alex.weatherbugassignment;

import android.app.Application;

import com.alex.weatherbugassignment.model.WBImage;

import java.util.List;

/**
 * Created by aschwartzman on 10/27/17.
 */

public class WBApplication extends Application {
    private List<WBImage> images;

    public List<WBImage> getItems() {
        return images;
    }
    public void setItems(List<WBImage> items) {
        this.images = items;
    }

}
