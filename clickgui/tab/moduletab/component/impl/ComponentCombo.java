package org.returnclient.clickgui.tab.moduletab.component.impl;

import org.returnclient.clickgui.tab.moduletab.ModuleTab;
import org.returnclient.clickgui.tab.moduletab.component.Component;
import org.returnclient.clickgui.utils.HoverUtils;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.options.OptionMode;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class ComponentCombo extends Component {

    OptionMode option;

    public ComponentCombo(int x, int y, ModuleTab parent, OptionMode option) {
        super(x, y, parent);
        this.option = option;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        RenderUtils.drawRect(x - 0.5, y + 0.5, x + width + 0.5, y + 0.5 + height + 0.5, new Color(13, 13, 13, parent.parent.parent.targetAlpha).hashCode());
        RenderUtils.drawRect(x, y + 0.5, x + width, y + 0.5 + height, new Color(7, 7, 7, parent.parent.parent.targetAlpha).hashCode());

        CFontRenderer font = CFonts.tahoma;

        font.drawStringWithShadow(option.name, x + 4.5, y + height / 2 - font.getHeight() / 2 + 1.0 + 0.5, new Color(192, 192, 192, parent.parent.parent.targetAlpha).hashCode());
        font.drawStringWithShadow(option.getMode(), x + width - font.getStringWidth(option.getMode()) - 4.5, y + height / 2 - font.getHeight() / 2 + 1.0 + 0.5, new Color(192, 192, 192, parent.parent.parent.targetAlpha).hashCode());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(HoverUtils.hovered(x, y, x + width, y + height, mouseX, mouseY)) {
            if(mouseButton == 0) {
                option.cycle();
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
