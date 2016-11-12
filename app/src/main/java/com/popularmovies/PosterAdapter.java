package com.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movie> mPosters;

    public PosterAdapter(Context context, List<Movie> posters) {
        mContext = context;
        mPosters = posters;
    }

    @Override
    public int getCount() {
        return mPosters.size();
    }

    @Override
    public Movie getItem(int position) {
        return mPosters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void clear(){
        mPosters.clear();
        notifyDataSetInvalidated();
    }

    public void addAll(List<Movie> newList) {
        mPosters.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mPosters.get(position).getPosterPath())
                .noFade()
                .resize(200,200)
                .centerCrop()
                .into(imageView);

        return imageView;
    }
}
