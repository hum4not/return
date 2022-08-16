package org.returnclient.module.impl.visual;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldSettings;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.module.Module;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class ArmorOverlay extends Module {

    public ArmorOverlay() {
        super("Armor Overlay", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        ScaledResolution scaledResolution = e.getScaledResolution();

        int count = 0;

        double x = 0;
        double y = 0;

        if(mc.playerController.getCurrentGameType() == WorldSettings.GameType.SURVIVAL) {

            for (ItemStack stack : mc.thePlayer.inventory.armorInventory) {
                if (stack != null) {

                    x = scaledResolution.getScaledWidth() / 2 + 12.5 + count * (15.0);
                    y = scaledResolution.getScaledHeight() - 54.5;

                    RenderUtils.renderItem(stack, x, y);
                    count++;
                }
            }

            if (mc.thePlayer.getHeldItem() != null) {
                if (x < 1.0 && y < 1.0) {
                    RenderUtils.renderItem(mc.thePlayer.getHeldItem(), scaledResolution.getScaledWidth() / 2 + 12.5, scaledResolution.getScaledHeight() - 54.5);
                } else {
                    RenderUtils.renderItem(mc.thePlayer.getHeldItem(), x + 15, y);
                }
            }
        }
    }
}
