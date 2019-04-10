package com.example.horizontalscrollviewsample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ScrollEventView extends HorizontalScrollView {

    /**
     *  reference
     *
     *  https://stackoverflow.com/questions/8181828/android-detect-when-scrollview-stops-scrolling
     *  thanks to tulio84z
     *
     * **/

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 100;
    private static final String TAG = "ScrollEventView";

    public interface OnScrollStoppedListener{
        void onScrollStopped();
    }

    private OnScrollStoppedListener onScrollStoppedListener;

    public ScrollEventView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scrollerTask = new Runnable() {

            public void run() {

                int newPosition = getScrollX();
                if(initialPosition - newPosition == 0){//has stopped

                    if(onScrollStoppedListener!=null){

                        onScrollStoppedListener.onScrollStopped();
                    }
                }else{
                    initialPosition = getScrollX();
                    ScrollEventView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public void setOnScrollStoppedListener(ScrollEventView.OnScrollStoppedListener listener){
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask(){

        initialPosition = getScrollX();
        ScrollEventView.this.postDelayed(scrollerTask, newCheck);
    }

}
