package com.alex.weatherbugassignment.ui;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.weatherbugassignment.R;
import com.alex.weatherbugassignment.WBApplication;
import com.alex.weatherbugassignment.model.WBImage;
import com.alex.weatherbugassignment.services.WBApiClient;
import com.alex.weatherbugassignment.services.WBApiInterface;
import com.alex.weatherbugassignment.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aschwartzman on 10/27/17.
 */

public class WBMainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WBImageAdapter imageAdapter;
    private static final String TAG = "WBMainActivity";
    private WBApplication application;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.IMAGES));
        application = (WBApplication) getApplicationContext();

        progress = (ProgressBar) findViewById(R.id.loading_progress_xml);
        progress.setVisibility(View.VISIBLE);


        recyclerView = (RecyclerView) findViewById(R.id.rv_photos);
        imageAdapter = new WBImageAdapter(new ArrayList<WBImage>());
        GridLayoutManager layoutManager;
        int columns = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            columns = 1;
        }
        layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(imageAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        // check network
        if (!Utils.isConnected(this)) {
            showAlert(getString(R.string.NETWORK_ERROR));
            return;
        }

        List<WBImage> items = application.getItems();
        if (items == null || items.size()==0) { //first time launch, get Images info asynchronously
            getImageList();
        } else { //activity was restarted (due to rotation)
            dismissProgress();
            populateList(items);
        }
    }

    private void populateList(List<WBImage> images) {
        application.setItems(images);
        imageAdapter.setItems(images);
        imageAdapter.notifyDataSetChanged();
    }

    private void dismissProgress() {
        progress.setVisibility(View.GONE);
    }

    //get Images info asynchronously
    private void getImageList() {
        WBApiInterface apiService = WBApiClient.getClient().create(WBApiInterface.class);
        Call<List<WBImage>> call = apiService.getImages();
        call.enqueue(new Callback<List<WBImage>>() {
            @Override
            public void onResponse(Call<List<WBImage>> call, Response<List<WBImage>> response) {
                //Log.d(TAG, call.request().url().toString());
                dismissProgress();
                if (response.isSuccessful()) {
                    populateList(response.body());
                } else {
                    Log.d(TAG, "Response is not successful, responce code = " + response.code());
                    showAlert(getString(R.string.UNEXPECTED_ERROR));
                }
            }

            @Override
            public void onFailure(Call<List<WBImage>> call, Throwable t) {
                dismissProgress();
                Log.e(TAG, t.getLocalizedMessage());
                showAlert(getString(R.string.UNEXPECTED_ERROR));
            }
        });
    }


    private void showAlert(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this);
        alertDialog.setTitle("");
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(WBMainActivity.this, getString(R.string.EXITING), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.show();
    }

}
