package com.kwhitejr.movies_stage_1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kwhitejr.movies_stage_1.Utilities.MovieAsyncTask;
import com.kwhitejr.movies_stage_1.Utilities.MovieQueryUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        int gridColumns = calculateNoOfColumns(this);
        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, gridColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getApplicationContext(), new ArrayList<Movie>(), this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData("popular");
    }

    /**
     *
     * @param movie
     */
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieDetailsActivity.class;
        Intent startMovieDetailsActivityIntent = new Intent(context, destinationClass);

        Log.d(LOG_TAG, "Clicked movie title: " + movie.getTitle());

        startMovieDetailsActivityIntent.putExtra("movie", (Parcelable) movie);

//        startMovieDetailsActivityIntent.putExtra("title", movie.getTitle());
//        startMovieDetailsActivityIntent.putExtra("releaseDate", movie.getReleaseDate());
//        startMovieDetailsActivityIntent.putExtra("rating", Double.toString(movie.getVoteAverage()));
//        startMovieDetailsActivityIntent.putExtra("description", movie.getOverview());
//        startMovieDetailsActivityIntent.putExtra("posterPathString", movie.getPosterPathString());
        startActivity(startMovieDetailsActivityIntent);
    }


    /**
     * The AsyncTask to fetch all movie data.
     */
    public class MainActivityMovieAsyncTask extends MovieAsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                 showMovieDataView();
                 mMovieAdapter.setMovieData(movieData);
            } else {
                 showErrorMessage();
            }
        }
    }

    /* Create the menu */
    // TODO: refactor menu selector; why isn't "Sort" showing?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_popular:
                loadMovieData("popular");
                return true;

            case R.id.action_sort_rating:
                loadMovieData("top_rated");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Helper Functions */
    /**
     * Execute the fetch movie async task.
     * @param queryParam defines the type of movies to fetch.
     */
    private void loadMovieData(String queryParam) {
        showMovieDataView();

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            new MainActivityMovieAsyncTask().execute(queryParam);
        }
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
    /* End Helper Functions */
}
