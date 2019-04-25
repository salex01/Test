package com.alex.weatherbugassignment.services;

import java.util.List;
import com.alex.weatherbugassignment.model.WBImage;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aschwartzman on 10/27/17.
 */

public interface WBApiInterface {

    @GET("sampledata.json")
    Call<List<WBImage>> getImages();



}
