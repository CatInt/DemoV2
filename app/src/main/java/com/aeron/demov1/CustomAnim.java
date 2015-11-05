package com.aeron.demov1;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Yoda on 2015/11/5.
 */
public class CustomAnim extends Animation{
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        t.getMatrix().setTranslate((float) (Math.sin(interpolatedTime*100)*10),0);
    }
}
