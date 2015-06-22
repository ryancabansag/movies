package com.app.movie.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.movie.movies.movie.MoviesContent;

public class MoviesDetailFragment extends Fragment {

    ImageView movie_bg;

    public static final String ARG_ITEM_ID = "item_id";
    
    private MoviesContent.MovieItems mItem;

    public MoviesDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = MoviesContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_details, container, false);

        movie_bg = (ImageView)rootView.findViewById(R.id.movie_poster);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)movie_bg.getLayoutParams();
        params.width = 200;
        movie_bg.setLayoutParams(params);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.title)).setText(mItem.title);
            ((TextView) rootView.findViewById(R.id.year)).setText(mItem.year);
            ((TextView) rootView.findViewById(R.id.rating)).setText("Ratings: "+mItem.rating);
            ((TextView) rootView.findViewById(R.id.description)).setText("Overview:\n\n\t\t"+mItem.overview);
            new ImageLoader((ImageView)rootView.findViewById(R.id.movie_poster)).execute("https://dl.dropboxusercontent.com/u/5624850/movielist/images/"+mItem.slug+"-cover.jpg");
            new ImageLoader((ImageView)rootView.findViewById(R.id.movie_background)).execute("https://dl.dropboxusercontent.com/u/5624850/movielist/images/"+mItem.slug+"-backdrop.jpg");
        }

        return rootView;
    }

}
