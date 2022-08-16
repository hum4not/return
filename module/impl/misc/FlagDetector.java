package org.returnclient.module.impl.misc;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.module.Module;
import org.returnclient.module.impl.movement.LongJump;
import org.returnclient.module.impl.movement.Phase;
import org.returnclient.module.impl.movement.Scaffold;
import org.returnclient.module.impl.movement.Speed;
import org.returnclient.notifications.Notification;

public class FlagDetector extends Module {

    int stage;

    public FlagDetector() {
        super("Flag Detector", Category.misc);
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if(e.getPacket() instanceof S08PacketPlayerPosLook) {
            if(mc.thePlayer.ticksExisted > 4) {

                for (Module module : Return.getInstance().moduleManager.modules) {
                    if(module instanceof LongJump || module instanceof Speed || module instanceof Phase || module instanceof Scaffold) {
                        if(module.isToggled()) {
                            Return.getInstance().notificationManager.addNotification(new Notification(module.getName() + " disabled", "This feature requires Bypass.", Notification.NotificationType.warning));
                            module.toggle();
                        }
                    }
                }
            }
        }
    }
}
