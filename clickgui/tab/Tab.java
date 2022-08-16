package org.returnclient.clickgui.tab;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.returnclient.Return;
import org.returnclient.clickgui.ClickGuiScreen;
import org.returnclient.clickgui.tab.moduletab.ModuleTab;
import org.returnclient.clickgui.utils.HoverUtils;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.module.Module;
import org.returnclient.module.Module.Category;
import org.returnclient.utils.RenderUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tab {

    public List<ModuleTab> tabs = new ArrayList<ModuleTab>();

    public int x, y, width, height;

    public Category category;

    public boolean extended;

    public ClickGuiScreen parent;

    public float currentScroll;

    public Tab(int x, int y, Category category, ClickGuiScreen parent) {
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 15;
        this.category = category;

        this.parent = parent;

        int x2 = parent.x + 10;
        int y1 = y + height + 15;

        for (Module module : Return.getInstance().moduleManager.modules) {
            if (module.getCategory() == category) {

                if ((x2 - parent.x + 10) + 195 > parent.width + 195) {
                    x2 = parent.x + 10;
                    y1 += 20;
                }

                tabs.add(new ModuleTab(x2, y1, module, this));
                x2 += 195;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        RenderUtils.drawRect(x + 1.5, y + height - 1.5, x + width - 1.5, y + height - 1.5 + 1.5, extended ? new Color(128, 56, 7, parent.targetAlpha).hashCode() : new Color(69, 69, 69, parent.targetAlpha).hashCode());

        CFontRenderer font = CFonts.tahoma;

        font.drawStringWithShadow(category.getName(), x + 4.5, y + 4.5, extended ? new Color(192, 192, 192, parent.targetAlpha).hashCode() : new Color(69, 69, 69, parent.targetAlpha).hashCode());

        if(extended) {

            int x2 = parent.x + 10;
            int y1 = y + height + 15;

            for (ModuleTab tab : tabs) {

                if(currentScroll > 0) currentScroll = 0.0f;

                if((y1 - y + height) + 20 > parent.height && ((x2 - parent.x + 10) + 195 < parent.width)) {
                    x2 += 195;
                    y1 = y + height + 15;
                }

                tab.x = x2;
                tab.y = y1 + (int) currentScroll;

                y1 += 20 + tab.getComponentHeight();
            }

            GL11.glPushMatrix();

            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            RenderUtils.prepareScissorBox(parent.x + 10, y + height + 15, parent.x + 10 + parent.width - 10, y + height + 15 + parent.height - 60);

            tabs.forEach(t -> t.drawScreen(mouseX, mouseY, partialTicks));

            GL11.glDisable(GL11.GL_SCISSOR_TEST);

            GL11.glPopMatrix();
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if(extended) {
            tabs.forEach(t -> t.keyTyped(typedChar, keyCode));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        if(extended) {
            tabs.forEach(t -> t.mouseClicked(mouseX, mouseY, mouseButton));
        }

        if(HoverUtils.hovered(x, y, x + width, y + height, mouseX, mouseY)) {
            if(mouseButton == 0) {
                extended = !extended;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(extended) {
            tabs.forEach(t -> t.mouseReleased(mouseX, mouseY, state));
        }
    }

    public void handleMouseInput() {
        int i = Mouse.getEventDWheel();

        i = Integer.compare(i, 0);

        currentScroll += i * 5;
    }
}
