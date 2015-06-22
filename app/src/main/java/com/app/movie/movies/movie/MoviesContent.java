package com.app.movie.movies.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesContent {

    public static List<MovieItems> ITEMS = new ArrayList<MovieItems>();

    public static Map<String, MovieItems> ITEM_MAP = new HashMap<String, MovieItems>();

    public static void addItem(MovieItems item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
    }

    public static class MovieItems {
        public String title;
        public String year;
        public String rating;
        public String genres;
        public String language;
        public String url;
        public String title_long;
        public String imdb_code;
        public String id;
        public String state;
        public String runtime;
        public String overview;
        public String slug;
        public String mpa_rating;

        public MovieItems(String title, String year, String rating, String genres, String language, String url,
                          String title_long, String imdb_code, String id, String state, String runtime,
                          String overview, String slug, String mpa_rating) {

            this.title = title;
            this.year = year;
            this.rating = rating;
            this.genres = genres;
            this.language = language;
            this.url = url;
            this.title_long = title_long;
            this.imdb_code = imdb_code;
            this.id = id;
            this.state = state;
            this.runtime = runtime;
            this.overview = overview;
            this.slug = slug;
            this.mpa_rating = mpa_rating;
        }

        @Override
        public String toString() {
            return title;
        }
    }

}
