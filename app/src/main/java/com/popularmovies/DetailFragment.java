package com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import static com.popularmovies.MovieGridFragment.EXTRA_MOVIE;

public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private Movie mMovie;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE)) {
            mMovie = intent.getParcelableExtra(EXTRA_MOVIE);
           ((TextView) rootView.findViewById(R.id.tv_original_title)).setText(mMovie.originalTitle);
           ((TextView) rootView.findViewById(R.id.tv_overview)).setText(mMovie.overView);
           ((TextView) rootView.findViewById(R.id.tv_rating)).setText(String.valueOf(mMovie.voteAverage));
           ((TextView) rootView.findViewById(R.id.tv_release_date)).setText(mMovie.releaseDate);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_poster);
            Picasso.with(getContext()).load(mMovie.getPosterPath()).noFade().centerCrop().resize(150,200).into(imageView);
        }

        return rootView;
    }

}
