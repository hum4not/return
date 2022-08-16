package org.returnclient.module.impl.player;

import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.module.Module;
import org.returnclient.options.OptionMode;
import org.returnclient.utils.TimerUtils;

public class NoFall extends Module {

    TimerUtils timer = new TimerUtils();

    OptionMode mode;

    String[] modes = { "Watchdog", "Packet", "Vanilla" };

    public NoFall() {
        super("No Fall", Category.player);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Watchdog", modes));
        setSuffix(mode.getMode());
    }

    @EventTarget
    public void onUpdate(EventMotion e) {
        setSuffix(mode.getMode());

        switch (mode.getMode()) {
            case "Watchdog":
                if(mc.thePlayer.fallDistance > 2.0) {
                    if(timer.hasTimeElapsed(125L, true)) {
                        e.setOnGround(true);
                    }
                }
                break;
            case "Packet":
                if(mc.thePlayer.fallDistance > 2.0) {
                    e.setOnGround(true);
                }
                break;
            case "Vanilla":
                if(mc.thePlayer.fallDistance > 2.0) {
                    mc.thePlayer.onGround = true;
                }
                break;
        }
    }

}
