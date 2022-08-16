package org.returnclient.clickgui.tab.moduletab.component;

import org.returnclient.clickgui.tab.moduletab.ModuleTab;
import org.returnclient.clickgui.utils.HoverUtils;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class Component {

    public int x, y, width, height;

    public ModuleTab parent;

    public Component(int x, int y, ModuleTab parent) {
        this.x = x;
        this.y = y;
        this.width = 190;
        this.height = 10;
        this.parent = parent;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}
