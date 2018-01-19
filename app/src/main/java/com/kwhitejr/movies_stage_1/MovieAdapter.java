package com.kwhitejr.movies_stage_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kwhi32 on 1/14/18.
 */

/**
 * {@link MovieAdapter} exposes a list of movies to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String[] mMovieData;

    // TODO: Declare MovieAdapterOnClickHandler;
    // TODO: Declare interface MovieAdapterOnClickHandler;

    // TODO: Declare MovieAdapter constructor
    public MovieAdapter() {

    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = (TextView) view.findViewById(R.id.tv_movie_data);
            view.setOnClickListener(this);
        }

        /**
         * Do XXX when child view is clicked.
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String movieText = mMovieData[adapterPosition];
            // TODO: attach ClickHandler
            return;
        }
    }

    /**
     * Called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType  ID for the desired layout.
     * @return A new MovieAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieAdapterViewHolder    The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movie = mMovieData[position];
        movieAdapterViewHolder.mMovieTextView.setText(movie);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) return 0;
        return mMovieData.length;
    }

    /**
     * Set movie data on the MovieAdapter.
     * @param movieData The new movie data to be displayed
     */
    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
