package com.example.horizontalscrollviewsample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ResponsiveScrollView extends HorizontalScrollView {

    public interface OnEndScrollListener {
        public void onEndScroll();
    }

    private boolean mIsFling;
    private OnEndScrollListener mOnEndScrollListener;

    public ResponsiveScrollView(Context context) {
        this(context, null, 0);
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponsiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void fling(int velocityX) {
        super.fling(velocityX);
        mIsFling = true;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (mIsFling) {
            if (Math.abs(x - oldX) < 2 || x >= getMeasuredHeight() || x == 0) {
                if (mOnEndScrollListener != null) {
                    mOnEndScrollListener.onEndScroll();
                }
                mIsFling = false;
            }
        }
    }

    public OnEndScrollListener getOnEndScrollListener() {
        return mOnEndScrollListener;
    }

    public void setOnEndScrollListener(OnEndScrollListener mOnEndScrollListener) {
        this.mOnEndScrollListener = mOnEndScrollListener;
    }

}