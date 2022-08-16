package org.returnclient.notifications;

import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    public static Minecraft mc = Minecraft.getMinecraft();

    public List<Notification> notifications = new ArrayList<Notification>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }
}
