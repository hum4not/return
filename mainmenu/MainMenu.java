package org.returnclient.mainmenu;

import com.mojang.realmsclient.gui.ChatFormatting;
import org.returnclient.Return;
import org.returnclient.altmanager.AltManager;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.glslsandbox.GLSLSandboxShader;
import org.returnclient.ui.UiButton;
import org.returnclient.utils.RenderUtils;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL20;

public class MainMenu extends GuiScreen {

    private List<ChangeLog> logs = new ArrayList<ChangeLog>();

    private GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();

    private ChangeLog guiLog;
    private ChangeLog modulesLog;

    public MainMenu() {
        try {
            this.backgroundShader = new GLSLSandboxShader("/noise.fsh");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load backgound shader", e);
        }

        logs.add(new ChangeLog("Added new Click GUI", ChangeLogType.added));
        logs.add(new ChangeLog("Added new ESP with options", ChangeLogType.added));
        logs.add(new ChangeLog("Added Hit Sound", ChangeLogType.added));
        logs.add(new ChangeLog("Added No Slow Down", ChangeLogType.added));
        logs.add(new ChangeLog("Added new Chams", ChangeLogType.added));
        logs.add(new ChangeLog("Added Slow Swing Animation", ChangeLogType.added));
        logs.add(new ChangeLog("Added Arrows", ChangeLogType.added));
        logs.add(new ChangeLog("Added new Chest ESP with options", ChangeLogType.added));
        logs.add(new ChangeLog("Added new Tags", ChangeLogType.added));
        logs.add(new ChangeLog("Added new Item ESP with options", ChangeLogType.added));
        logs.add(new ChangeLog("Added new Target Strage", ChangeLogType.added));
        logs.add(new ChangeLog("Improved Velocity", ChangeLogType.improved));
        logs.add(new ChangeLog("Improved Kill Aura, and fixed bugs", ChangeLogType.improved));
        logs.add(new ChangeLog("Removed Trails", ChangeLogType.removed));
        logs.add(new ChangeLog("Removed old Disabler", ChangeLogType.removed));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        CFontRenderer font = CFonts.tahoma;

        GlStateManager.disableCull();

        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY, (System.currentTimeMillis() - initTime) / 1000f);

        glBegin(GL_QUADS);

        glVertex2f(-1f, -1f);
        glVertex2f(-1f, 1f);
        glVertex2f(1f, 1f);
        glVertex2f(1f, -1f);

        glEnd();

        GL20.glUseProgram(0);

        glPushMatrix();

        glScaled(2.0, 2.0, 2.0);

        mc.fontRendererObj.drawStringWithShadow("Return", width / 4 - mc.fontRendererObj.getStringWidth("Return") / 2, height / 20, new Color(0x930000).hashCode());

        glPopMatrix();

        font.drawStringWithShadow(ChatFormatting.GRAY + "(<" + ChatFormatting.WHITE + " Change Log " + ChatFormatting.GRAY + ">)", 1.5, 1.5, -1);

        String welcome = ChatFormatting.GRAY + "Welcome, " + ChatFormatting.WHITE + "user";

        font.drawStringWithShadow(welcome, width - font.getStringWidth(welcome) - 1.5, 1.5, -1);

        font.drawStringWithShadow("Current Return Build " + "(#" + Return.getInstance().getBuild() + ")", width - font.getStringWidth("Current Return Build " + "(#" + Return.getInstance().getBuild() + ")") - 1.5, height - 10, new Color(0x909090).hashCode());
        font.drawStringWithShadow("Current Version " + ChatFormatting.GRAY + "1.8.x", 1.5, height - 10, -1);

        int var = 0;
        String name = null;

        for (ChangeLog changeLog : logs) {
            if (changeLog != null) {

                if (changeLog.type == ChangeLogType.added) {
                    name = "[" + ChatFormatting.DARK_GREEN + "+" + ChatFormatting.WHITE + "]" + ChatFormatting.DARK_GRAY + " | " + ChatFormatting.WHITE + changeLog.getName();
                } else if (changeLog.type == ChangeLogType.removed) {
                    name = "[" + ChatFormatting.DARK_RED + "-" + ChatFormatting.WHITE + "]" + ChatFormatting.DARK_GRAY + " | " + ChatFormatting.WHITE + changeLog.getName();
                    ;
                } else if (changeLog.type == ChangeLogType.improved) {
                    name = "[" + ChatFormatting.GRAY + "/" + ChatFormatting.WHITE + "]" + ChatFormatting.DARK_GRAY + " | " + ChatFormatting.WHITE + changeLog.getName();
                }

                font.drawStringWithShadow(name, 6, 12 + var * (font.getHeight() + 2.5), -1);
                var++;
            }
        }

        int x = width / 2 - 55;
        int y = height / 2 - 35;

        int width = 105;
        int height = 25 * buttonList.size() - 5;

        RenderUtils.drawRect(x - 3.0, y - 3.0, x + width + 3.0, y + height + 3.0, new Color(0x202020).hashCode());
        RenderUtils.drawRect(x - 2.5, y - 2.5, x + width + 2.5, y + height + 2.5, new Color(0x353535).hashCode());
        RenderUtils.drawRect(x - 2.0, y - 2.0, x + width + 2.0, y + height + 2.0, new Color(0x282828).hashCode());
        RenderUtils.drawRect(x - 1.5, y - 1.5, x + width + 1.5, y + height + 1.5, new Color(0x353535).hashCode());
        RenderUtils.drawRect(x - 1.0, y - 1.0, x + width + 1.0, y + height + 1.0, new Color(0x202020).hashCode());

        RenderUtils.drawRect(x, y, x + width, y + height, new Color(0x101010).hashCode());

        RenderUtils.drawRect(x + 5, y + 5, x + width - 5, y + height - 5, new Color(0x303030).hashCode());
        RenderUtils.drawRect(x + 5.5, y + 5.5, x + width - 5.5, y + height - 5.5, new Color(0x101010).hashCode());

        glPushMatrix();

        font = CFonts.verdana;

        font.drawStringWithShadow("Main Menu", x + 8, y + 4.5, -1);

        glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {

        this.buttonList.add(new UiButton(0, width / 2 - 40, height / 2 - 20, 75, 15, "Singleplayer"));
        this.buttonList.add(new UiButton(1, width / 2 - 40, height / 2, 75, 15, "Multiplayer"));
        this.buttonList.add(new UiButton(2, width / 2 - 40, height / 2 + 20, 75, 15, "Accounts"));
        this.buttonList.add(new UiButton(3, width / 2 - 40, height / 2 + 40, 75, 15, "Languages"));
        this.buttonList.add(new UiButton(4, width / 2 - 40, height / 2 + 60, 75, 15, "Settings"));
        this.buttonList.add(new UiButton(5, width / 2 - 40, height / 2 + 80, 75, 15, "Quit"));

        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 1) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 2) {
            mc.displayGuiScreen(new AltManager());
        }
        if (button.id == 3) {
            mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
        }
        if (button.id == 4) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
        if (button.id == 5) {
            mc.shutdown();
        }
        super.actionPerformed(button);
    }

    public class ChangeLog {

        public String name;
        public ChangeLogType type;

        public String getName() {
            return name;
        }

        public ChangeLog(String name, ChangeLogType type) {
            this.name = name;
            this.type = type;
        }

    }

    public enum ChangeLogType {
        added,
        removed,
        improved;
    }
}