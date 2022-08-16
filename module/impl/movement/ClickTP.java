package org.returnclient.module.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Mouse;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.module.Module;

public class ClickTP extends Module {

    public ClickTP() {
        super("Click TP", Category.movement);
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if(Mouse.isButtonDown(2)) {

            e.setX(mc.objectMouseOver.hitVec.xCoord);
            e.setY(mc.objectMouseOver.hitVec.zCoord);
            e.setZ(mc.objectMouseOver.hitVec.yCoord);

            mc.thePlayer.setPosition(mc.objectMouseOver.hitVec.xCoord, mc.objectMouseOver.hitVec.yCoord, mc.objectMouseOver.hitVec.zCoord);

        }
    }

}
