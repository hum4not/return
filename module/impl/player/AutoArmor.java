package org.returnclient.module.impl.player;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;

import java.util.Arrays;

public class AutoArmor extends Module {
    
    public AutoArmor() {
        super("Auto Armor", Category.player);
    }
    
    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.thePlayer != null && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)) {

            for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {

            }
        }
    }
}
