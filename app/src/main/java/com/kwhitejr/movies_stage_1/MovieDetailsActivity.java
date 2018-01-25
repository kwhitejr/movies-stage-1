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
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra("movie");

                String releaseDate = mMovie.getReleaseDate();
                releaseDate = String.format(res.getString(R.string.movie_release_date), releaseDate);

                String rating = Double.toString(mMovie.getVoteAverage());
                rating = String.format(res.getString(R.string.movie_avg_rating), rating);

                Picasso.with(getApplicationContext()).load(mMovie.getPosterPathString()).into(mMoviePoster);
                mMovieTitleTextView.setText(mMovie.getTitle());
                mMovieReleaseDateTextView.setText(releaseDate);
                mMovieRatingTextView.setText(rating);
                mMovieDescriptionTextView.setText(mMovie.getOverview());
            }
        }
    }
}
