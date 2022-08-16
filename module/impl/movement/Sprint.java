package org.returnclient.module.impl.movement;

import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.thePlayer.movementInput.moveForward > 0) {
            mc.thePlayer.setSprinting(true);
        }
    }

}
