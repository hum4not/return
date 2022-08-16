package org.returnclient.module.impl.combat;

import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.TimerUtils;

public class AutoPot extends Module {

    TimerUtils timer = new TimerUtils();

    OptionMode mode;

    String[] modes = { "Jump Only", "Always" };

    public AutoPot() {
        super("Auto Pot", Category.combat);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Jump Only", modes));
        setSuffix(mode.getMode());
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

        setSuffix(mode.getMode());

        int slotID = -1;

        ItemStack stack = mc.thePlayer.getHeldItem();

        if (!(mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion || mc.thePlayer.getHeldItem().getItem() instanceof ItemExpBottle)) {

            for (int i = 0; i < 9; i++) {

                stack = mc.thePlayer.inventory.getStackInSlot(i);

                if (stack.getItem() instanceof ItemPotion) {

                    slotID = mc.thePlayer.inventory.currentItem;

                    mc.thePlayer.inventory.currentItem = i;
                    mc.playerController.updateController();

                    break;
                }
            }
        }

        if (!(stack.getItem() instanceof ItemPotion))
            return;

        if (mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion || mc.thePlayer.getHeldItem().getItem() instanceof ItemExpBottle)) {
            if((mode.is("Jump Only") ? !mc.thePlayer.onGround : mc.thePlayer.onGround)) {

                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;

                if(timer.hasTimeElapsed(1000 / 10, true)) {
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }
            }
        }

        if (slotID != -1) {
            mc.thePlayer.inventory.currentItem = slotID;
            mc.playerController.updateController();
        }
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        if(mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion || mc.thePlayer.getHeldItem().getItem() instanceof ItemExpBottle)) {
            e.setPitch(90.0f);
        }
    }

}
