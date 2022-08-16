package org.returnclient.module.impl.player;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.notifications.Notification;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.TimerUtils;

public class ChestStealer extends Module {

    TimerUtils timer = new TimerUtils();

    OptionValue delay;

    public ChestStealer() {
        super("Chest Stealer", Category.player);
        Return.getInstance().optionManager.addValue(delay = new OptionValue("Delay", this, 120.0, 80.0, 275.5));
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

        if (mc.currentScreen instanceof GuiChest) {

            if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;

                for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++) {
                    Slot slot = container.inventorySlots.get(i);

                    if (timer.hasTimeElapsed((long) delay.getValue(), false) && container.getLowerChestInventory().getStackInSlot(slot.slotNumber) != null) {
                        mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, 1, mc.thePlayer);
                        timer.reset();
                    }
                }
            }
        }
    }
}
