package com.app.movie.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;



public class MoviesListActivity extends FragmentActivity
        implements MoviesListFragment.Callbacks {

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        if (findViewById(R.id.movies_detail_container) != null) {

            mTwoPane = true;

            ((MoviesListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.movies_list))
                    .setActivateOnItemClick(true);
        }

    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {

            Bundle arguments = new Bundle();
            arguments.putString(MoviesDetailFragment.ARG_ITEM_ID, id);
            MoviesDetailFragment fragment = new MoviesDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movies_detail_container, fragment)
                    .commit();

        } else {

            Intent detailIntent = new Intent(this, MoviesDetailActivity.class);
            detailIntent.putExtra(MoviesDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

}
