package com.example.hang.instagramclient;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hang on 12/6/15.
 */
public class InstagramPhotoAdaptor extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotoAdaptor(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get dataitem for this position
        InstagramPhoto photo = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        //ImageView profileImageView = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        RoundedImageView profileImageView = (RoundedImageView)convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        TextView tvLikeCount = (TextView)convertView.findViewById(R.id.tvLikeCount);
        TextView tvTimeStamp = (TextView)convertView.findViewById(R.id.tvTimpStamp);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivPhoto);

        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvLikeCount.setText(photo.likesCount + " likes");
        tvTimeStamp.setText(photo.timestamp);


        profileImageView.setImageResource(0);
        imageView.setImageResource(0); // clears previous image
        Picasso.with(getContext()).load(photo.profileImageUrl).into(profileImageView);
        Picasso.with(getContext()).load(photo.imageUrl).into(imageView);
        return convertView;
    }
}
