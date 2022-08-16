package org.returnclient.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.scoreboard.Team;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.module.Module;
import org.returnclient.options.OptionBoolean;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.RotationUtils;
import org.returnclient.utils.TimerUtils;

public class KillAura extends Module {

    TimerUtils timer = new TimerUtils();

    EntityLivingBase target;

    OptionValue range;
    OptionValue aps;

    OptionBoolean throughWalls;
    OptionBoolean invisibles;
    OptionBoolean swing;
    OptionBoolean downRotation;

    OptionMode targets;

    String[] modes = { "Players", "Animals", "Mobs" };

    public KillAura() {
        super("Kill Aura", Category.combat);
        Return.getInstance().optionManager.addValue(range = new OptionValue("Range", this, 3.5, 1.0, 6.0));
        Return.getInstance().optionManager.addValue(aps = new OptionValue("APS", this, 10.0, 7.5, 15.0));
        Return.getInstance().optionManager.addBoolean(throughWalls = new OptionBoolean("Through Walls", this, true));
        Return.getInstance().optionManager.addBoolean(invisibles = new OptionBoolean("Invisibles", this, false));
        Return.getInstance().optionManager.addBoolean(swing = new OptionBoolean("Swing", this, true));
        Return.getInstance().optionManager.addBoolean(downRotation = new OptionBoolean("Down Rotation", this, false));
        Return.getInstance().optionManager.addMode(targets = new OptionMode("Targets", this, "Players", modes));
    }

    @EventTarget
    public void onMotion(EventMotion e) {

        target = getTarget(range.getValue());

        if(target == null)
            return;

        if(target.getDistanceToEntity(mc.thePlayer) <= range.getValue()) {

            float[] rotation = RotationUtils.getRotationToEntity(target);

            e.setYaw(rotation[0]);
            e.setPitch(rotation[1]);

            mc.thePlayer.rotationYawHead = rotation[0];
            mc.thePlayer.renderYawOffset = rotation[0];

            mc.thePlayer.rotationPitchHead = downRotation.isToggled() ? 90.0f : rotation[1];

            if(timer.hasTimeElapsed(1000 / (long) aps.getValue(), true)) {

                if(swing.isToggled()) {
                    mc.thePlayer.swingItem();
                } else {
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }

                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
        }
    }

    public boolean isValidEntity(EntityLivingBase entity) {
        if(entity instanceof EntityPlayer || entity instanceof EntityMob || entity instanceof EntityAnimal || entity instanceof EntityVillager) {
            if(entity instanceof EntityPlayer && targets.is("Players")) {
                return true;
            }
            if((entity instanceof EntityMob || entity instanceof EntityVillager) && targets.is("Mobs")) {
                return true;
            }
            if(entity instanceof EntityAnimal && targets.is("Animals")) {
                return true;
            }
            if(mc.thePlayer.canEntityBeSeen(entity)) {
                return throughWalls.isToggled();
            }
            if(!mc.thePlayer.isInvisible() && invisibles.isToggled()) {
                return true;
            }
        }
        return false;
    }

    public EntityLivingBase getTarget(double range) {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if(mc.thePlayer != entity) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase base = (EntityLivingBase) entity;

                    if (base.getDistanceToEntity(mc.thePlayer) <= range && isValidEntity(base)) {
                        return base;
                    }
                }
            }
        }
        return null;
    }
}
