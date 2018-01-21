package com.kwhitejr.movies_stage_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwhitejr.movies_stage_1.Utilities.MovieQueryUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kwhi32 on 1/14/18.
 */

/**
 * {@link MovieAdapter} exposes a list of movies to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<Movie> mMovies;
    private Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mMovies = movies;
        mClickHandler = clickHandler;
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
        movieAdapterViewHolder.bindMovieData(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        return mMovies.size();
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO: How to use this `@Bind` functionality?
        // @Bind(R.id.tv_movie_data) TextView mMovieTextView;
        public final TextView mMovieTitleTextView;
        public final TextView mMovieVoteAvgTextView;
        public final ImageView mMoviePosterImageView;
        private Context mContext;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieTitleTextView = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mMovieVoteAvgTextView = (TextView) itemView.findViewById(R.id.tv_movie_vote_avg);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.image_movie_poster);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindMovieData(Movie movie) {
            double voteAvgDouble = movie.getVoteAverage();
            mMovieTitleTextView.setText(movie.getTitle());
            mMovieVoteAvgTextView.setText(Double.toString(voteAvgDouble));

            Picasso.with(mContext).load(movie.getPosterPathString()).into(mMoviePosterImageView);
        }

        /**
         * Do XXX when child view is clicked.
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    /**
     * Set movie data on the MovieAdapter.
     * @param movieData The new movie data to be displayed
     */
    public void setMovieData(ArrayList<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }
}
