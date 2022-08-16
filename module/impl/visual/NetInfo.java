package org.returnclient.module.impl.visual;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.module.Module;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class NetInfo extends Module {

    public NetInfo() {
        super("Net Info", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        ScaledResolution scaledResolution = e.getScaledResolution();

        double width = 110;
        double height = 37.5;

        double x = scaledResolution.getScaledWidth() / 2.5 - width;
        double y = 10.5;

        RenderUtils.drawRect(x, y, x + width, y + 0.5, new Color(0xFFFFFF).hashCode());
        RenderUtils.drawRect(x, y + height - 0.5, x + width, y + height - 0.5 + 0.5, new Color(0xFFFFFF).hashCode());
        RenderUtils.drawRect(x, y, x + 0.5, y + height, new Color(0xFFFFFF).hashCode());
        RenderUtils.drawRect(x + width - 0.5, y, x + width - 0.5 + 0.5, y + height, new Color(0xFFFFFF).hashCode());

        RenderUtils.drawRect(x, y + 8.5, x + width, y + 8.5 + 0.5, new Color(0xFFFFFF).hashCode());
    }
}
