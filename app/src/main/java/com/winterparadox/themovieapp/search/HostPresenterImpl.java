package com.winterparadox.themovieapp.search;

import com.winterparadox.themovieapp.arch.Navigator;
import com.winterparadox.themovieapp.common.PresenterUtils;
import com.winterparadox.themovieapp.room.AppDatabase;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.winterparadox.themovieapp.common.beans.Chart.CHART_LATEST;
import static com.winterparadox.themovieapp.common.beans.Chart.CHART_POPULAR;
import static com.winterparadox.themovieapp.common.beans.Chart.CHART_TOP_RATED;

public class HostPresenterImpl extends HostPresenter {

    private HostApiInteractor api;
    private final AppDatabase database;
    private final Scheduler mainScheduler;
    private Disposable favMenuDisposable;
    private Disposable recentMenuDisposable;
    private Disposable chartsDisposable;

    public HostPresenterImpl (HostApiInteractor api, AppDatabase database,
                              Scheduler mainScheduler) {
        this.api = api;
        this.database = database;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void attachView (HostView view, Navigator navigator) {
        super.attachView (view, navigator);

        fetchChartData ();

        favMenuDisposable = database.favoriteDao ()
                .anyExists ()
                .subscribeOn (Schedulers.io ())
                .observeOn (mainScheduler)
                .subscribe (anyExists -> {
                    if ( view != null ) {
                        view.showFavoritesMenu (anyExists);
                    }
                });

        recentMenuDisposable = database.recentlyViewedDao ()
                .anyExists ()
                .subscribeOn (Schedulers.io ())
                .observeOn (mainScheduler)
                .subscribe (anyExists -> {
                    if ( view != null ) {
                        view.showRecentMenu (anyExists);
                    }
                });

    }

    @Override
    public void fetchChartData () {
        if ( chartsDisposable != null && !chartsDisposable.isDisposed () ) {
            chartsDisposable.dispose ();
        }

        if ( view != null ) {
            List<String> defaultCharts = view.getDefaultCharts ();
            chartsDisposable = PresenterUtils.createChartss (api.generes (),
                    chart -> {
                        switch ( chart.id ) {
                            case CHART_POPULAR:
                                return api.popularMovieBackdrop (chart);
                            case CHART_LATEST:
                                return api.latestMovieBackdrop (chart);
                            case CHART_TOP_RATED:
                                return api.topRatedMovieBackdrop (chart);
                            default:
                                return api.genreMovieBackdrop (chart);
                        }

                    }, database, defaultCharts);
        }
    }

    @Override
    public void onRecentlyViewedClicked () {
        if ( navigator != null ) {
            navigator.openRecentlyViewed ();
        }
    }

    @Override
    public void onFavoritesClicked () {
        if ( navigator != null ) {
            navigator.openFavorites ();
        }
    }

    @Override
    public void onMyListsClicked () {
        if ( navigator != null ) {
            navigator.openMyLists ();
        }
    }

    @Override
    public void onChartsClicked () {
        if ( navigator != null ) {
            navigator.openCharts ();
        }
    }

    @Override
    public void detachView () {
        super.detachView ();
        if ( favMenuDisposable != null && !favMenuDisposable.isDisposed () ) {
            favMenuDisposable.dispose ();
        }
        if ( recentMenuDisposable != null && !recentMenuDisposable.isDisposed () ) {
            recentMenuDisposable.dispose ();
        }
    }
}
