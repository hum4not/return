package org.returnclient.module.impl.visual;

import org.returnclient.Return;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.ColorUtils;

import java.awt.*;

public class Chams extends Module {

    public OptionMode mode;
    public OptionValue red;
    public OptionValue green;
    public OptionValue blue;
    public OptionValue alpha;

    String[] modes = { "Pulse", "Rainbow", "Static" };

    public Chams() {
        super("Chams", Category.visual);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Pulse", modes));
        Return.getInstance().optionManager.addValue(red = new OptionValue("Red", this, 255.0, 0.0, 255.0));
        Return.getInstance().optionManager.addValue(green = new OptionValue("Green", this, 255.0, 0.0, 255.0));
        Return.getInstance().optionManager.addValue(blue = new OptionValue("Blue", this, 255.0, 0.0, 255.0));
        Return.getInstance().optionManager.addValue(alpha = new OptionValue("Alpha", this, 60.0, 15.0, 255.0));
        setSuffix(mode.getMode());
    }

    public int getChamsColor() {
        setSuffix(mode.getMode());

        if(mode.is("Pulse")) {
            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (mc.fontRendererObj.FONT_HEIGHT + 50D));
            return ColorUtils.getGradientOffset(new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255), Color.BLACK, colorOffset, (int) alpha.getValue()).hashCode();

        } else if(mode.is("Static")) {
            return new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), (int) alpha.getValue()).hashCode();

        } else if(mode.is("Rainbow")) {
            Color color = new Color(ColorUtils.getChamsRainbow(300));
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) alpha.getValue()).hashCode();
        }

        return -1;
    }
}
