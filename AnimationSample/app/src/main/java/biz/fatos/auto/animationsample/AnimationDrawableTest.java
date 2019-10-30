package biz.fatos.auto.animationsample;

import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;

public class AnimationDrawableTest extends AnimationDrawable {

    private volatile int duration;
    private int currentFrame;

    public AnimationDrawableTest(AnimationDrawable aniDrawable)
    {
        for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++)
        {
            this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i));
        }

        currentFrame = 0;
    }

    @Override
    public void run() {
        int n = getNumberOfFrames();
        currentFrame++;
        if (currentFrame >= n) {
            currentFrame = 0;
        }
        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis() + duration);
    }

    public void setDuration(int duration) {
        this.duration = duration;
        unscheduleSelf(this);
        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis() + duration);
    }
}
