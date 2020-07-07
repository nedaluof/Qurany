package com.nedaluof.qurany.util;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by nedaluof on 7/6/2020.
 */
public class ButtonAnimation implements Interpolator {
    private double amplitude = 1;
    private double frequency = 10;

    public ButtonAnimation(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / amplitude) *
                Math.cos(frequency * time) + 1);
    }
}