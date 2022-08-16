package org.returnclient.altmanager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL20;
import org.returnclient.glslsandbox.GLSLSandboxShader;
import org.returnclient.mainmenu.MainMenu;
import org.returnclient.ui.UiButton;
import org.returnclient.ui.UiTextField;

import java.awt.*;
import java.io.IOException;
import java.net.Proxy;

import static org.lwjgl.opengl.GL11.*;

public class AltManager extends GuiScreen {

    private GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();

    private UiTextField username;
    private UiTextField password;

    private String status = null;

    private AltLoginThread thread;

    public AltManager() {
        try {
            this.backgroundShader = new GLSLSandboxShader("/noise.fsh");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load backgound shader", e);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.disableCull();

        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY, (System.currentTimeMillis() - initTime) / 1000f);

        glBegin(GL_QUADS);

        glVertex2f(-1f, -1f);
        glVertex2f(-1f, 1f);
        glVertex2f(1f, 1f);
        glVertex2f(1f, -1f);

        glEnd();

        GL20.glUseProgram(0);

        mc.fontRendererObj.drawStringWithShadow("Account Manager", width / 2 - mc.fontRendererObj.getStringWidth("Account Manager") / 2, 4.5, new Color(0x808080).hashCode());
        mc.fontRendererObj.drawStringWithShadow(this.thread == null ? ChatFormatting.GRAY + "Idle..." : this.thread.getStatus(), width / 2 - mc.fontRendererObj.getStringWidth(this.thread == null ? ChatFormatting.GRAY + "Idle..." : this.thread.getStatus()) / 2, 4.5 + mc.fontRendererObj.FONT_HEIGHT + 1.5, new Color(0x808080).hashCode());

        username.drawTextBox();
        password.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        username.textboxKeyTyped(typedChar, keyCode);
        password.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void initGui() {

        this.username = new UiTextField(0, mc.fontRendererObj, this.width / 2 - 200 / 2, 30, 200, 15);
        this.password = new UiTextField(1, mc.fontRendererObj, this.width / 2 - 200 / 2, 55, 200, 15);

        this.buttonList.add(new UiButton(0, this.width / 2 - 200 / 2, this.height / 2 - 50, 200, 15, "Login"));
        this.buttonList.add(new UiButton(1, this.width / 2 - 200 / 2, this.height / 2 - 25, 200, 15, "Back"));

        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0) {
            if(password.getText() != "") {

                this.thread = new AltLoginThread(username.getText(), password.getText());
                this.thread.start();

            } else {
                mc.session = new Session(username.getText(), "", "", "mojang");
            }
        }
        if(button.id == 1) {
            mc.displayGuiScreen(new MainMenu());
        }
        super.actionPerformed(button);
    }
}
