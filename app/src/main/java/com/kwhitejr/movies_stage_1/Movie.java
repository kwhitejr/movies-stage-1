package com.kwhitejr.movies_stage_1;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.kwhitejr.movies_stage_1.Utilities.MovieQueryUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kwhi32 on 1/20/18.
 */

public class Movie implements Parcelable {
    private static final String LOG_TAG = Movie.class.getSimpleName();

    private String mTitle;
    private String mReleaseDate;
    private String mPosterPath;
    private String mOverview;
    private double mVoteAverage;

    private static final String POSTER_PATH_BASE = "http://image.tmdb.org/t/p";
    private static final String POSTER_PATH_SIZE = "w342";

    /**
     * Public constructor for the Movie class.
     * @param title of the Movie
     * @param releaseDate of the Movie
     * @param poster for the Movie
     * @param overview of the Movie
     * @param voteAverage for the Movie
     */
    public Movie(String title, String releaseDate, String poster, String overview, double voteAverage) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = poster;
        mOverview = overview;
        mVoteAverage = voteAverage;
    }

    public String getTitle() { return mTitle; }
    public String getReleaseDate() { return mReleaseDate; }
    public String getOverview() { return mOverview; }
    public double getVoteAverage() { return mVoteAverage; }

    /**
     *
     * @return the URL for the movie poster image
     */
    public URL getPosterPathUrl() {
        Uri builtUri = Uri.parse(POSTER_PATH_BASE).buildUpon()
                .appendPath(POSTER_PATH_SIZE)
                .appendPath(mPosterPath)
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
     *
     * @return the URL as String for the movie poster image
     */
    public String getPosterPathString() {
        String posterPathString = "";
        posterPathString += POSTER_PATH_BASE;
        posterPathString += "/" + POSTER_PATH_SIZE;
        posterPathString += "/" + mPosterPath;

        return posterPathString;
    }

    /* Implement Parcelable */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mOverview);
        parcel.writeString(mPosterPath);
        parcel.writeDouble(mVoteAverage);
    }

    private Movie(Parcel in) {
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mOverview = in.readString();
        mPosterPath = in.readString();
        mVoteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
