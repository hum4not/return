package org.returnclient.font;

import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class CFonts {

    public static CFontRenderer tahoma = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/tahoma.ttf"), 16.0f, 0), true, true);
    public static CFontRenderer verdana = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/verdana.ttf"), 12.0f, 0), true, true);
    public static CFontRenderer badcache = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/badcache.ttf"), 35.0f, 0), true, true);
    public static CFontRenderer icons = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/icons.ttf"), 55.0f, 0), true, true);

    public static CFontRenderer tahoma_bigger = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/tahoma.ttf"), 18.0f, 0), true, true);
    public static CFontRenderer tahoma_small = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/tahoma.ttf"), 14.0f, 0), true, true);
    public static CFontRenderer tahoma_big = new CFontRenderer(FontUtil.getFontFromTTF(new ResourceLocation("fonts/tahoma.ttf"), 25.0f, 0), true, true);

}
