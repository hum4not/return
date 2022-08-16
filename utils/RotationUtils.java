package org.returnclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotationToEntity(Entity entity) {

        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY - 0.25 + entity.getEyeHeight() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double distance = (double)MathHelper.sqrt_double(x * x + z * z);

        float yaw = (float)(MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(-(MathHelper.atan2(y, distance) * 180.0D / Math.PI));

        return new float[] {yaw, pitch};
    }
}
