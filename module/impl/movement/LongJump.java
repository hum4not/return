package org.returnclient.module.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.MoveUtils;
import org.returnclient.utils.TimerUtils;

public class LongJump extends Module {

    TimerUtils verusTimer = new TimerUtils();

    OptionMode mode;

    String[] modes = { "Watchdog", "Verus" };

    OptionValue verusSpeed;

    int state = 0;

    public LongJump() {
        super("Long Jump", Category.movement);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Watchdog", modes));
        Return.getInstance().optionManager.addValue(verusSpeed = new OptionValue("Verus Speed", this, 1.2f, 0.85f, 1.75f));
        setSuffix(mode.getMode());
    }

    @Override
    public void onEnable() {
        state = 0;
        mc.timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        state = 0;
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        setSuffix(mode.getMode());

        if (mode.is("Verus")) {

            mc.thePlayer.motionY = 0.0;

            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;

            if(mc.thePlayer.movementInput.jump) {
                mc.thePlayer.motionY += 0.5;
            }

            if(mc.thePlayer.movementInput.sneak) {
                mc.thePlayer.motionY -= 0.5;
            }

            if (MoveUtils.isMoving()) {
                mc.thePlayer.jumpMovementFactor = (float) verusSpeed.getValue();
            }
        } else if(mode.is("Watchdog")) {
            state++;

            if(state > 0) {
                mc.thePlayer.motionY = 0.0;
            }

            switch (state) {
                case 1:
                    mc.timer.timerSpeed = 1.08f;
                    mc.thePlayer.motionY = 0.291828212;
                    break;
                case 5:
                    mc.timer.timerSpeed = 1.08f;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.291828212, mc.thePlayer.posZ, true));
                    break;
                case 10:
                    mc.timer.timerSpeed = 1.04f;
                    mc.thePlayer.posY = -0.49829839;
                    break;
            }
        }
    }
}
