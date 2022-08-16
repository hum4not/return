package org.returnclient.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {

    public List<EntityPlayer> bots = new ArrayList<EntityPlayer>();

    OptionMode mode;

    String[] modes = { "Watchdog", "Normal" };

    public AntiBot() {
        super("Anti Bot", Category.combat);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Watchdog", modes));
        setSuffix(mode.getMode());
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mode.getMode());

        if(mode.is("Watchdog")) {

            for (Entity entity : mc.theWorld.playerEntities) {
                if(mc.thePlayer != entity) {
                    if(entity.isInvisible() || (entity.isInvisible() && entity.noClip)) {
                        mc.theWorld.removeEntity(entity);
                    }
                }
            }

        } else if(mode.is("Normal")) {

            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (mc.thePlayer != entity) {
                    if (entity.isInvisible()) {
                        mc.theWorld.removeEntity(entity);
                    }
                }
            }
        }
    }
}
