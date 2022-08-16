package org.returnclient.module.impl.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;

import java.util.Arrays;

public class InventoryWalk extends Module {

    KeyBinding[] keys = new KeyBinding[] {mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSneak};

    public InventoryWalk() {
        super("Inventory Walk", Category.movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            Arrays.asList(keys).forEach(key -> KeyBinding.setKeyBindState(key.getKeyCode(), Keyboard.isKeyDown(key.getKeyCode())));
        }
    }
}
