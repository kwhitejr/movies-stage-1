package com.kwhitejr.movies_stage_1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kwhitejr.movies_stage_1.Utilities.MovieQueryUtils;
import com.kwhitejr.movies_stage_1.Utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getApplicationContext(), new ArrayList<Movie>());
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData("popular"); // this parameter doesn't go anywhere yet
    }

    private void loadMovieData(String queryParam) {
        Log.d(LOG_TAG, "Inside of loadMovieData.");

        // TODO: how to pass the query param to the AsyncTask?
        showMovieDataView();
        new FetchMoviesTask().execute();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {

            Log.d(LOG_TAG, "Inside of doInBackground.");
            Log.d(LOG_TAG, "`strings` parameter: " + strings);

            String requestParams;
            // Default to `popular` movie query
            if (strings.length == 0) {
                requestParams = "popular";
            } else {
                requestParams = strings[0];
            }
//            URL movieRequestUrl = NetworkUtils.buildMoviesUrl(requestParams);

            try {
//                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                // TODO: remove hardcode requestParams
                ArrayList<Movie> movieArrayList = MovieQueryUtils.fetchMovieData("popular");

                return movieArrayList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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

    /* Helper Functions */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
