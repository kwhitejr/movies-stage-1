package com.kwhitejr.movies_stage_1;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kwhi32 on 1/21/18.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    // TODO: intent should populate mMovie with extra
    private Movie mMovie;
    private ImageView mMoviePoster;
    private TextView mMovieTitleTextView;
    private TextView mMovieReleaseDateTextView;
    private TextView mMovieRatingTextView;
    private TextView mMovieDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        mMoviePoster = (ImageView) findViewById(R.id.image_movie_details_poster);
        mMovieTitleTextView = (TextView) findViewById(R.id.tv_movie_details_title);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_details_release_date);
        mMovieRatingTextView = (TextView) findViewById(R.id.tv_movie_details_rating);
        mMovieDescriptionTextView = (TextView) findViewById(R.id.tv_movie_details_description);

        Intent intentThatStartedThisActivity = getIntent();
        Resources res = getResources();

        if (intentThatStartedThisActivity != null) {

            if (intentThatStartedThisActivity.hasExtra("posterPathString")) {
                String posterPath = intentThatStartedThisActivity.getStringExtra("posterPathString");

                Picasso.with(getApplicationContext()).load(posterPath).into(mMoviePoster);
            }

            if (intentThatStartedThisActivity.hasExtra("title")) {
                String title = intentThatStartedThisActivity.getStringExtra("title");
                mMovieTitleTextView.setText(title);
            }

            if (intentThatStartedThisActivity.hasExtra("releaseDate")) {
                String releaseDate = intentThatStartedThisActivity.getStringExtra("releaseDate");
                releaseDate = String.format(res.getString(R.string.movie_release_date), releaseDate);
                mMovieReleaseDateTextView.setText(releaseDate);
            }

            if (intentThatStartedThisActivity.hasExtra("rating")) {
                String rating = intentThatStartedThisActivity.getStringExtra("rating");
                rating = String.format(res.getString(R.string.movie_avg_rating), rating);
                mMovieRatingTextView.setText(rating);
            }

            if (intentThatStartedThisActivity.hasExtra("description")) {
                String description = intentThatStartedThisActivity.getStringExtra("description");
                mMovieDescriptionTextView.setText(description);
            }
        }
    }
}
