package com.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList {

    @SerializedName("results")
    public List<Movie> movies;

}
