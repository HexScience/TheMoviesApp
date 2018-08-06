package com.winterparadox.themovieapp.recentlyViewed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.winterparadox.themovieapp.App;
import com.winterparadox.themovieapp.R;
import com.winterparadox.themovieapp.common.beans.Movie;
import com.winterparadox.themovieapp.common.views.OnScrollObserver;
import com.winterparadox.themovieapp.search.HostView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.GridLayout.VERTICAL;


public class RecentlyViewedFragment extends Fragment implements RecentlyViewedView,
        RecentMoviesAdapter.ClickListener {

    @BindView(R.id.tvHeader) TextView tvHeader;
    @BindView(R.id.recyclerView) ShimmerRecyclerView recyclerView;
    @BindView(R.id.scrollIndicator) ImageView scrollIndicator;
    Unbinder unbinder;

    @Inject RecentlyViewedPresenter presenter;
    private RecentMoviesAdapter movieAdapter;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {

        ((App) getActivity ().getApplication ()).getAppComponent ().inject (this);

        View view = inflater.inflate (R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind (this, view);

        tvHeader.setText (R.string.recently_viewed);

        GridLayoutManager gridLayoutManager = new GridLayoutManager (getActivity (),
                3, VERTICAL, false);
        recyclerView.setLayoutManager (gridLayoutManager);
        RecentMovieDecoration decor = new RecentMovieDecoration (getActivity (),
                DividerItemDecoration.VERTICAL);
        decor.setItemPadding (8);
        decor.setDefaultOffset (24);
        recyclerView.addItemDecoration (decor);
        recyclerView.setHasFixedSize (true);
        movieAdapter = new RecentMoviesAdapter (this);
        recyclerView.setAdapter (movieAdapter);

        recyclerView.addOnScrollListener (new OnScrollObserver () {
            @Override
            public void onScrolling () {
                scrollIndicator.setVisibility (View.VISIBLE);
            }

            @Override
            public void onScrollToTop () {
                scrollIndicator.setVisibility (View.GONE);
            }
        });


        presenter.attachView (this);

        return view;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    @Override
    public void showMovies (List<Movie> movies) {
        movieAdapter.setItems (movies);
    }

    @Override
    public void showProgress () {
    }

    @Override
    public void hideProgress () {
    }

    @Override
    public void showMessage (String message) {
        Toast.makeText (getActivity (), message, Toast.LENGTH_LONG).show ();
    }

    @Override
    public void showError (String message) {

    }

    @Override
    public void onMovieClick (Movie movie, View element) {
        ((HostView) getActivity ()).openMovie (movie, element);
    }
}