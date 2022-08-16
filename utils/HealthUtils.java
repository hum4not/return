package org.returnclient.utils;

import java.awt.*;

public class HealthUtils {

    public static int getHealthColor(float health) {

        int color = -1;

        if(health > 15) {
            color = new Color(0x209B26).hashCode();
        } else if(health > 8) {
            color = new Color(0xB9B422).hashCode();
        } else if(health > 0) {
            color = new Color(0xA12929).hashCode();
        }

        return color;
    }

}
