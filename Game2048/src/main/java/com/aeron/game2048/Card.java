package com.aeron.game2048;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Define the basic class of each block of GameView
 */

public class Card extends FrameLayout {
    @Override
    public View getRootView() {
        return super.getRootView();
    }

    private int mNum;
    private TextView mLabel;
    private int mPreNum;
    private int mStep;
    private boolean mHasChange;


    public Card(Context context) {
        super(context);

        mLabel = new TextView(getContext());
        mLabel.setGravity(Gravity.CENTER);
        mLabel.setTextSize(32);

        //label.setBackgroundColor();

        LayoutParams lp = new LayoutParams(-1, -1);

        addView(mLabel, lp);
        setBackgroundColor(getAdaptiveColor(0));
        mNum = 0;
        mStep = 0;
        mPreNum = 0;
        }

    public void init(){
        mNum= 0;
        mHasChange = true;
        makeChangeAccordingToNum();
        mStep = 0;
    }

    public class AnimationView extends View{
        public AnimationView(Context context, int beginColor, int destColor,int duration){
            super(context);
            ObjectAnimator objectAnimator=
                    new ObjectAnimator();
            objectAnimator.setValues(
                    PropertyValuesHolder.ofInt(
                            "backgroundColor", beginColor, destColor));
            objectAnimator.setDuration(duration);

            objectAnimator.setEvaluator(new ArgbEvaluator());
            objectAnimator.setTarget(this);
            objectAnimator.start();
        }
    }

    public void makeChangeAccordingToNum(){
        if(mHasChange){
            AnimationView animationView = new AnimationView(
                    getContext(), getAdaptiveColor(mPreNum), getAdaptiveColor(), 300);
            addView(animationView);
            mPreNum = mNum;
            setBackgroundColor(getAdaptiveColor());
            mHasChange = false;
        }

    }

    public void setNum(int num, int step) {
//        if(mPreNum!=0){
//            AnimationView animationView = new AnimationView(
//                    getContext(), getAdaptiveColor(mPreNum), getAdaptiveColor(num), 200);
//            addView(animationView);
//        }else {
//            AnimationView animationView = new AnimationView(
//                    getContext(), getAdaptiveColor(),getAdaptiveColor(num), 500);
//            addView(animationView);
//        }
//
//        if(num == 0){
//            mPreNum = this.num;
//        }
//        this.num = num;
//        setBackgroundColor(getAdaptiveColor());
        if(step>mStep){
            mStep = step;
            mPreNum = mNum;
            mHasChange = true;
        }
        mNum = num;
    }


    public int getNum() {
        return mNum;
    }

    //return the num and initial itself
    public int giveNum(int step){
        int cache = this.mNum;
        setNum(0, step);
        return cache;
    }

    //return adaptiveColor according to num
    public int getAdaptiveColor() {
        int color = 0;
        switch (mNum){
            case 0:
                color = getResources().getColor(R.color.color_0);
                mLabel.setText("");
                break;
            case 2:
                color = getResources().getColor(R.color.color_2);
//                label.setText("2");
//                label.setTextSize(textWidth*8/10);
                break;
            case 4:
                color = getResources().getColor(R.color.color_4);
//                label.setText("4");
//                label.setTextSize(textWidth*8/10);
                break;
            case 8:
                color = getResources().getColor(R.color.color_8);
//                label.setText("8");
//                label.setTextSize(textWidth*8/10);
                break;
            case 16:
                color = getResources().getColor(R.color.color_16);
//                label.setText("16");
//                label.setTextSize(50);
                break;
            case 32:
                color = getResources().getColor(R.color.color_32);
//                label.setText("32");
//                label.setTextSize(50);
                break;
            case 64:
                color = getResources().getColor(R.color.color_64);
//                label.setText("64");
//                label.setTextSize(50);
                break;
            case 128:
                color = getResources().getColor(R.color.color_128);
//                label.setText("128");
//                label.setTextSize(40);
                break;
            case 256:
                color = getResources().getColor(R.color.color_256);
//                label.setText("256");
//                label.setTextSize(40);
                break;
            case 512:
                color = getResources().getColor(R.color.color_512);
//                label.setText("512");
//                label.setTextSize(40);
                break;
            case 1024:
                color = getResources().getColor(R.color.color_1024);
//                label.setText("1024");
//                label.setTextSize(30);
                break;
            case 2048:
                color = getResources().getColor(R.color.color_2048);
//                label.setText("2048");
//                label.setTextSize(30);
                break;
            case 4096:
                color = getResources().getColor(R.color.color_4096);
//                label.setText("4096");
//                label.setTextSize(30);
                break;
            case 8192:
                color = getResources().getColor(R.color.color_8192);
//                label.setText("8192");
//                label.setTextSize(30);
                break;
        }
        return color;
    }

    public int getAdaptiveColor(int num) {
        int color = 0;
        switch (num){
            case 0:
                color = getResources().getColor(R.color.color_0);
                mLabel.setText("");
                break;
            case 2:
                color = getResources().getColor(R.color.color_2);
//                label.setText("2");
//                label.setTextSize(textWidth*8/10);
                break;
            case 4:
                color = getResources().getColor(R.color.color_4);
//                label.setText("4");
//                label.setTextSize(textWidth*8/10);
                break;
            case 8:
                color = getResources().getColor(R.color.color_8);
//                label.setText("8");
//                label.setTextSize(textWidth*8/10);
                break;
            case 16:
                color = getResources().getColor(R.color.color_16);
//                label.setText("16");
//                label.setTextSize(50);
                break;
            case 32:
                color = getResources().getColor(R.color.color_32);
//                label.setText("32");
//                label.setTextSize(50);
                break;
            case 64:
                color = getResources().getColor(R.color.color_64);
//                label.setText("64");
//                label.setTextSize(50);
                break;
            case 128:
                color = getResources().getColor(R.color.color_128);
//                label.setText("128");
//                label.setTextSize(40);
                break;
            case 256:
                color = getResources().getColor(R.color.color_256);
//                label.setText("256");
//                label.setTextSize(40);
                break;
            case 512:
                color = getResources().getColor(R.color.color_512);
//                label.setText("512");
//                label.setTextSize(40);
                break;
            case 1024:
                color = getResources().getColor(R.color.color_1024);
//                label.setText("1024");
//                label.setTextSize(30);
                break;
            case 2048:
                color = getResources().getColor(R.color.color_2048);
//                label.setText("2048");
//                label.setTextSize(30);
                break;
            case 4096:
                color = getResources().getColor(R.color.color_4096);
//                label.setText("4096");
//                label.setTextSize(30);
                break;
            case 8192:
                color = getResources().getColor(R.color.color_8192);
//                label.setText("8192");
//                label.setTextSize(30);
                break;
        }
        return color;
    }

}
