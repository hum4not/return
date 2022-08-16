package org.returnclient.module.impl.visual;

import org.returnclient.Return;
import org.returnclient.module.Module;
import org.returnclient.options.OptionValue;

import java.awt.*;

public class Glint extends Module {

    public OptionValue red;
    public OptionValue green;
    public OptionValue blue;

    public Glint() {
        super("Glint", Category.visual);
        Return.getInstance().optionManager.addValue(red = new OptionValue("Red", this, 255.0, 0.0, 255.0));
        Return.getInstance().optionManager.addValue(green = new OptionValue("Green", this, 255.0, 0.0, 255.0));
        Return.getInstance().optionManager.addValue(blue = new OptionValue("Blue", this, 255.0, 0.0, 255.0));
    }

    public int getGlintColor() {
        return new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue()).hashCode();
    }

}
