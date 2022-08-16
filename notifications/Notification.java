package org.returnclient.notifications;

import javafx.animation.Interpolator;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.returnclient.Return;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import net.minecraft.client.Minecraft;
import org.returnclient.utils.InterpolatorUtils;
import org.returnclient.utils.RenderUtils;
import org.returnclient.utils.TimerUtils;

import java.awt.*;
import java.util.HashMap;

public class Notification {

    TimerUtils timer = new TimerUtils();

    private HashMap<Notification, Float> map = new HashMap<>();

    public Minecraft mc = Minecraft.getMinecraft();

    public double width, height;

    private String title, massage;

    private NotificationType type;

    public Notification(String title, String massage, NotificationType type) {

        CFontRenderer font = CFonts.tahoma;

        this.title = title;
        this.massage = massage;
        this.type = type;

        this.width = 15.5 + font.getStringWidth(massage) + 4.5;
        this.height = 20;

        map.put(this, 55f);
    }

    public void onRender(double x, double y) {

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        CFontRenderer font = CFonts.tahoma_bigger;

        String icon = null;

        float lastX = 0;

        if (map.containsKey(this)) {
            map.entrySet().forEach(m -> m.setValue((float) Interpolator.EASE_OUT.interpolate((float) m.getValue(), 0f, InterpolatorUtils.getInterpolationSpeed(0.055))));

            lastX = map.get(this);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(lastX, 0, 0);

        float time = System.currentTimeMillis()-timer.lastMS;

        int color = -1;

        switch (type) {
            case successfully:
                color = new Color(0x268C01).hashCode();
                icon = "Z";
                break;
            case warning:
                color = new Color(0xA85E5E).hashCode();
                icon = "c";
                break;
            case info:
                color = new Color(0xB2B256).hashCode();
                icon = "N";
        }

        RenderUtils.drawRect(x, y, x + width, y + height, new Color(0xDF101010, true).hashCode());
        RenderUtils.drawRect(x, y + height - 1.5, x + width, y + height - 1.5 + 1.5, color);
        RenderUtils.drawRect(x, y + height - 1.5, x + width, y + height - 1.5 + 1.5, Integer.MIN_VALUE);

        RenderUtils.drawRect(x, y + height - 1.5, x + width * (time / 3450L), y + height - 1.5 + 1.5, color);

        // title

        font.drawStringWithShadow(title, x + 17.5, y + 2.5, -1);

        // icon

        font = CFonts.icons;

        font.drawStringWithShadow(icon, x + 1.5, y - 1.5, color);

        // massage

        font = CFonts.tahoma;

        font.drawStringWithShadow(massage, x + 17.5, y + 11.5, -1);

        if(lastX < 0.1f) {
            if (timer.hasTimeElapsed(3450L, true)) {
                for (int i = 0; i < Return.getInstance().notificationManager.notifications.size(); i++) {

                    Notification notification = Return.getInstance().notificationManager.notifications.get(i);

                    Return.getInstance().notificationManager.removeNotification(notification);
                }
            }
        }

        GL11.glPopMatrix();
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public enum NotificationType {
        successfully,
        warning,
        info;
    }

}

