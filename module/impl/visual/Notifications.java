package org.returnclient.module.impl.visual;

import net.minecraft.client.gui.ScaledResolution;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.module.Module;
import org.returnclient.notifications.Notification;
import org.returnclient.notifications.NotificationManager;

public class Notifications extends Module {

    public Notifications() {
        super("Notifications", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        ScaledResolution scaledResolution = e.getScaledResolution();

        int var = 0;

        for (Notification notification : Return.getInstance().notificationManager.notifications) {

            double x = scaledResolution.getScaledWidth() / 2 - notification.width / 2;
            double y = scaledResolution.getScaledHeight() / 2 + 25.5 + var * (notification.height + 1.0);

            notification.onRender(x, y);
            var++;
        }
    }
}
