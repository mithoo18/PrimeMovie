package com.example.primemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.primemovie.MoviesDetails;
import com.example.primemovie.R;
import com.example.primemovie.model.BannerMovie;

import java.util.List;


public class BannerMoviesPagerAdapter extends PagerAdapter {


    Context context;
    List<BannerMovie> bannerMovieList;

    public BannerMoviesPagerAdapter(Context context, List<BannerMovie> bannerMovieList) {
        this.context = context;
        this.bannerMovieList = bannerMovieList;
    }

    @Override
    public int getCount() {
        return bannerMovieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout,null);
        ImageView bannerImage = view.findViewById(R.id.banner_image);
        //glide lib to fetch image from url
        Glide.with(context).load(bannerMovieList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "" + bannerMovieList.get(position).getMovieName(), Toast.LENGTH_SHORT).show();
             Intent i = new Intent(context, MoviesDetails.class);
                i.putExtra("movieId",bannerMovieList.get(position).getId());
                i.putExtra("movieName",bannerMovieList.get(position).getMovieName());
                i.putExtra("movieImageUrl",bannerMovieList.get(position).getImageUrl());
                i.putExtra("movieFile",bannerMovieList.get(position).getFileUrl());
                context.startActivity(i);
             }
        });

        return view;
    }
}


