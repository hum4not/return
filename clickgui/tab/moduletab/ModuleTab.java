package org.returnclient.clickgui.tab.moduletab;

import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.input.Keyboard;
import org.returnclient.Return;
import org.returnclient.clickgui.tab.Tab;
import org.returnclient.clickgui.tab.moduletab.component.Component;
import org.returnclient.clickgui.tab.moduletab.component.impl.ComponentCheckbox;
import org.returnclient.clickgui.tab.moduletab.component.impl.ComponentCombo;
import org.returnclient.clickgui.tab.moduletab.component.impl.ComponentSlider;
import org.returnclient.clickgui.utils.HoverUtils;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.module.Module;
import org.returnclient.module.impl.config.OtherSettings;
import org.returnclient.options.Option;
import org.returnclient.options.OptionBoolean;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.RenderUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleTab {

    public List<Component> components = new ArrayList<Component>();

    public int x, y, width, height;

    public Module module;

    public boolean binding;

    public Tab parent;

    public ModuleTab(int x, int y, Module module, Tab parent) {
        this.x = x;
        this.y = y;
        this.width = 190;
        this.height = 15;
        this.module = module;
        this.parent = parent;

        int y3 = y + height;

        for (Option option : Return.getInstance().optionManager.options) {
            if(option.module == module) {

                if(option instanceof OptionBoolean) {
                    components.add(new ComponentCheckbox(x, y3, this, (OptionBoolean) option));
                    y3 += 10;

                } else if(option instanceof OptionValue) {
                    components.add(new ComponentSlider(x, y3, this, (OptionValue) option));
                    y3 += 10;

                } else if(option instanceof OptionMode) {
                    components.add(new ComponentCombo(x, y3, this, (OptionMode) option));
                    y3 += 10;
                }
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        RenderUtils.drawRect(x - 0.5, y - 0.5, x + width + 0.5, y + height + 0.5, new Color(13, 13, 13, parent.parent.targetAlpha).hashCode());
        RenderUtils.drawRect(x, y, x + width, y + height, new Color(7, 7, 7, parent.parent.targetAlpha).hashCode());

        if(!(module instanceof OtherSettings)) {
            RenderUtils.drawFilledCircle(x + width - 8.5, y + 7.5, 3.0, module.isToggled() ? new Color(155, 72, 16, parent.parent.targetAlpha).hashCode() : new Color(13, 13, 13, parent.parent.targetAlpha).hashCode());
            RenderUtils.drawCircle(x + width - 8.5, y + 7.5, 3.0, new Color(48, 48, 48, parent.parent.targetAlpha).hashCode());
        }

        CFontRenderer font = CFonts.tahoma;

        font.drawStringWithShadow(module.getName() + (module instanceof OtherSettings ? "" : ChatFormatting.DARK_GRAY + " [" + Keyboard.getKeyName(module.getBind()) + "]"), x + 4.5, y + height / 2 - font.getHeight() / 2 + 1.0, new Color(192, 192, 192, parent.parent.targetAlpha).hashCode());

        int y3 = y + height;

        for (Component component : components) {
            component.x = x;
            component.y = y3;

            y3 += 10;
        }

        components.forEach(c -> c.drawScreen(mouseX, mouseY, partialTicks));
    }

    public void keyTyped(char typedChar, int keyCode) {

        components.forEach(c -> c.keyTyped(typedChar, keyCode));

        if(binding) {
            module.setBind(keyCode);
            binding = false;

            if(keyCode == Keyboard.KEY_ESCAPE) {
                module.setBind(0);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        components.forEach(c -> c.mouseClicked(mouseX, mouseY, mouseButton));

        if(HoverUtils.hovered(x, y, x + width, y + height, mouseX, mouseY)) {
            if(mouseButton == 0) {
                module.toggle();
            } else if(mouseButton == 2) {
                binding = !binding;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        components.forEach(c -> c.mouseReleased(mouseX, mouseY, state));
    }

    public int getComponentHeight() {

        int height = 0;

        for (Option option : Return.getInstance().optionManager.options) {
            if(option.module == module) {
                height += 10;
            }
        }

        return height;
    }

    public Option getOptions() {
        for (Option option : Return.getInstance().optionManager.options) {
            if (option.module == module) {
                return option;
            }
        }

        return null;
    }
}
