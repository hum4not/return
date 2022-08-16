package org.returnclient.module.impl.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.module.Module;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", Category.combat);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if(e.getPacket() instanceof S12PacketEntityVelocity) {

            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();

            if(packet.getEntityID() == mc.thePlayer.getEntityId()) {
                e.setCancelled(true);
            }
        }
    }
}
