package com.example.alc_challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads)
    {
        mContext=context;
        mUploads=uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item, viewGroup,false);
        return  new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        Upload uploadCur=mUploads.get(i);
        imageViewHolder.img_description.setText(uploadCur.getImgName());


        //this na the place to put other guys





        Picasso.get()
                .load(uploadCur.getImgUrl())
                .placeholder(R.drawable.imagepreview)
                .fit()
                .centerCrop()
                .into(imageViewHolder.image_view);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView img_description;
        public ImageView image_view;
        public TextView image_Location;
        public TextView image_cost;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img_description=itemView.findViewById(R.id.img_description);
            image_view=itemView.findViewById(R.id.image_view);
            image_Location=itemView.findViewById(R.id.Image_Location);
            image_cost=itemView.findViewById(R.id.Image_Cost);
        }
    }
}
