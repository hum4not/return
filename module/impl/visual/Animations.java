package org.returnclient.module.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventTransformItem;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;

public class Animations extends Module {

    OptionValue x;
    OptionValue y;
    OptionValue z;

    OptionValue scale;

    public OptionMode mode;

    String[] modes = { "Return", "Normal" };

    public Animations() {
        super("Animations", Category.visual);
        Return.getInstance().optionManager.addValue(x = new OptionValue("Position X", this, 0, -2, 2));
        Return.getInstance().optionManager.addValue(y = new OptionValue("Position Y", this, 0, -2, 2));
        Return.getInstance().optionManager.addValue(z = new OptionValue("Position Z", this, 0, -2, 2));
        Return.getInstance().optionManager.addValue(scale = new OptionValue("Scale", this, 1, 0.15, 2.5));
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Return", modes));
    }

    @EventTarget
    public void onTransform(EventTransformItem e) {
        GlStateManager.translate(x.getValue(), y.getValue(), z.getValue());
        GlStateManager.scale(scale.getValue(), scale.getValue(), scale.getValue());
    }
}
