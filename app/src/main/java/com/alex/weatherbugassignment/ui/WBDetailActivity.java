package com.alex.weatherbugassignment.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.weatherbugassignment.R;
import com.alex.weatherbugassignment.services.WBApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by aschwartzman on 10/27/17.
 */

public class WBDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ivImage = (ImageView) findViewById(R.id.iv_detail_image);
        TextView tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        TextView tvDescription = (TextView) findViewById(R.id.tv_detail_description);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");

        tvTitle.setText(title);
        tvDescription.setText(description);

        //Don't support images on detail screen for now, since it's not in requirements
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ivImage.setVisibility(View.GONE);
        } else {
            ivImage.setVisibility(View.VISIBLE);
            String imageUrl = WBApiClient.BASE_URL + intent.getStringExtra("filename");
            Glide.with(this)
                    .load(imageUrl)
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
