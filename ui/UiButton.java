package org.returnclient.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

public class UiButton extends GuiButton {

    private int fade;

    public UiButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public UiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {

            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

            CFontRenderer font = CFonts.verdana;

            RenderUtils.drawRect(this.xPosition - 1.0, this.yPosition - 1.0, this.xPosition + this.width + 1.0, this.yPosition + this.height + 1.0, new Color(0x151515).hashCode());
            RenderUtils.drawRect(this.xPosition - 0.5, this.yPosition - 0.5, this.xPosition + this.width + 0.5, this.yPosition + this.height + 0.5, hovered ? new Color(0x353535).hashCode() : new Color(0x303030).hashCode());
            RenderUtils.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(0x202020).hashCode());

            font.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - font.getHeight()) / 2 + 1.0, -1);
        }
    }
}
