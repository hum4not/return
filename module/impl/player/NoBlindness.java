package org.returnclient.module.impl.player;

import net.minecraft.potion.Potion;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;

public class NoBlindness extends Module {

    public NoBlindness() {
        super("No Blindness", Category.player);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.thePlayer.isPotionActive(Potion.blindness)) {
            mc.thePlayer.removePotionEffect(Potion.blindness.id);
        }
    }

}
