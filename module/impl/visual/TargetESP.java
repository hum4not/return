package org.returnclient.module.impl.visual;

import net.minecraft.entity.player.EntityPlayer;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender3D;
import org.returnclient.module.Module;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class TargetESP extends Module {

    public TargetESP() {
        super("Target ESP", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender3D e) {
        for (EntityPlayer target : mc.theWorld.playerEntities) {
            if(mc.thePlayer != target) {

                if(!target.isDead && target.getHealth() > 0) {
                    if (mc.thePlayer.getDistanceToEntity(target) <= 3.5) {

                        double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
                        double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
                        double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

                        RenderUtils.drawBox(x, y + target.height - 0.4, z, 0.4, 0.1, target.hurtTime > 1.0 ? new Color(0xABB42222, true).hashCode() : new Color(0x9B2F8326, true).hashCode());
                        RenderUtils.drawOutlinedBox(x, y + target.height - 0.4, z, 0.4, 0.1, target.hurtTime > 1.0 ? new Color(0xEF8F2020, true).hashCode() : new Color(0xED328827, true).hashCode());
                    }
                }
            }
        }
    }

}
