package com.alex.weatherbugassignment.ui;

/**
 * Created by aschwartzman on 9/16/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.weatherbugassignment.R;
import com.alex.weatherbugassignment.model.WBImage;
import com.alex.weatherbugassignment.services.WBApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aschwartzman on 9/27/17.
 */

public class WBImageAdapter extends RecyclerView.Adapter<WBImageAdapter.ImageViewHolder> {

    private List<WBImage> wbImages;
    public WBImageAdapter(ArrayList<WBImage>  list) {
        wbImages = list;
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder  {
        final private TextView description, title;
        final private ImageView ivPhoto;


        public ImageViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.image_row, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final WBImage image = wbImages.get(position);
        holder.title.setText(image.getTitle());
        holder.description.setText(image.getDescription());
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                 Intent i = new Intent(context, WBDetailActivity.class);
                 i.putExtra("title", image.getTitle());
                 i.putExtra("description", image.getDescription());
                 i.putExtra("filename", image.getFilename());
                context.startActivity(i);
            }
        });

        String imageUrl = WBApiClient.BASE_URL + image.getFilename();
        Glide.with(holder.ivPhoto.getContext())
                .load(imageUrl)
                .centerCrop()
                .override(128, 128)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPhoto);


    }

    public void setItems(List<WBImage> list) {
        this.wbImages = list;
    }

    @Override
    public int getItemCount() {
        return wbImages.size();
    }
}