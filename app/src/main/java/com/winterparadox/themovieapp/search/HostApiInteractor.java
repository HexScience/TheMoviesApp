package com.winterparadox.themovieapp.search;

import com.winterparadox.themovieapp.common.beans.Chart;

import java.util.List;

import io.reactivex.Single;

public interface HostApiInteractor {

    Single<List<Chart>> generes ();

    Single<Chart> popularMovieBackdrop (Chart chart);

    Single<Chart> latestMovieBackdrop (Chart chart);

    Single<Chart> topRatedMovieBackdrop (Chart chart);

    Single<Chart> genreMovieBackdrop (Chart chart);
}
