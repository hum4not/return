package org.returnclient.module.impl.visual;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionValue;

public class WorldTime extends Module {

    OptionValue time;

    public WorldTime() {
        super("World Time", Category.visual);
        Return.getInstance().optionManager.addValue(time = new OptionValue("Time", this, 16000, 1000, 16000));
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.theWorld.setWorldTime((long) time.getValue());
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if(e.getPacket() instanceof S03PacketTimeUpdate) {
            e.setCancelled(true);
        }
    }
}
