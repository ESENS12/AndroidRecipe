package com.example.horizontalscrollviewsample;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class CustomScrollView extends HorizontalScrollView {

    private static final int DELAY_MILLIS = 100;

    public interface OnFlingListener {
        public void onFlingStarted();
        public void onFlingStopped();
    }

    private OnFlingListener mFlingListener;
    private Runnable mScrollChecker;
    private int mPreviousPosition;

    public CustomScrollView(Context context) {
        this(context, null, 0);

    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScrollChecker = new Runnable() {
            @Override
            public void run() {
                int position = getScrollX();
                //디바이스 마다 스펙이 다른 경우 주의
                Log.d("fling" ,"velocity : " + (Math.abs(mPreviousPosition - position)));
                if (Math.abs(mPreviousPosition - position)<100) {

                    mFlingListener.onFlingStopped();
                    removeCallbacks(mScrollChecker);
                } else {
                    mPreviousPosition = getScrollX();
                    postDelayed(mScrollChecker, DELAY_MILLIS);
                }
            }
        };
    }

    @Override
    public void fling(int velocityX) {
        super.fling(velocityX);
        if (mFlingListener != null) {
            mFlingListener.onFlingStarted();
            post(mScrollChecker);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //// TODO: 2019. 4. 3. touch up 상태에서도 제자리 찾아가도록 해야함
        //Log.d("onTouch","touch event + " + ev.toString());
        if(ev.getAction() == MotionEvent.ACTION_UP){
            if (mFlingListener != null) {
                Log.d("onTOuch" ,"listener not null");
                mFlingListener.onFlingStopped();
                removeCallbacks(mScrollChecker);
                //post(mScrollChecker);
            }
        }

        return super.onTouchEvent(ev);
    }

    public OnFlingListener getOnFlingListener() {
        return mFlingListener;
    }

    public void setOnFlingListener(OnFlingListener mOnFlingListener) {
        this.mFlingListener = mOnFlingListener;
    }

}