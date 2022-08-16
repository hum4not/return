package org.returnclient.module.impl.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.notifications.Notification;
import org.returnclient.utils.MoveUtils;
import org.returnclient.utils.TimerUtils;

import java.util.ArrayList;
import java.util.List;

public class HackerDetector extends Module {

    List<Hacker> hackers = new ArrayList<Hacker>();

    TimerUtils timer = new TimerUtils();

    public HackerDetector() {
        super("Hacker Detector", Category.misc);
    }

    @Override
    public void onEnable() {
        hackers.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        hackers.clear();
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

        hackers.clear();

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if(mc.thePlayer != player) {

                if(mc.thePlayer.getDistanceToEntity(player) <= 85) {

                    for (C02PacketUseEntity.Action value : C02PacketUseEntity.Action.values()) {
                        C02PacketUseEntity c02PacketUseEntity = new C02PacketUseEntity(player, value);

                        if(player.isBlocking()) {
                            if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                                hackers.add(new Hacker(player.getName(), "Auto Block"));
                            }
                        }
                    }
                }
            }
        }

        for (Hacker hacker : hackers) {
            if(timer.hasTimeElapsed(2750L, false)) {
                Return.getInstance().notificationManager.addNotification(new Notification("Hacker Detected", hacker.getName() + " is using " + hacker.getMassage() + "!", Notification.NotificationType.warning));
                mc.thePlayer.sendChatMessage("/report " + hacker.getName());
            }
        }
    }

    private class Hacker {

        private String name, massage;

        public Hacker(String name, String massage) {
            this.name = name;
            this.massage = massage;
        }

        public String getName() {
            return name;
        }

        public String getMassage() {
            return massage;
        }
    }

}
