package org.returnclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoveUtils {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        if(mc.thePlayer.movementInput.moveForward == 0.0f) {
            if(mc.thePlayer.movementInput.moveStrafe == 0.0f) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMovingOnGround() {
        return isMoving() && mc.thePlayer.onGround;
    }

    public static float getSpeed() {
        float speed = (float)Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) * 20;
        return speed;
    }

    public static float getEntitySpeed(EntityPlayer entity) {
        float speed = (float)Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ) * 20;
        return speed;
    }

    public static void setSpeed(double speed) {

        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;

        float yaw = mc.thePlayer.rotationYaw;

        if (forward == 0.0 && strafe == 0.0) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }

            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

}
