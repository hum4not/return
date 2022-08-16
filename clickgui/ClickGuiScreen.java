package org.returnclient.clickgui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Display;
import org.returnclient.clickgui.tab.Tab;
import org.returnclient.clickgui.tab.moduletab.component.impl.ComponentSlider;
import org.returnclient.clickgui.utils.HoverUtils;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.module.Module.Category;
import org.returnclient.module.ModuleManager;
import org.returnclient.module.impl.config.OtherSettings;
import org.returnclient.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends GuiScreen {

    public List<Tab> tabs = new ArrayList<Tab>();

    public int x, y, width, height, dragX, dragY, lastX, lastY;

    public boolean dragging, rescaling;

    public int targetAlpha;

    public ClickGuiScreen() {
        this.x = 15;
        this.y = 15;
        this.width = 405;
        this.height = 275;

        int x1 = x + 10;

        for (Category value : Category.values()) {
            tabs.add(new Tab(x1, y + 22, value, this));
            x1 += 60;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        if (!(width < 405 || width > 625)) {
            if (!(height < 275 || height > 445)) {
                if (rescaling) {
                    width = mouseX - lastX;
                    height = mouseY - lastY;
                }
            } else {
                height = height > 445 ? 445 : 275;
                rescaling = false;
            }
        } else {
            width = width > 625 ? 625 : 405;
            rescaling = false;
        }

        OtherSettings settings = (OtherSettings) ModuleManager.getModule("Other Settings");

        if(settings.animation.isToggled()) {
            if (targetAlpha < 255) {
                targetAlpha += 3;
            }
        } else {
            targetAlpha = 255;
        }

        RenderUtils.drawRect(x, y, x + width, y + height, new Color(3, 3, 3, targetAlpha).hashCode());

        RenderUtils.drawRect(x, y, x + width, y + 20.5, new Color(13, 13, 13, targetAlpha).hashCode());
        RenderUtils.drawRect(x, y, x + width, y + 20.0, new Color(3, 3, 3, targetAlpha).hashCode());

        RenderUtils.drawRect(x, y + 20.5, x + width, y + 20.5 + 20.5, new Color(13, 13, 13, targetAlpha).hashCode());
        RenderUtils.drawRect(x, y + 20.5, x + width, y + 20.5 + 20.0, new Color(7, 7, 7, targetAlpha).hashCode());

        if(settings.resizable.isToggled()) {
            RenderUtils.drawRect(x + width - 3.5, y + height - 12.5, x + width - 3.5 + 1.5, y + height - 12.5 + 10.5, new Color(37, 37, 37, targetAlpha).hashCode());
            RenderUtils.drawRect(x + width - 12.5, y + height - 3.5, x + width - 12.5 + 10.5, y + height - 3.5 + 1.5, new Color(37, 37, 37, targetAlpha).hashCode());
        }

        CFontRenderer font = CFonts.tahoma_big;

        font.drawStringWithShadow("Return", x + 5.5, y + 5.5, new Color(192, 192, 192, targetAlpha).hashCode());

        font = CFonts.tahoma;

        font.drawStringWithShadow("1.0", x + 45.5, y + 10.0, new Color(69, 69, 69, targetAlpha).hashCode());

        int x1 = x + 10;

        for (Tab tab : tabs) {
            tab.x = x1;
            tab.y = y + 22;

            x1 += tab.width;
        }

        tabs.forEach(t -> t.drawScreen(mouseX, mouseY, partialTicks));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        tabs.forEach(t -> t.keyTyped(typedChar, keyCode));
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        tabs.forEach(t -> t.mouseClicked(mouseX, mouseY, mouseButton));

        if(HoverUtils.hovered(x, y, x + width - 10, y + height - 10, mouseX, mouseY)) {
            if(mouseButton == 0 && !ComponentSlider.isHovering) {
                dragX = mouseX - x;
                dragY = mouseY - y;
                dragging = true;
            }
        }

        OtherSettings settings = (OtherSettings) ModuleManager.getModule("Other Settings");

        if(settings.resizable.isToggled()) {
            if (HoverUtils.hovered(x + width - 10, y + height - 10, x + width - 10 + 10, y + height - 10 + 10, mouseX, mouseY)) {
                if (mouseButton == 0) {
                    lastX = mouseX - width;
                    lastY = mouseY - height;
                    rescaling = true;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        tabs.forEach(t -> t.mouseReleased(mouseX, mouseY, state));
        dragging = false;
        rescaling = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        tabs.forEach(t -> t.handleMouseInput());
        super.handleMouseInput();
    }

    @Override
    public void onGuiClosed() {
        this.targetAlpha = 0;
        super.onGuiClosed();
    }
}
