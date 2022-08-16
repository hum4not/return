package org.returnclient.module.impl.visual;

import net.minecraft.entity.player.EntityPlayer;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.module.Module;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class Radar extends Module {

    OptionValue x;
    OptionValue y;
    OptionValue size;

    public Radar() {
        super("Radar", Category.visual);
        Return.getInstance().optionManager.addValue(x = new OptionValue("X", this, 10.0, 0.0, 500.0));
        Return.getInstance().optionManager.addValue(y = new OptionValue("Y", this, 20.0, 0.0, 500.0));
        Return.getInstance().optionManager.addValue(size = new OptionValue("Size", this, 1.0, 0.5, 2.0));
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        double x = this.x.getValue();
        double y = this.y.getValue();

        double width = 100.0 * this.size.getValue();
        double height = 100.0 * this.size.getValue();

        RenderUtils.drawRect(x - 3.0, y - 3.0, x + width + 3.0, y + height + 3.0, new Color(0x202020).hashCode());
        RenderUtils.drawRect(x - 2.5, y - 2.5, x + width + 2.5, y + height + 2.5, new Color(0x353535).hashCode());
        RenderUtils.drawRect(x - 2.0, y - 2.0, x + width + 2.0, y + height + 2.0, new Color(0x282828).hashCode());
        RenderUtils.drawRect(x - 1.5, y - 1.5, x + width + 1.5, y + height + 1.5, new Color(0x353535).hashCode());
        RenderUtils.drawRect(x - 1.0, y - 1.0, x + width + 1.0, y + height + 1.0, new Color(0x202020).hashCode());

        RenderUtils.drawRect(x, y, x + width, y + height, new Color(0x101010).hashCode());

        RenderUtils.drawRect(x + width / 2 - 0.5 / 2, y, x + width / 2 - 0.5 / 2 + 0.5, y + height, new Color(0x757575).hashCode());
        RenderUtils.drawRect(x, y + height / 2 - 0.5 / 2, x + width, y + height / 2 - 0.5 / 2 + 0.5, new Color(0x757575).hashCode());

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if(mc.thePlayer != player) {

                double playerX = player.posX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks - mc.thePlayer.posX;
                double playerZ = player.posZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks - mc.thePlayer.posZ;

                double cos = Math.cos(mc.thePlayer.rotationYaw * (Math.PI / 180.0));
                double sin = Math.sin(mc.thePlayer.rotationYaw * (Math.PI / 180.0));

                double rotationX = -(playerX * cos + playerZ * sin);
                double rotationY = -(playerZ * cos - playerX * sin);

                if(rotationX >= width / 2 - 1.5 / 2) {
                    rotationX = width / 2 - 1.5 / 2;
                } else if (rotationX <= -(width / 2 - 1.5 / 2)) {
                    rotationX = -(width / 2 - 1.5 / 2);
                }

                if(rotationY >= height / 2 - 1.5 / 2) {
                    rotationY = height / 2 - 1.5 / 2;
                } else if(rotationY <= -(height / 2 - 1.5 / 2)) {
                    rotationY = -(height / 2 - 1.5 / 2);
                }

                int color = -1;

                if(mc.thePlayer.canEntityBeSeen(player)) {
                    color = new Color(0xFF0000).hashCode();
                } else {
                    color = new Color(0xFFFF00).hashCode();
                }

                RenderUtils.drawRect(x + width / 2 - 1.5 / 2 + rotationX, y + height / 2 - 1.5 / 2 + rotationY,x + width / 2 - 1.5 / 2 + rotationX + 1.5, y + height / 2 - 1.5 / 2 + rotationY + 1.5, color);
            }
        }
    }
}
