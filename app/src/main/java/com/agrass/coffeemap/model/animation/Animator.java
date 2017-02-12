package com.agrass.coffeemap.model.animation;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class Animator {

    private static long animDuration = 500;

    public static void slideFromBottomToTop(final View view, final int visibility) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 300, view.getY());
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.setAnimation(animate);
        view.animate().setListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                view.setAlpha(1.0f);
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                view.setVisibility(visibility);
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {

            }
        });
        view.animate().start();
    }

    public static void slideFromTopToBottom(final View view, final int visibility) {
        view.animate()
                .translationY(1000)
                .alpha(0.0f)
                .setDuration(2000)
                .setListener(new android.animation.Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {
                        view.setAlpha(1.0f);
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setVisibility(visibility);
                        animation.cancel();
                    }

                    @Override
                    public void onAnimationCancel(android.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(android.animation.Animator animation) {

                    }
                })
                .start();
    }

    public static void fadeIn(final View view) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(animDuration)
                .setListener(null);

    }

    public static void fadeOut(final View view) {
        view.animate()
                .alpha(0f)
                .setDuration(animDuration)
                .setListener(new android.animation.Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {
                        view.setAlpha(1.0f);
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(android.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(android.animation.Animator animation) {

                    }
                });
    }

}
