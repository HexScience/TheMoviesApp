package com.winterparadox.themovieapp.home;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.winterparadox.themovieapp.common.views.DefaultItemDecoration;

import static com.winterparadox.themovieapp.common.UiUtils.dpToPx;

public class HomeItemDecoration extends DefaultItemDecoration {

    HomeItemDecoration (Context context, int orientation) {
        super (context, orientation);
    }

    private int middleHorizontalPadding;
    private int verticalOffset;

    void setMiddleHorizontalPadding (int middleHorizontalPadding) {
        this.middleHorizontalPadding = middleHorizontalPadding;
    }

    void setVerticalOffset (int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent,
                                RecyclerView.State state) {
        outRect.left = (int) dpToPx (defaultOffset);
        outRect.right = (int) dpToPx (defaultOffset);
        outRect.bottom = (int) dpToPx (verticalOffset);

        if ( parent.getChildAdapterPosition (view) % 5 == 0 ) {
            outRect.top = (int) dpToPx (8);
            outRect.bottom = 0;
        }

        if ( parent.getChildAdapterPosition (view) % 5 == 2 ) {
            outRect.right = 0;
        }

        if ( parent.getChildAdapterPosition (view) % 5 == 3 ) {
            outRect.right = (int) dpToPx (middleHorizontalPadding);
            outRect.left = (int) dpToPx (middleHorizontalPadding);
        }

        if ( parent.getChildAdapterPosition (view) % 5 == 4 ) {
            outRect.left = 0;
        }

        if ( parent.getChildAdapterPosition (view) == parent.getAdapter ().getItemCount () - 1 ) {
            outRect.bottom = (int) dpToPx (16);
        }
        if ( parent.getChildAdapterPosition (view) == parent.getAdapter ().getItemCount () - 2 ) {
            outRect.bottom = (int) dpToPx (16);
        }
        if ( parent.getChildAdapterPosition (view) == parent.getAdapter ().getItemCount () - 3 ) {
            outRect.bottom = (int) dpToPx (16);
        }
    }
}
