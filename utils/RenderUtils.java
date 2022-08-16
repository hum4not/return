package org.returnclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void setupColor(int color) {
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;

        GL11.glColor4f(f, f1, f2, f3);
    }

    public static void setupColor(int color, float alpha) {
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;

        GL11.glColor4f(f, f1, f2, alpha / 255.0f);
    }

    public static void drawFigure(Entity entity, double radius, double corners, int color) {

        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        startSmooth();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glLineWidth(4.0F);

        GL11.glBegin(GL11.GL_LINE_STRIP);

        for(int i = 0; i < corners + 1; ++i) {
            setupColor(new Color(0x000000).hashCode());

            GL11.glVertex3d(x + radius * Math.sin(i / corners * 2.0 * Math.PI), y, z + radius * Math.cos(i / corners * 2.0 * Math.PI));
        }

        GL11.glEnd();

        GL11.glLineWidth(3.0F);

        GL11.glBegin(GL11.GL_LINE_STRIP);

        for(int i = 0; i < corners + 1; ++i) {
            setupColor(color);

            GL11.glVertex3d(x + radius * Math.sin(i / corners * 2.0 * Math.PI), y, z + radius * Math.cos(i / corners * 2.0 * Math.PI));
        }

        GL11.glEnd();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        endSmooth();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    public static void startSmooth() {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT,GL11. GL_NICEST);
        GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void endSmooth() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
    }

    public static void drawTriangle(final double x, final double y, final double radius, final int color) {

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

        startSmooth();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        for (int i = 0; i < 3; i++) {

            setupColor(color);
            GL11.glVertex2d(x + (radius * Math.sin((i * 2 * Math.PI / 3))), y + (radius * Math.cos((i * 2 * Math.PI / 3))));
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        GL11.glEnd();

        endSmooth();

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(double xx, double yy, double radius, int color) {

        int sections = 20;
        double dAngle = 2 * Math.PI / sections;
        double x, y;

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glLineWidth(1.0f);

        GL11.glBegin(GL_TRIANGLE_FAN);

        for (int i = 0; i < sections; i++) {
            x = (float) (radius * Math.sin((i * dAngle)));
            y = (float) (radius * Math.cos((i * dAngle)));

            setupColor(color);

            GL11.glVertex2d(xx + x, yy + y);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        GL11.glEnd();

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawCircle(double xx, double yy, double radius, int color) {

        int sections = 20;
        double dAngle = 2 * Math.PI / sections;
        double x, y;

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

        startSmooth();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glBegin(GL_LINE_LOOP);

        for (int i = 0; i < sections; i++) {
            x = (float) (radius * Math.sin((i * dAngle)));
            y = (float) (radius * Math.cos((i * dAngle)));

            setupColor(color);

            GL11.glVertex2d(xx + x, yy + y);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        GL11.glEnd();

        endSmooth();

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawImage(ResourceLocation location, double posX, double posY, double width, double height) {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);

        float f = 1.0F / (float) width;
        float f1 = 1.0F / (float) height;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(posX, (posY + height), 0.0D).tex((double) (0 * f), (double) ((0 + (float) height) * f1)).endVertex();
        worldrenderer.pos((posX + width), (posY + height), 0.0D).tex((double) ((0 + (float) width) * f), (double) ((0 + (float) height) * f1)).endVertex();
        worldrenderer.pos((posX + width), posY, 0.0D).tex((double) ((0 + (float) width) * f), (double) (0 * f1)).endVertex();
        worldrenderer.pos(posX, posY, 0.0D).tex((double) (0 * f), (double) (0 * f1)).endVertex();

        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void drawRect(double x, double y, double width, double height, int color) {
        Gui.drawRect(x, y, width, height, color);
    }

    public static void drawGradientRect(double x, double y, double width, double height, int startColor, int endColor) {
        Gui.drawGradientRect(x, y, width, height, startColor, endColor);
    }

    public static void drawOutlinedBox(double x, double y, double z, double width, double height, int color) {

        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glDepthMask(false);

        GL11.glLineWidth(1.0f);

        setupColor(color);

        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

    }

    public static void drawBox(double x, double y, double z, double width, double height, int color){

        glPushMatrix();

        glDisable(GL_DEPTH_TEST);
        glDisable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);

        AxisAlignedBB aa = new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width);

        setupColor(color);

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);

        glEnd();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);

        glDisable(GL_BLEND);

        glPopMatrix();
    }

    public static void drawOutlinedBlockBox(BlockPos blockPos, int color) {

        double x = blockPos.getX() - mc.getRenderManager().renderPosX;
        double y = blockPos.getY() - mc.getRenderManager().renderPosY;
        double z = blockPos.getZ() - mc.getRenderManager().renderPosZ;

        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glLineWidth(0.5f);

        setupColor(color);

        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

    }

    public static void drawBlockBox(BlockPos blockPos, int color) {

        double x = blockPos.getX() - mc.getRenderManager().renderPosX;
        double y = blockPos.getY() - mc.getRenderManager().renderPosY;
        double z = blockPos.getZ() - mc.getRenderManager().renderPosZ;

        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glLineWidth(1.0f);

        AxisAlignedBB aa = new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D);

        setupColor(color);

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);

        glEnd();

        glBegin(GL_QUADS);

        glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        glVertex3d(aa.minX, aa.minY, aa.maxZ);
        glVertex3d(aa.minX, aa.maxY, aa.minZ);
        glVertex3d(aa.minX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        glVertex3d(aa.maxX, aa.minY, aa.minZ);
        glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        glVertex3d(aa.maxX, aa.minY, aa.maxZ);

        glEnd();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

    }

    public static void renderItem(ItemStack item, double x, double y) {
        GL11.glPushMatrix();

        if (item != null) {
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(item, (int)x, (int)y);
            mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, item, (int)x, (int)y);
            RenderHelper.disableStandardItemLighting();
        }

        GL11.glPopMatrix();
    }

    public static void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        final int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((scale.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
}
