package com.example.primemovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MoviesDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName;
    Button playButton;
    String mName,mImage,mId,mFileUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        movieName = findViewById(R.id.movie_name);
        movieImage = findViewById(R.id.movie_image);
        playButton = findViewById(R.id.play_button);

        //getting data from INeat

        mId = getIntent().getStringExtra("movieId");
        mName = getIntent().getStringExtra("movieName");
        mImage = getIntent().getStringExtra("movieImageUrl");
        mFileUrl = getIntent().getStringExtra("movieFile");

        //set data
        Glide.with(this).load(mImage).into(movieImage);
        movieName.setText(mName);

        //play btn
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("2","setUpExoPlayer: 2");
                Intent i  = new Intent(MoviesDetails.this,VideoPlayerActivity.class);
                       i.putExtra("url",mFileUrl);
                       startActivity(i);
            }
        });
    }
}
