package org.returnclient.module.impl.movement;

import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.module.Module;

public class Phase extends Module {

    public Phase() {
        super("Phase", Category.movement);
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if(mc.thePlayer.movementInput.sneak) {

            e.setX(mc.thePlayer.posX);
            e.setY(mc.thePlayer.posY - 1.25);
            e.setZ(mc.thePlayer.posZ);

            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.25, mc.thePlayer.posZ);
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.25 + 0.25d, mc.thePlayer.posZ);

        }
    }
}
