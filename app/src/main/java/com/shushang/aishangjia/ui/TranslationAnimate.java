package com.shushang.aishangjia.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by YD on 2017/12/11.
 *
 */

public class TranslationAnimate {

    private int startX,startY,endX,endY;
    private FrameLayout mFrameLayout;
    private ImageView mImageView;
    private int [] SourseLocation=new int[2];
    private int [] targetLocation=new int[2];



    public TranslationAnimate(int[] sourseLocation, int[] targetLocation, FrameLayout frameLayout, ImageView mImageView) {
        this.mFrameLayout = frameLayout;
        this.mImageView = mImageView;
        this.SourseLocation = sourseLocation;
        this.targetLocation = targetLocation;

    }

    public void move() {

        mImageView.getLocationInWindow(SourseLocation);
        mFrameLayout.getLocationInWindow(targetLocation);

        startX = SourseLocation[0] + mImageView.getWidth() / 2;
        startY = SourseLocation[1] + mImageView.getHeight() / 2;
        endX = targetLocation[0] + mFrameLayout.getWidth() / 2;
        endY = targetLocation[1] + mFrameLayout.getHeight() / 2;

        ObjectAnimator translationXanimator=ObjectAnimator.ofFloat(mImageView,"translationX",0f,-(startX-endX));
        translationXanimator.setDuration(200);
        translationXanimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator translationYanimator=ObjectAnimator.ofFloat(mImageView,"translationY",0f,(endY-startY));
        translationYanimator.setDuration(200);
        translationYanimator.setInterpolator(new  AccelerateInterpolator());

        final AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(translationXanimator,translationYanimator);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {

                //水波纹形状的揭露动画
                Animator animator = ViewAnimationUtils.createCircularReveal(mFrameLayout, mFrameLayout.getWidth() / 2, mFrameLayout.getHeight() / 2, 0, mFrameLayout.getHeight());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mImageView.setVisibility(View.INVISIBLE);
                        mFrameLayout.setVisibility(View.VISIBLE);

                    }

                });
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
            }
        });

}


   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public void close( ) {



       //水波纹形状的揭露动画
       Animator animator = ViewAnimationUtils.createCircularReveal(mFrameLayout, mFrameLayout.getWidth() / 2, mFrameLayout.getHeight() / 2, mFrameLayout.getHeight(),mImageView.getWidth()/2);
       animator.addListener(new AnimatorListenerAdapter() {

           @Override
           public void onAnimationEnd(Animator animation) {
               mImageView.setVisibility(View.VISIBLE);
               mFrameLayout.setVisibility(View.INVISIBLE);


               ObjectAnimator translationXanimator=ObjectAnimator.ofFloat(mImageView,"translationX",-(startX-endX),0);
               translationXanimator.setDuration(200);
               translationXanimator.setInterpolator(new AccelerateInterpolator());

               ObjectAnimator translationYanimator=ObjectAnimator.ofFloat(mImageView,"translationY",(endY-startY),0);
               translationYanimator.setDuration(200);
               translationYanimator.setInterpolator(new LinearInterpolator());

               AnimatorSet animatorSet=new AnimatorSet();
               animatorSet.playTogether(translationXanimator,translationYanimator);
               animatorSet.start();


           }

       });
       animator.setDuration(500);
       animator.setInterpolator(new AccelerateInterpolator());
       animator.start();


   }



}
