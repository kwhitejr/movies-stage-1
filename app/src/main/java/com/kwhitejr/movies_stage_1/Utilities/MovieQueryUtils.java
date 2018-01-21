package com.kwhitejr.movies_stage_1.Utilities;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kwhitejr.movies_stage_1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwhi32 on 1/14/18.
 */

public final class MovieQueryUtils {

    private static final String LOG_TAG = MovieQueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link MovieQueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name MovieQueryUtils (and an object instance of MovieQueryUtils is not needed).
     */
    private MovieQueryUtils() {
    }

    /**
     *
     * @param requestParams should be `popular` or `top_rated`
     * @return
     */
    public static ArrayList<Movie> fetchMovieData(String requestParams) {
        Log.d(LOG_TAG, "Inside of fetchMovieData.");

        if (requestParams != "popular" && requestParams != "top_rated") {
            Log.d(LOG_TAG, "fetch parameter is invalid");
            return null;
        }

        URL url = NetworkUtils.buildMoviesUrl(requestParams);
        Log.d(LOG_TAG, "Built Url: " + url.toString());
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            if (jsonResponse != null) {
                Log.d(LOG_TAG, "Data fetch successful: " + jsonResponse);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing the input stream: ", e);
            e.printStackTrace();
        }

        ArrayList<Movie> movies = extractMovies(jsonResponse);
        return movies;
    }

    private static ArrayList<Movie> extractMovies(String response) {
        if (TextUtils.isEmpty(response)) {
            return null;
        }

        /* Empty ArrayList to push Movies into */
        ArrayList<Movie> movies = new ArrayList<>();

        /* Parse the JSON response */
        try {
            /* TODO: Is it necessary for NetworkUtils to return a String that is then
            ** reconverted to a JSON object?
             */
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray movieArray = jsonResponse.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i); // the current movie in the loop
                String title = movie.getString("title");
                String releaseDate = movie.getString("release_date");
                String posterPath = movie.getString("poster_path");
                String overview = movie.getString("overview");
                double voteAverage = movie.getDouble("vote_average");

                /* Add current movie data to adapter */
                movies.add(new Movie(title, releaseDate, posterPath, overview, voteAverage));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        String testExtraction = movies.get(0).toString();
        Log.d(LOG_TAG, "ArrayList<Movie> first item: " + testExtraction);

        return movies;
    }

    public static String[] getSimpleMovieStringsFromJson(Context context, String movieJsonString)
            throws JSONException {
        /* Movies are returned in the top level `results` array */
        final String MOVIE_LIST = "results";

        /* Movie title */
        final String MOVIE_TITLE = "title";

        /* Movie release date */
        final String MOVIE_RELEASE_DATE = "release_date";

        /* Move poster image url */
        final String MOVIE_POSTER_PATH = "poster_path";

        /* Response object contains a status_code when returning an error */
        final String ERROR_MESSAGE_CODE = "status_code"; // Returns `34`?

        /* String array to hold each movie's data */
        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

        /* Is there an error? */
        if (movieJson.has(ERROR_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(ERROR_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);

        parsedMovieData = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            String title;
            String releaseDate;
            String posterImage;

            JSONObject movieObject = movieArray.getJSONObject(i);
            title = movieObject.getString(MOVIE_TITLE);
            releaseDate = movieObject.getString(MOVIE_RELEASE_DATE);
            posterImage = movieObject.getString(MOVIE_POSTER_PATH);

            parsedMovieData[i] = title + ", release " + releaseDate;
        }

        return parsedMovieData;
    }
}
