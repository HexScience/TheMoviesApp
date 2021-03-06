package com.winterparadox.themovieapp.search;

import com.winterparadox.themovieapp.common.apiServices.ChartsApiService;
import com.winterparadox.themovieapp.common.beans.Chart;
import com.winterparadox.themovieapp.common.beans.Movie;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class HostApiInteractorImpl implements HostApiInteractor {

    private final ChartsApiService chartService;
    private ConfigurationApiService configService;

    public HostApiInteractorImpl (ConfigurationApiService configService, ChartsApiService
            chartsService) {

        this.configService = configService;
        this.chartService = chartsService;
    }

    @Override
    public Single<List<Chart>> generes () {
        return configService.genres ()
                .subscribeOn (Schedulers.io ())
                .map (genresResponse -> genresResponse.genres);
    }

    @Override
    public Single<Chart> popularMovieBackdrop (Chart chart) {
        return chartService.popular (1)
                .subscribeOn (Schedulers.io ())
                .map (moviesResponse -> {
                    Movie movie = moviesResponse.results.get (0);
                    chart.backDropPath = movie.backdropPath;
                    return chart;
                });
    }

    @Override
    public Single<Chart> latestMovieBackdrop (Chart chart) {
        return chartService.latest (1)
                .subscribeOn (Schedulers.io ())
                .map (moviesResponse -> {
                    Movie movie = moviesResponse.results.get (0);
                    chart.backDropPath = movie.backdropPath;
                    return chart;
                });
    }

    @Override
    public Single<Chart> topRatedMovieBackdrop (Chart chart) {
        return chartService.topRated (1)
                .subscribeOn (Schedulers.io ())
                .map (moviesResponse -> {
                    Movie movie = moviesResponse.results.get (0);
                    chart.backDropPath = movie.backdropPath;
                    return chart;
                });
    }

    @Override
    public Single<Chart> genreMovieBackdrop (Chart chart) {
        return chartService.topRatedInGenre (chart.id, 1)
                .subscribeOn (Schedulers.io ())
                .map (moviesResponse -> {
                    Movie movie = moviesResponse.results.get (0);
                    chart.backDropPath = movie.backdropPath;
                    return chart;
                });
    }
}
