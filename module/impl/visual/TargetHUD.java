package org.returnclient.module.impl.visual;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.module.Module;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.HealthUtils;
import org.returnclient.utils.MathUtils;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class TargetHUD extends Module {

    OptionValue x;
    OptionValue y;

    public TargetHUD() {
        super("Target HUD", Category.visual);
        Return.getInstance().optionManager.addValue(x = new OptionValue("X", this, 425.0, 0.0, 550));
        Return.getInstance().optionManager.addValue(y = new OptionValue("Y", this, 285.0, 0.0, 500));
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        CFontRenderer font = CFonts.tahoma_small;

        int count = 0;

        for (EntityPlayer target : mc.theWorld.playerEntities) {
            if(mc.thePlayer != target) {
                if(!target.isDead && target.getHealth() > 0) {

                    if(mc.thePlayer.getDistanceToEntity(target) <= 4) {

                        double width = 15.5 + 95.5;
                        double height = 35.5;

                        double x = this.x.getValue();
                        double y = this.y.getValue() + count * (height + 8.5);

                        RenderUtils.drawRect(x - 3.0, y - 3.0, x + width + 3.0, y + height + 3.0, new Color(0x202020).hashCode());
                        RenderUtils.drawRect(x - 2.5, y - 2.5, x + width + 2.5, y + height + 2.5, new Color(0x353535).hashCode());
                        RenderUtils.drawRect(x - 2.0, y - 2.0, x + width + 2.0, y + height + 2.0, new Color(0x282828).hashCode());
                        RenderUtils.drawRect(x - 1.5, y - 1.5, x + width + 1.5, y + height + 1.5, new Color(0x353535).hashCode());
                        RenderUtils.drawRect(x - 1.0, y - 1.0, x + width + 1.0, y + height + 1.0, new Color(0x202020).hashCode());

                        RenderUtils.drawRect(x, y, x + width, y + height, new Color(0x101010).hashCode());

                        RenderUtils.drawRect(x + 27.5, y + 10.5, x + width - 18.0, y + 10.5 + 3.0, new Color(0x202020).hashCode());
                        RenderUtils.drawRect(x + 27.5, y + 10.5, MathUtils.getInputFromPercent((target.getHealth() / target.getMaxHealth()) * 100, x + 27.5, x + width - 18.0), y + 10.5 + 3.0, HealthUtils.getHealthColor(target.getHealth()));

                        for(double i = 0; i < 11.5; i++) {
                            RenderUtils.drawRect(x + 27.5 + 6.5 * i, y + 10.5, x + 27.5 + 6.5 * i + 0.5, y + 10.5 + 3.0, new Color(0x151515).hashCode());
                        }

                        font.drawStringWithShadow(target.getName(), x + 27.5, y + 3.5, -1);

                        font = CFonts.verdana;

                        String stats = "HP: " + String.format("%.0f", target.getHealth()) + " | " + "Dist: " + String.format("%.0f", mc.thePlayer.getDistanceToEntity(target));

                        font.drawStringWithShadow(stats, x + 27.5, y + 17.5, -1);

                        int armorCount = 0;

                        for (ItemStack stack : target.inventory.armorInventory) {
                            if(stack != null) {

                                GL11.glPushMatrix();

                                GL11.glTranslated(x + 27.5 + armorCount * (12.5), y + 22.5, 0);

                                GL11.glScaled(0.80, 0.80, 1);

                                RenderUtils.renderItem(stack, 0, 0);

                                GL11.glPopMatrix();

                                armorCount++;
                            }
                        }

                        GL11.glPushMatrix();

                        GL11.glColor4f(1, 1, 1, 1);

                        GuiInventory.drawEntityOnScreen((int) (x + 13.5), (int) (y + height - 3.5), (int) 15.5, target.rotationYaw, target.rotationPitch, target);

                        GL11.glPopMatrix();
                        count++;
                    }
                }
            }
        }
    }

}
