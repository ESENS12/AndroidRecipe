package com.example.horizontalscrollviewsample;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class CustomScrollView extends HorizontalScrollView {
    private int number = 0 ;
    private static final int DELAY_MILLIS = 100;
    private static final String TAG = CustomScrollView.class.getSimpleName();

    public interface OnFlingListener {
        public void onFlingStarted();
        public void onFlingStopped(int x);
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
                //Log.d("fling" ,"velocity : " + (Math.abs(mPreviousPosition - position)));
                if (Math.abs(mPreviousPosition - position)<100) {

                    mFlingListener.onFlingStopped(getScrollX());
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

        /**
         * action down 때의 getScrollX와 up일때의  x값이 이동하는 값..
         * 문제는 fling 때에도 제대로 동작해야함
         * 절대값으로 item/2 크기 이상 이동했다면 액션이벤트 발생 -> 방향 전달
         * **/

//        if(ev.getAction() == MotionEvent.ACTION_DOWN){
//            //Log.d(TAG,"Action Down! : " + getScrollX());
//            number = getScrollX();
//        }
//
        if(ev.getAction() == MotionEvent.ACTION_UP){
            if (mFlingListener != null) {
                //Log.d(TAG ,"distance  : " + Math.abs(number - getScrollX()));
//                number = 0;
//                mFlingListener.onFlingStopped(getScrollX());
//                removeCallbacks(mScrollChecker);
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