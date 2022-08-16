package org.returnclient.clickgui.tab.moduletab.component.impl;

import org.returnclient.clickgui.tab.moduletab.ModuleTab;
import org.returnclient.clickgui.tab.moduletab.component.Component;
import org.returnclient.clickgui.utils.HoverUtils;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.RenderUtils;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComponentSlider extends Component {

    public static boolean isHovering;

    OptionValue option;

    boolean dragging;

    public ComponentSlider(int x, int y, ModuleTab parent, OptionValue option) {
        super(x, y, parent);
        this.option = option;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        double percentValue = ((75.5)) * (option.getValue() - option.getMin()) / (option.getMax() - option.getMin());

        RenderUtils.drawRect(x - 0.5, y + 0.5, x + width + 0.5, y + 0.5 + height + 0.5, new Color(13, 13, 13, parent.parent.parent.targetAlpha).hashCode());
        RenderUtils.drawRect(x, y + 0.5, x + width, y + 0.5 + height, new Color(7, 7, 7, parent.parent.parent.targetAlpha).hashCode());

        CFontRenderer font = CFonts.tahoma;

        RenderUtils.drawRect(x + width - font.getStringWidth(String.format("%.1f", option.getValue())) - 85.5, y + 5.0, x + width - font.getStringWidth(String.format("%.1f", option.getValue())) - 85.5 + 75.5, y + 5.0 + 1.5, new Color(41, 41, 41, parent.parent.parent.targetAlpha).hashCode());

        RenderUtils.drawFilledCircle(x + width - font.getStringWidth(String.format("%.1f", option.getValue())) - 85.5 + percentValue, y + 5.5, 2.5, new Color(155, 72, 16, parent.parent.parent.targetAlpha).hashCode());
        RenderUtils.drawCircle(x + width - font.getStringWidth(String.format("%.1f", option.getValue())) - 85.5 + percentValue, y + 5.5, 2.5, new Color(155, 72, 16, parent.parent.parent.targetAlpha).hashCode());

        font.drawStringWithShadow(option.name, x + 4.5, y + height / 2 - font.getHeight() / 2 + 1.0 + 0.5, new Color(192, 192, 192, parent.parent.parent.targetAlpha).hashCode());
        font.drawStringWithShadow(String.format("%.1f", option.getValue()), x + width - font.getStringWidth(String.format("%.1f", option.getValue())) - 4.5, y + height / 2 - font.getHeight() / 2 + 1.0 + 0.5, new Color(192, 192, 192, parent.parent.parent.targetAlpha).hashCode());

        if(dragging) {
            double diff = Math.min((75.5), Math.max(0, mouseX - (x + width - font.getStringWidth(String.format("%.1f", option.getValue())) - 85.5)));

            double min = option.getMin();
            double max = option.getMax();

            if (diff == 0) {
                option.setValue(option.getMin());
            } else {
                double value = roundToPlace(((diff / (75.5)) * (max - min) + min), 2);
                option.setValue(value);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(HoverUtils.hovered(x, y, x + width, y + height, mouseX, mouseY)) {
            if(mouseButton == 0) {
                dragging = true;
                isHovering = true;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        isHovering = false;
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
