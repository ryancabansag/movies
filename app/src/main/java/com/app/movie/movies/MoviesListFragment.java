package com.app.movie.movies;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.app.movie.movies.movie.MoviesContent;

import java.util.ArrayList;

public class MoviesListFragment extends ListFragment {


    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {
        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public MoviesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieListAdapter mla = new movieListAdapter(getActivity(), R.layout.fragment_list_view, (ArrayList<MoviesContent.MovieItems>) MoviesContent.ITEMS);
        mla.clear();
        setListAdapter(mla);


        Parser parser = new Parser(getActivity(),mla);
        parser.execute();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        mCallbacks.onItemSelected(MoviesContent.ITEMS.get(position).title);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }



    public class movieListAdapter extends ArrayAdapter<MoviesContent.MovieItems> {
        Context context;
        private ArrayList<MoviesContent.MovieItems> movies;

        public movieListAdapter(Context context, int id, ArrayList<MoviesContent.MovieItems> movies){
            super(context, id, movies);
            this.movies = movies;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if(v == null) {
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.fragment_list_view, null);
            }

            TextView title = (TextView)v.findViewById(R.id.title);
            TextView year = (TextView)v.findViewById(R.id.year);
            ImageView bg = (ImageView)v.findViewById(R.id.movie_list_poster);

            title.setText(MoviesContent.ITEMS.get(position).title);
            year.setText(MoviesContent.ITEMS.get(position).year);
            new ImageLoader(bg).execute("https://dl.dropboxusercontent.com/u/5624850/movielist/images/" + MoviesContent.ITEMS.get(position).slug + "-backdrop.jpg");

            return v;
        }
    }
}
