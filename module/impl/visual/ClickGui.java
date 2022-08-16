package org.returnclient.module.impl.visual;

import org.lwjgl.input.Keyboard;
import org.returnclient.Return;
import org.returnclient.module.Module;

public class ClickGui extends Module {

    public ClickGui() {
        super("Click Gui", Category.visual);
        setBind(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Return.clickGuiScreen);
        toggle();
        super.onEnable();
    }
}
