package org.returnclient.module.impl.misc;

import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove;
import net.minecraft.util.ChatComponentText;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.utils.TimerUtils;

public class Bypass extends Module {

    TimerUtils packetTimer = new TimerUtils();
    TimerUtils timer = new TimerUtils();

    OptionMode mode;

    String[] modes = { "Watchdog Off", "Silent Verus Off" };

    public Bypass() {
        super("Bypass", Category.misc);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Watchdog Off", modes));

        setSuffix(mode.getMode());
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if(mode.is("Silent Verus Off")) {
            if (packetTimer.hasTimeElapsed(250L, true)) {
                if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                    e.setCancelled(false);
                }
            } else {
                if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                    e.setCancelled(true);
                }
                mc.thePlayer.addChatMessage(new ChatComponentText("you possible fly now"));
            }

            if(e.getPacket() instanceof S00PacketKeepAlive || e.getPacket() instanceof S14PacketEntity) {
                e.setCancelled(true);
            }

            if(!mc.thePlayer.isDead) {
                if (e.getPacket() instanceof S05PacketSpawnPosition) {
                    e.setCancelled(true);
                }
            } else {
                if (e.getPacket() instanceof S07PacketRespawn) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mode.getMode());

        if(mode.is("Silent Verus Off")) {
            if(timer.hasTimeElapsed(250L, true)) {
                for(double value : new double[] { 0.0, 12.4, 12.6, 12.8, 0.0 })
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + value, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
            }
        }
    }
}
