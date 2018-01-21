package com.kwhitejr.movies_stage_1.Utilities;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.kwhitejr.movies_stage_1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by kwhi32 on 1/14/18.
 */

public final class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_MOVIE_URL_V3 = "https://api.themoviedb.org/3";
    private static final String BASE_MOVIE_URL_V4 = "https://api.themoviedb.org/4";

    private static final String API_DOMAIN = "movie";
    private static final String API_KEY_QUERY_PARAM = "api_key";
    private static final String API_KEY_VALUE = BuildConfig.MOVIE_API_KEY;

//    private static final String POPULAR = "popular";
//    private static final String TOP_RATED = "top_rated";

    /**
     * Builds the URL to get movies of type.
     *
     * @param typeQuery the type of movies to be queried. `popular` or `top_rated`
     * @return The URL to use to query the movie db service.
     */
    // NOTE: currently hardcoded for v3; make dynamic
    public static URL buildMoviesUrl(String typeQuery) {
        Uri builtUri = Uri.parse(BASE_MOVIE_URL_V3).buildUpon()
                .appendPath(API_DOMAIN)
                .appendPath(typeQuery)
                .appendQueryParameter(API_KEY_QUERY_PARAM, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d(LOG_TAG, "Built url: " + url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
