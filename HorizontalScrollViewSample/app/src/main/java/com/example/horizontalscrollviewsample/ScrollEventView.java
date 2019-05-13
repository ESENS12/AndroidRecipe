package com.example.horizontalscrollviewsample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

public class ScrollEventView extends HorizontalScrollView {

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 100;
    private static final String TAG = "ScrollEventView";

    public interface OnScrollStoppedListener{
        void onScrollStopped();
    }

    public interface OnScrollChangedListener{
        void onScrollChanged(int x, int y);
    }

    private OnScrollStoppedListener onScrollStoppedListener;
    private OnScrollChangedListener onScrollChangedListener;

    public ScrollEventView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scrollerTask = new Runnable() {

            public void run() {
                onScrollChangedListener.onScrollChanged(getScrollX(), initialPosition);
                int newPosition = getScrollX();
                if(initialPosition - newPosition == 0){//has stopped
                    //Log.d(TAG,"not changed  : " +initialPosition + ", new : " + newPosition);
                    if(onScrollStoppedListener!=null){

                        onScrollStoppedListener.onScrollStopped();
                    }
                }else{
                    //onScrollChangedListener.onScrollChanged(getScrollX(),initialPosition);
                    //onScrollChanged(getScrollX(),initialPosition);
                    initialPosition = getScrollX();
                    ScrollEventView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public void onScrollChanged(int x, int oldX) {
        Log.d(TAG,"onScrollChanged : " + oldX + " to  " + x);
    }

    public void setOnScrollStoppedListener(ScrollEventView.OnScrollStoppedListener listener){
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask(){

        initialPosition = getScrollX();
        ScrollEventView.this.postDelayed(scrollerTask, newCheck);
    }


    public void setOnScrollChangedListener(ScrollEventView.OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

}
