package org.returnclient.module.impl.visual;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;

public class Brightness extends Module {

    OptionMode mode;

    String[] modes = { "Potion", "Gamma" };

    public Brightness() {
        super("Brightness", Category.visual);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Potion", modes));
    }

    @Override
    public void onDisable() {
        switch (mode.getMode()) {
            case "Potion":
                if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
                    mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                }
                break;
            case "Gamma":
                mc.gameSettings.saturation = 1.0f;
                break;
        }
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        switch (mode.getMode()) {
            case "Potion":
                if(mc.gameSettings.saturation > 1.0f) {
                    mc.gameSettings.saturation = 1.0f;
                }
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5200, 1));
                break;
            case "Gamma":
                if(mc.gameSettings.saturation == 1.0f || mc.gameSettings.saturation < 1.0f) {
                    mc.gameSettings.saturation = 100.0f;
                }
                if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
                    mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                }
                break;
        }
    }
}
