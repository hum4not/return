package org.returnclient.ui;

import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;

public class UiTextField extends GuiTextField {

    public Minecraft mc = Minecraft.getMinecraft();

    public UiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }

    @Override
    public void drawTextBox() {
        if(this.getVisible()) {

            if(this.getEnableBackgroundDrawing()) {
                RenderUtils.drawRect(this.xPosition - 1.0, this.yPosition - 1.0, this.xPosition + this.width + 1.0, this.yPosition + this.height + 1.0, new Color(0x151515).hashCode());
                RenderUtils.drawRect(this.xPosition - 0.5, this.yPosition - 0.5, this.xPosition + this.width + 0.5, this.yPosition + this.height + 0.5, new Color(0x303030).hashCode());
                RenderUtils.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(0x202020).hashCode());
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = mc.fontRendererObj.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;

            if (k > s.length())
            {
                k = s.length();
            }

            if (!s.isEmpty())
            {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = (int) mc.fontRendererObj.drawStringWithShadow(s1, l, i1, i);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;

            if (!flag)
            {
                k1 = j > 0 ? l + this.width : l;
            }
            else if (flag2)
            {
                k1 = j1 - 1;
                --j1;
            }

            if (!s.isEmpty() && flag && j < s.length())
            {
                j1 = (int) mc.fontRendererObj.drawStringWithShadow(s.substring(j), (int) j1, (int) i1, i);
            }

            if (flag1)
            {
                if (flag2)
                {
                    RenderUtils.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + mc.fontRendererObj.FONT_HEIGHT, new Color(0x202020).hashCode());
                }
                else
                {
                    mc.fontRendererObj.drawStringWithShadow("_", (float)k1, (float)i1, i);
                }
            }

            if (k != j) {
                int l1 = l + mc.fontRendererObj.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + mc.fontRendererObj.FONT_HEIGHT);
            }
        }
    }
}
