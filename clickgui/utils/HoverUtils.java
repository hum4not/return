package org.returnclient.clickgui.utils;

public class HoverUtils {

    public static boolean hovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return x < mouseX && y < mouseY && width > mouseX && height > mouseY;
    }
}
