package ndk.com.example.apple.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class ValueAnimatorAct extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ObjectAnimator alphaAnimator, scaleAnimator, rotateAnimator, textColorAnimator, translationXAnimator;

    private AnimatorSet animatorSet, animSetFromXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator);
        initAnimators();
    }

    private void initAnimators() {
        animatorSet = new AnimatorSet();
        alphaAnimator = ObjectAnimator.ofFloat(null, "alpha", 1f, 0.8f, 0.5f, 0.2f, 0, 0.5f, 1f);
        alphaAnimator.setDuration(5000);
        scaleAnimator = ObjectAnimator.ofFloat(null, "scaleX", 2f, 1f);//2f为相对原始大小的绝对值
        scaleAnimator.setDuration(1000);
        rotateAnimator = ObjectAnimator.ofFloat(null, "rotation", 0, 180f, 360, 180, 0);//rotationX绕X轴旋转，rotationY绕Y轴旋转
        rotateAnimator.setDuration(3000);
        textColorAnimator = ObjectAnimator.ofInt(null, "textColor", Color.RED, Color.BLUE, Color.BLUE);
        textColorAnimator.setDuration(3000);
        animatorSet.play(alphaAnimator).after(scaleAnimator).after(textColorAnimator).with(rotateAnimator);//以play为基准，scale最先执行，alpha和rotate同时进行
        translationXAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.backgroundanimator);
        animSetFromXml = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animset);

    }

    public void watchValueAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(1, 2, 4, 8);
        animator.setDuration(5000);
        animator.setRepeatCount(2);//重复次数
        animator.setRepeatMode(ValueAnimator.RESTART);//ValueAnimator.RESTART正序，ValueAnimator.REVERSE倒序
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void watchValueAnim(View view) {
        watchValueAnimator();
    }

    public void changeAlpha(View view) {
        alphaAnimator.setTarget(view);
        alphaAnimator.start();
    }

    public void changeSize(View view) {
        scaleAnimator.setTarget(view);
        scaleAnimator.start();
    }

    public void rotateView(View view) {
        rotateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        rotateAnimator.addListener(new AnimatorListenerAdapter() {
        });//空实现，只复写需要的方法
        rotateAnimator.setTarget(view);
        rotateAnimator.start();
    }

    public void animatorSet(View view) {
        animatorSet.setTarget(view);
        animatorSet.start();
    }

    public void translationView(View view) {
        translationXAnimator.setTarget(view);
        translationXAnimator.start();
    }

    public void animSetFromXml(View view) {
        animSetFromXml.setTarget(view);
        animSetFromXml.start();
    }
}
