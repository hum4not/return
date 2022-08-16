package org.returnclient.module.impl.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.module.Module;

public class AntiVoid extends Module {

    public AntiVoid() {
        super("Anti Void", Category.movement);
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if(mc.thePlayer.fallDistance >= 6.0) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 12.5, mc.thePlayer.posZ, mc.thePlayer.onGround));
            mc.thePlayer.fallDistance = 0.0f;
        }
    }
}
