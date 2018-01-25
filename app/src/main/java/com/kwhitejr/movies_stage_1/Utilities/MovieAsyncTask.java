package com.kwhitejr.movies_stage_1.Utilities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.kwhitejr.movies_stage_1.Movie;
import com.kwhitejr.movies_stage_1.R;

import java.util.ArrayList;

/**
 * Created by kwhi32 on 1/24/18.
 */

public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>>{

    private static final String LOG_TAG = MovieAsyncTask.class.getSimpleName();

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {

        Log.d(LOG_TAG, "`strings` parameter: " + strings);

        // Default to `popular` movie query
        String requestParams = "popular";
        if (strings.length == 0) {
            requestParams = "popular";
        } else {
            requestParams = strings[0];
        }

        try {
            ArrayList<Movie> movieArrayList = MovieQueryUtils.fetchMovieData(requestParams);

            return movieArrayList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
