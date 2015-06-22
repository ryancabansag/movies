package com.app.movie.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.app.movie.movies.movie.MoviesContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ryan on 6/20/2015.
 */
public class Parser extends AsyncTask<Void, Void, Void> {

    private Context context;
    private ArrayAdapter adapter;
    ProgressDialog progressDialog;

    private static String url = "https://dl.dropboxusercontent.com/u/5624850/movielist/list_movies_page1.json";

    private static final String TAG_DATA = "data";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_RATING = "rating";
    private static final String TAG_GENRES = "genres";
    private static final String TAG_LANGUAGE = "language";
    private static final String TAG_TITLE = "title";
    private static final String TAG_URL = "url";
    private static final String TAG_TITLE_LONG = "title_long";
    private static final String TAG_IMDB_CODE = "imdb_code";
    private static final String TAG_ID = "id";
    private static final String TAG_STATE = "state";
    private static final String TAG_YEAR = "year";
    private static final String TAG_RUNTIME = "runtime";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_SLUG = "slug";
    private static final String TAG_MPA_RATING = "mpa_rating";

    JSONObject data = null;
    JSONArray movies = null;

    ServiceHandler sh = new ServiceHandler();

    public Parser(Context context,ArrayAdapter adapter) {
        this.context = context;
        this.adapter=adapter;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {

        MoviesContent dummy = new MoviesContent();
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        Log.d("Response: ", ">" + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);

                data = jsonObject.getJSONObject(TAG_DATA);
                movies = data.getJSONArray(TAG_MOVIES);

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject m = movies.getJSONObject(i);

                    String title = m.getString(TAG_TITLE);
                    String year = m.getString(TAG_YEAR);
                    String rating = m.getString(TAG_RATING);
                    String genres = m.getString(TAG_GENRES);
                    String language = m.getString(TAG_LANGUAGE);
                    String url = m.getString(TAG_URL);
                    String title_long = m.getString(TAG_TITLE_LONG);
                    String imdb_code = m.getString(TAG_IMDB_CODE);
                    String id = m.getString(TAG_ID);
                    String state = m.getString(TAG_STATE);
                    String runtime = m.getString(TAG_RUNTIME);
                    String overview = m.getString(TAG_OVERVIEW);
                    String slug = m.getString(TAG_SLUG);
                    String mpa_rating = m.getString(TAG_MPA_RATING);


                    dummy.addItem(new MoviesContent.MovieItems(title,year,rating,genres,language,url,title_long,
                            imdb_code,id,state,runtime,overview,slug,mpa_rating));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Service Handler", "Couldn't get any data from the url");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        adapter.notifyDataSetChanged();
        if (this.progressDialog.isShowing())
            this.progressDialog.dismiss();
    }


}
