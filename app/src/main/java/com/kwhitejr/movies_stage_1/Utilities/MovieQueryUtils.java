package com.kwhitejr.movies_stage_1.Utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by kwhi32 on 1/14/18.
 */

public final class MovieJsonUtils {

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
