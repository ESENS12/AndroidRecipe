package com.example.esens.cardviewtest.spotdialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;

import java.util.Arrays;


class AnimatorPlayer extends AnimatorListenerAdapter {

    private boolean interrupted = false;
    private final Animator[] animators;

    public AnimatorPlayer(Animator[] animators) {

        if(animators == null)
        {
            this.animators = new Animator[0];
        }
        else
        {
            this.animators = Arrays.copyOf(animators, animators.length);
        }

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (!interrupted) animate();
    }

    public void play() {
        animate();
    }

    public void stop() {
        interrupted = true;
    }

    private void animate() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.addListener(this);
        set.start();
    }
}
