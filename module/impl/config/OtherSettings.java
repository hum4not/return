package org.returnclient.module.impl.config;

import org.returnclient.Return;
import org.returnclient.module.Module;
import org.returnclient.options.OptionBoolean;

public class OtherSettings extends Module {

    public OptionBoolean resizable;
    public OptionBoolean animation;

    public OtherSettings() {
        super("Other Settings", Category.config);
        Return.getInstance().optionManager.addBoolean(resizable = new OptionBoolean("Resizable", this, true));
        Return.getInstance().optionManager.addBoolean(animation = new OptionBoolean("Animation", this, true));
    }
}
