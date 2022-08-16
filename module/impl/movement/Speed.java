package org.returnclient.module.impl.movement;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import org.apache.commons.lang3.RandomUtils;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.utils.MoveUtils;
import org.returnclient.utils.TimerUtils;

public class Speed extends Module {

    OptionMode mode;

    String[] modes = { "BHop", "Low Hop" };

    TimerUtils timer = new TimerUtils();
    TimerUtils lowHopTimer = new TimerUtils();

    public Speed() {
        super("Speed", Category.movement);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "BHop", modes));
        setSuffix(mode.getMode());
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        setSuffix(mode.getMode());

        if (mode.is("Low Hop")) {
            if (mc.thePlayer.ticksExisted % 3 == 0) {

                if (MoveUtils.isMoving()) {

                    mc.thePlayer.motionY = -1f;

                    if(lowHopTimer.hasTimeElapsed(250L, true)) {
                        mc.timer.timerSpeed = 1.05f;
                    }

                    MoveUtils.setSpeed(0.25);
                    mc.thePlayer.moveEntity(0, 0.25, 0);
                }
            }
        } else if(mode.is("BHop")) {
            if (mc.thePlayer.ticksExisted % 3 == 0) {

                if (MoveUtils.isMoving()) {

                    if (timer.hasTimeElapsed(250L, true)) {
                        mc.timer.timerSpeed = 1.03f;
                    }

                    double speed = 0.25;

                    mc.thePlayer.motionX = mc.thePlayer.movementInput.moveForward * speed * Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f)) + mc.thePlayer.movementInput.moveStrafe * speed * Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
                    mc.thePlayer.motionZ = mc.thePlayer.movementInput.moveForward * speed * Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f)) - mc.thePlayer.movementInput.moveStrafe * speed * Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));

                    MoveUtils.setSpeed(speed);

                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                }
            }
        }
    }
}
