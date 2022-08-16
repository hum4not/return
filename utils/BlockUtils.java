package org.returnclient.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BlockUtils {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotationToBlock(BlockPos block) {

        Vec3 pos = new Vec3(block.getX(), block.getY(), block.getZ());

        double d0 = pos.xCoord - mc.thePlayer.posX;
        double d1 = pos.yCoord - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double d2 = pos.zCoord - mc.thePlayer.posZ;

        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        float f = (float) (MathHelper.atan2(d2, d0) * 180D / Math.PI) - 90.0F;
        float f1 = MathHelper.wrapAngleTo180_float((float) -(MathHelper.atan2(d1, d3) * 180D / Math.PI));

        return new float[] {f, f1};
    }

}
