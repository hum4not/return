package org.returnclient.event.impl;

import net.minecraft.client.gui.ScaledResolution;
import org.returnclient.event.Event;

public class EventRender2D extends Event {

    private ScaledResolution scaledResolution;

    public EventRender2D(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
}
