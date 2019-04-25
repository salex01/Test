package com.alex.weatherbugassignment.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aschwartzman on 10/27/17.
 */

public class WBApiClient {

    public static final String BASE_URL = "https://s3.amazonaws.com/sc.va.util.weatherbug.com/interviewdata/mobilecodingchallenge/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
