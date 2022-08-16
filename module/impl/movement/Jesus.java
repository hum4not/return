package org.returnclient.module.impl.movement;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.utils.MoveUtils;

public class Jesus extends Module {

    public Jesus() {
        super("Jesus", Category.movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.thePlayer.isInWater()) {
            mc.thePlayer.motionY = 0.2;
            mc.thePlayer.onGround = false;
        }
    }

}
