package org.returnclient.module.impl.combat;

import org.returnclient.Return;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;

public class Criticals extends Module {

    public OptionMode mode;

    String[] modes = { "Jump", "Packet", "Ground" };

    public Criticals() {
        super("Criticals", Category.combat);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Jump", modes));
        setSuffix(mode.getMode());
    }
}
