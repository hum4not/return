package org.returnclient.utils;

import java.awt.*;

public class ColorUtils {

    public static int getRainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.8f).getRGB();
    }

    public static int getChamsRainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 1.0f).getRGB();
    }

    public static Color getGradientOffset(Color one, Color two, double offset, final int alpha) {

        if(offset > 1){
            double left = offset % 1;
            int off = (int) offset;
            offset = off % 2 == 0 ? left : 1 - left;
        }

        double inverse_percent = 1 - offset;

        int redPart = (int) (one.getRed() * inverse_percent + two.getRed() * offset);
        int greenPart = (int) (one.getGreen() * inverse_percent + two.getGreen() * offset);
        int bluePart = (int) (one.getBlue() * inverse_percent + two.getBlue() * offset);

        return new Color(redPart, greenPart, bluePart, alpha);
    }

}
