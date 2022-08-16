package org.returnclient.module.impl.misc;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.module.Module;

import java.util.ArrayList;
import java.util.List;

public class Blink extends Module {

    public List<Packet> packetList = new ArrayList<Packet>();

    EntityOtherPlayerMP fakePlayer;

    public Blink() {
        super("Blink", Category.misc);
    }

    @Override
    public void onEnable() {
        fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        fakePlayer.setHealth(mc.thePlayer.getHealth());
        mc.theWorld.addEntityToWorld(-100, fakePlayer);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.theWorld.removeEntity(fakePlayer);
        super.onDisable();
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        e.setOnGround(true);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if(e.getPacket() instanceof C06PacketPlayerPosLook) {

            if(mc.thePlayer.ticksExisted % 15 == 0) {
                C06PacketPlayerPosLook packetPlayerPosLook = (C06PacketPlayerPosLook) e.getPacket();

                packetPlayerPosLook.setMoving(false);
                packetPlayerPosLook.onGround = false;
                packetPlayerPosLook.rotating = false;

                e.setCancelled(true);
            }
        }

        if(e.getPacket() instanceof C03PacketPlayer) {

            C03PacketPlayer player = (C03PacketPlayer)e.getPacket();

            player.setMoving(true);

            e.setCancelled(true);
        }
    }

}
