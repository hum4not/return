package org.returnclient.module.impl.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import javafx.animation.Interpolator;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.font.CFontRenderer;
import org.returnclient.font.CFonts;
import org.returnclient.module.Module;
import org.returnclient.options.OptionBoolean;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.ColorUtils;
import org.returnclient.utils.MoveUtils;
import org.returnclient.utils.RenderUtils;
import org.returnclient.utils.TimerUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;

public class Overlay extends Module {

    public static HashMap<Module, Float> mapX = new HashMap<Module, Float>();

    private TimerUtils timer = new TimerUtils();

    OptionMode colorMode;
    OptionValue red;
    OptionValue green;
    OptionValue blue;
    OptionValue alpha;
    OptionBoolean background;
    OptionBoolean upBar;
    OptionBoolean leftbar;
    OptionBoolean leftPosition;

    String[] modes = { "Gradient", "Orange", "Rainbow", "Static" };

    public Overlay() {
        super("Overlay", Category.visual);
        Return.getInstance().optionManager.addMode(colorMode = new OptionMode("Color Mode", this, "Gradient", modes));
        Return.getInstance().optionManager.addValue(red = new OptionValue("Red", this, 255, 0, 255));
        Return.getInstance().optionManager.addValue(green = new OptionValue("Green", this, 255, 0, 255));
        Return.getInstance().optionManager.addValue(blue = new OptionValue("Blue", this, 255, 0, 255));
        Return.getInstance().optionManager.addValue(alpha = new OptionValue("Alpha", this, 90, 0, 255));
        Return.getInstance().optionManager.addBoolean(background = new OptionBoolean("Background", this, true));
        Return.getInstance().optionManager.addBoolean(leftPosition = new OptionBoolean("Left Position", this, false));
        Return.getInstance().optionManager.addBoolean(upBar = new OptionBoolean("Up bar", this, true));
        Return.getInstance().optionManager.addBoolean(leftbar = new OptionBoolean("Left bar", this, false));

        setSuffix(colorMode.getMode());
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        setSuffix(colorMode.getMode());

        ScaledResolution scaledResolution = new ScaledResolution(mc);
        CFontRenderer font = CFonts.tahoma;

        Return.getInstance().moduleManager.modules.sort(Comparator.comparingInt(m -> font.getStringWidth(((Module) m).getSuffix() != null ? ((Module) m).getName() + " " + ((Module) m).getSuffix() : ((Module) m).getName())).reversed());

        int color = -1;

        if(colorMode.is("Orange")) {

            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (font.getHeight() + 50D));
            color = ColorUtils.getGradientOffset(Color.ORANGE, Color.MAGENTA, colorOffset, 255).hashCode();

        }else if(colorMode.is("Gradient")) {

            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (font.getHeight() + 50D));
            color = ColorUtils.getGradientOffset(new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255), Color.BLACK, colorOffset, 255).hashCode();

        }else if(colorMode.is("Rainbow")) {

            color = ColorUtils.getRainbow(300);

        } else if(colorMode.is("Static")) {
            color = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255).hashCode();
        }

        double height = 14;

        /*

        RenderUtils.drawRect(2.5 - 1.0, 2.5 - 1.0, 2.5 - 1.0 + 1.0, 2.5 + height + 1.0, new Color(0x252525).hashCode());
        RenderUtils.drawRect(2.5 + width, 2.5 - 1.0, 2.5 + width + 1.0, 2.5 + height + 1.0, new Color(0x252525).hashCode());
        RenderUtils.drawRect(2.5 - 1.0, 2.5 - 1.0, 2.5 + width + 1.0, 2.5 - 1.0 + 1.0, new Color(0x252525).hashCode());
        RenderUtils.drawRect(2.5 - 1.0, 2.5 + height, 2.5 + width + 1.0, 2.5 + height + 1.0, new Color(0x252525).hashCode());

        RenderUtils.drawRect(2.5, 2.5, 2.5 + width, 2.5 + height, new Color(0xAB6B6B6B, true).hashCode());

        mc.fontRendererObj.drawStringWithShadow("Virtue", 2.5 + width / 2 - mc.fontRendererObj.getStringWidth("Virtue") / 2, 4.5, new Color(0xC5C5C5).hashCode());

        mc.fontRendererObj.drawStringWithShadow(new SimpleDateFormat("HH:mm a").format(Calendar.getInstance().getTime()), 2.5 + width / 2 - mc.fontRendererObj.getStringWidth(new SimpleDateFormat("HH:mm a").format(Calendar.getInstance().getTime())) / 2, 13.5, new Color(0x909090).hashCode());

        mc.fontRendererObj.drawStringWithShadow("Fps: " + mc.getDebugFPS(), 2.5 + width / 2 - mc.fontRendererObj.getStringWidth("Fps: " + mc.getDebugFPS()) / 2, 22.5, new Color(0x909090).hashCode());

        String currentName = "Return" + " | " + mc.thePlayer.getName() + " | " + mc.getDebugFPS() + "fps" + " | " + (mc.isSingleplayer() ? "local" : mc.getCurrentServerData().pingToServer + "ms");

        RenderUtils.drawRect(3.5 - 2.0, 4.5 - 3.0, 3.5 + font.getStringWidth(currentName) + 3.5 + 2.0, 4.5 + font.getHeight() + 4.0 + 2.0, new Color(0x303030).hashCode());
        RenderUtils.drawRect(3.5 - 1.5, 4.5 - 2.5, 3.5 + font.getStringWidth(currentName) + 3.5 + 1.5, 4.5 + font.getHeight() + 4.0 + 1.5, new Color(0x202020).hashCode());
        RenderUtils.drawRect(3.5 - 1.0, 4.5 - 2.0, 3.5 + font.getStringWidth(currentName) + 3.5 + 1.0, 4.5 + font.getHeight() + 4.0 + 1.0, new Color(0x303030).hashCode());
        RenderUtils.drawRect(3.5 - 0.5, 4.5 - 1.5, 3.5 + font.getStringWidth(currentName) + 3.5 + 0.5, 4.5 + font.getHeight() + 4.0 + 0.5, new Color(0x202020).hashCode());

        RenderUtils.drawRect(3.5, 4.5 - 1.0, 3.5 + font.getStringWidth(currentName) + 3.5, 4.5 + font.getHeight() + 4.0, color);

        RenderUtils.drawRect(3.5, 4.5, 3.5 + font.getStringWidth(currentName) + 3.5, 4.5 + font.getHeight() + 4.0, new Color(0x151515).hashCode());

        font.drawStringWithShadow(currentName, 4.5, 7.0, -1);

         */

        String currentName = ChatFormatting.DARK_RED + "R" + ChatFormatting.WHITE + "eturn " + ChatFormatting.GRAY +  "[" + ChatFormatting.WHITE + "1.8.x" + ChatFormatting.GRAY + "] " +  "[" + ChatFormatting.WHITE + mc.getDebugFPS() + " FPS" + ChatFormatting.GRAY + "]";

        mc.fontRendererObj.drawStringWithShadow(currentName, 1.5, 1.5, -1);

        String blocks = String.format("%.2f", MoveUtils.getSpeed());

        mc.fontRendererObj.drawStringWithShadow(ChatFormatting.GRAY + "b/s " + ChatFormatting.WHITE + blocks, scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(ChatFormatting.GRAY + "b/s " + ChatFormatting.WHITE + blocks) / 2, 12.5, -1);

        String release = ChatFormatting.GRAY + "Developer Build" + " - " + ChatFormatting.WHITE + Return.getInstance().getBuild();

        font.drawStringWithShadow(release, scaledResolution.getScaledWidth() - font.getStringWidth(release) - 1.0, scaledResolution.getScaledHeight() - (mc.currentScreen instanceof GuiChat ? 22.5 : 10), -1);

        int count = 0;

        for (Module module : Return.getInstance().moduleManager.modules) {
            if (!module.isToggled() || module.getCategory().equals(Category.visual))
                continue;

            if (!module.isHidden()) {

                if (mapX.containsKey(module)) {
                    mapX.entrySet().forEach(m -> m.setValue((float) Interpolator.EASE_OUT.interpolate((float) m.getValue(), 0f, 0.01)));

                    module.setX(mapX.get(module));
                }

                if (colorMode.is("Orange")) {

                    double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (count * font.getHeight() + 50D));
                    color = ColorUtils.getGradientOffset(Color.ORANGE, Color.MAGENTA, colorOffset, 255).hashCode();

                } else if (colorMode.is("Gradient")) {

                    double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (count * font.getHeight() + 50D));
                    color = ColorUtils.getGradientOffset(new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255), Color.BLACK, colorOffset, 255).hashCode();

                } else if (colorMode.is("Rainbow")) {

                    color = ColorUtils.getRainbow(count * 300);

                } else if (colorMode.is("Static")) {
                    color = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255).hashCode();
                }

                int backcolor = new Color(0, 0, 0, (int) alpha.getValue()).hashCode();

                String name = null;

                if (module.getSuffix() != null) {
                    name = module.getName() + ChatFormatting.GRAY + " " + module.getSuffix();

                } else {
                    name = module.getName();
                }

                if (leftPosition.isToggled()) {
                    if (background.isToggled()) {
                        RenderUtils.drawRect(0, (2.5 + height + 2.5) + count * (font.getHeight() + 2.5) + 2.0, font.getStringWidth(name) + 4.0 - module.getX(), 2.5 + font.getHeight() + (2.5 + height + 2.5) + count * (font.getHeight() + 2.5) + 2.0, backcolor);
                    }

                    if(upBar.isToggled()) {

                        int upColor = -1;

                        if (colorMode.is("Orange")) {

                            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (font.getHeight() + 50D));
                            upColor = ColorUtils.getGradientOffset(Color.ORANGE, Color.MAGENTA, colorOffset, 255).hashCode();

                        } else if (colorMode.is("Gradient")) {

                            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (font.getHeight() + 50D));
                            upColor = ColorUtils.getGradientOffset(new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255), Color.BLACK, colorOffset, 255).hashCode();

                        } else if (colorMode.is("Rainbow")) {

                            upColor = ColorUtils.getRainbow(300);

                        } else if (colorMode.is("Static")) {
                            upColor = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255).hashCode();
                        }

                        RenderUtils.drawRect(0, (2.5 + height + 2.5) + 2.0, font.getStringWidth(name) + 4.0 - module.getX(), (2.5 + height + 2.5) + 2.0 + 1.0,  upColor);
                    }

                    if (leftbar.toggled) {
                        RenderUtils.drawRect(0, (2.5 + height + 2.5) + count * (font.getHeight() + 2.5) + 2.0, 1.0, 2.5 + font.getHeight() + (2.5 + height + 2.5) + count * (font.getHeight() + 2.5) + 2.0, color);
                    }

                    font.drawStringWithShadow(name, ((upBar.isToggled() && !leftbar.isToggled()) ? 1.0 : 2.5) - module.getX(), (2.5 + height + 2.5) + 2.0 + count * (font.getHeight() + 2.5) + 2.0, color);

                } else {

                    if (background.isToggled()) {
                        RenderUtils.drawRect(scaledResolution.getScaledWidth() - font.getStringWidth(name) - ((upBar.isToggled() && !leftbar.isToggled()) ? 3.0 : 4.0) + module.getX(), count * (font.getHeight() + 2.5) + 2.0, scaledResolution.getScaledWidth(), 2.5 + font.getHeight() + count * (font.getHeight() + 2.5) + 2.0, backcolor);
                    }

                    if(upBar.isToggled()) {

                        int upColor = -1;

                        if (colorMode.is("Orange")) {

                            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (font.getHeight() + 50D));
                            upColor = ColorUtils.getGradientOffset(Color.ORANGE, Color.MAGENTA, colorOffset, 255).hashCode();

                        } else if (colorMode.is("Gradient")) {

                            double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (50 / (font.getHeight() + 50D));
                            upColor = ColorUtils.getGradientOffset(new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255), Color.BLACK, colorOffset, 255).hashCode();

                        } else if (colorMode.is("Rainbow")) {

                            upColor = ColorUtils.getRainbow(300);

                        } else if (colorMode.is("Static")) {
                            upColor = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue(), 255).hashCode();
                        }

                        RenderUtils.drawRect(scaledResolution.getScaledWidth() - font.getStringWidth(name) - ((upBar.isToggled() && !leftbar.isToggled()) ? 3.0 : 4.0) + module.getX(), 1.0, scaledResolution.getScaledWidth(), 1.0 + 1.0, upColor);
                    }

                    if (leftbar.toggled) {
                        RenderUtils.drawRect(scaledResolution.getScaledWidth() - 1.0 + module.getX(), count * (font.getHeight()) + 2.0, scaledResolution.getScaledWidth(), 2.5 + font.getHeight() + count * (font.getHeight() + 2.5) + 2.0, color);
                    }

                    font.drawStringWithShadow(name, scaledResolution.getScaledWidth() - font.getStringWidth(name) - ((upBar.isToggled() && !leftbar.isToggled()) ? 1.0 : 2.0) + module.getX(), 1.0 + count * (font.getHeight() + 2.5) + 2.0, color);
                }

                count++;
            }
        }
    }
}
