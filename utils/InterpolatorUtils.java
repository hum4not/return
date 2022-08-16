package org.returnclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class InterpolatorUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static double getInterpolationSpeed(double speed) {
        return  MathHelper.clamp_double((speed * 100) / mc.getDebugFPS(), 0.001, (speed * 100) / mc.getDebugFPS());
    }
}
