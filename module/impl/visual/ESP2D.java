package org.returnclient.module.impl.visual;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.event.impl.EventRender3D;
import org.returnclient.module.Module;
import org.returnclient.utils.HealthUtils;
import org.returnclient.utils.MathUtils;
import org.returnclient.utils.RenderUtils;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class ESP2D extends Module {

    FloatBuffer screen_coords = GLAllocation.createDirectFloatBuffer(4);
    IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);

    public ESP2D() {
        super("2D ESP", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender2D e) {

        ScaledResolution scaledResolution = e.getScaledResolution();

        glPushMatrix();

        double scale = scaledResolution.getScaleFactor() / 2.0f;

        glScaled(scale, scale, scale);

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if(mc.thePlayer != player) {

                double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
                double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
                double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

                double width = player.width / 1.5;
                double height = (player.isSneaking() ? player.height - 0.15 : player.height);

                mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);

                AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);

                Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};

                Vector4d position = null;

                for (Vector3d vector : vectors) {
                    vector = gluProject2D(vector.x, vector.y, vector.z, scaledResolution.getScaleFactor());

                    if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
                        if (position == null)
                            position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);

                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);

                    }
                }

                if (position != null) {

                    mc.entityRenderer.setupOverlayRendering();

                    double posX = position.x;
                    double posY = position.y;

                    double endPosX = position.z;
                    double endPosY = position.w;

                    glEnable(GL_BLEND);
                    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                    RenderUtils.drawRect(posX - 1.0d, posY, posX + 0.5d, endPosY + 0.5d, new Color(0x000000).hashCode());
                    RenderUtils.drawRect(posX - 1.0d, posY - 0.5d, endPosX + 0.5d, posY + 0.5d + 0.5d, new Color(0x000000).hashCode());
                    RenderUtils.drawRect(endPosX - 0.5d - 0.5d, posY, endPosX + 0.5d, endPosY + 0.5d, new Color(0x000000).hashCode());
                    RenderUtils.drawRect(posX - 1.0d, endPosY - 0.5d - 0.5d, endPosX + 0.5d, endPosY + 0.5d, new Color(0x000000).hashCode());

                    RenderUtils.drawRect(posX - 0.5d, posY, posX + 0.5d - 0.5d, endPosY, new Color(0xFFFFFF).hashCode());
                    RenderUtils.drawRect(posX, endPosY - 0.5d, endPosX, endPosY, new Color(0xFFFFFF).hashCode());
                    RenderUtils.drawRect(posX - 0.5d, posY, endPosX, posY + 0.5d, new Color(0xFFFFFF).hashCode());
                    RenderUtils.drawRect(endPosX - 0.5d, posY, endPosX, endPosY, new Color(0xFFFFFF).hashCode());

                    RenderUtils.drawRect(posX - 3.5d, posY - 0.5d, posX - 1.5d, endPosY + 0.5d, new Color(0x000000).hashCode());
                    RenderUtils.drawRect(posX - 3.0d, posY, posX - 2.0d, endPosY, HealthUtils.getHealthColor(player.getHealth()));

                    glDisable(GL_BLEND);

                }
            }
        }

        glPopMatrix();

        mc.entityRenderer.setupOverlayRendering();
    }

    public Vector3d gluProject2D(double x, double y, double z, double scaleFactor) {

        glGetFloat(2982, modelview);
        glGetFloat(2983, projection);
        glGetInteger(2978, viewport);

        if(GLU.gluProject((float) x, (float) y, (float) z, modelview, projection, viewport, screen_coords))
            return new Vector3d((screen_coords.get(0) / scaleFactor), ((Display.getHeight() - screen_coords.get(1)) / scaleFactor), screen_coords.get(2));
        return null;
    }
}
