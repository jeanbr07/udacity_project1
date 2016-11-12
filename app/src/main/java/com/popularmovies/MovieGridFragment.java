package com.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.popularmovies.data.Movie;
import com.popularmovies.data.MoviesList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieGridFragment extends Fragment {

    public static final String EXTRA_MOVIE = "com.popularmovies.extra.MOVIE";

    private final Gson mGson = new Gson();

    private PosterAdapter mAdapter;

    public MovieGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAdapter = new PosterAdapter(getActivity(), new ArrayList<Movie>());
        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Movie movie = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(EXTRA_MOVIE, movie);
                startActivity(intent);
            }
        });

        gridView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPosters();
    }

    private void getPosters() {
        FetchPosterTask task = new FetchPosterTask();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
        task.execute(sortBy);
    }

    public class FetchPosterTask extends AsyncTask<String, Void, List<Movie>> {

        private final String LOG_TAG = FetchPosterTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {
            if(params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            List<Movie> movies = null;

            try {
                final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
                final String SORT_PARAM = params[0];
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                        .appendPath(SORT_PARAM)
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if(inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                movies = mGson.fromJson(reader, MoviesList.class).movies;
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }

                if(reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            if (result != null) {
                mAdapter.clear();
                mAdapter.addAll(result);
            }
        }
    }

}
