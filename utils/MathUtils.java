package org.returnclient.utils;

public class MathUtils {

    public static double getInputFromPercent(final double value, final double minInput, final double maxInput) {
        return (value * (maxInput - minInput)) / 100 + minInput;
    }
}
