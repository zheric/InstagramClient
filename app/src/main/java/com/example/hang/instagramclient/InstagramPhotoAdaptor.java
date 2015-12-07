package com.example.hang.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivPhoto);

        tvCaption.setText(photo.username+ ": " + photo.caption);
        imageView.setImageResource(0); // clears previous image
        Picasso.with(getContext()).load(photo.imageUrl).into(imageView);
        return convertView;
    }
}
