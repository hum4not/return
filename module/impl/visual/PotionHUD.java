package org.returnclient.module.impl.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import javafx.animation.Interpolator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.module.Module;
import org.returnclient.utils.RenderUtils;

import java.awt.*;
import java.util.HashMap;

public class PotionHUD extends Module {

    public PotionHUD() {
        super("Potion HUD", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        ScaledResolution scaledResolution = e.getScaledResolution();

        int var = 0;

        for (PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {

            String level = null;

            switch (potionEffect.getAmplifier()) {
                case 1:
                    level = " " + I18n.format("enchantment.level.2");
                    break;
                case 2:
                    level = " " + I18n.format("enchantment.level.3");
                    break;
                case 3:
                    level = " " + I18n.format("enchantment.level.4");
                    break;
            }

            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];

            CFontRenderer font = CFonts.tahoma;

            String name = null;

            if(level != null) {
                name = I18n.format(potionEffect.getEffectName()) + " " + level;
            } else {
                name = I18n.format(potionEffect.getEffectName());
            }

            font.drawStringWithShadow(name + " " + ChatFormatting.GRAY + Potion.getDurationString(potionEffect), scaledResolution.getScaledWidth() - 2.5 - font.getStringWidth(name + " " + ChatFormatting.GRAY + Potion.getDurationString(potionEffect)), scaledResolution.getScaledHeight() - (mc.currentScreen instanceof GuiChat ? 32.5 : 20.5) + var * (9.5), potion.getLiquidColor());
            var--;
        }
    }

}
