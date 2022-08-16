package org.returnclient.module.impl.visual;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventRender3D;
import org.returnclient.module.Module;
import org.returnclient.utils.RenderUtils;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class ChestESP extends Module {

    public ChestESP() {
        super("Chest ESP", Category.visual);
    }

    @EventTarget
    public void onRender(EventRender3D e) {
        for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if(tileEntity instanceof TileEntityChest) {

                renderChestBox(((TileEntityChest)tileEntity).getPos());
            }
        }
    }

    public void renderChestBox(BlockPos blockPos) {

        double x = blockPos.getX() - mc.getRenderManager().viewerPosX + 0.5;
        double y = blockPos.getY() - mc.getRenderManager().viewerPosY + 0.5;
        double z = blockPos.getZ() - mc.getRenderManager().viewerPosZ + 0.5;

        glPushMatrix();

        glTranslated(x, y, z);

        glRotated(-mc.getRenderManager().playerViewY, 0, 1, 0);
        glRotated(mc.getRenderManager().playerViewX, 1, 0, 0);

        glDisable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);

        glLineWidth(3.0f);

        glBegin(GL_LINE_LOOP);

        RenderUtils.setupColor(new Color(0x000000).hashCode());

        glVertex2d(0.5, 0.5);
        glVertex2d(0.5, -0.5);
        glVertex2d(-0.5, -0.5);
        glVertex2d(-0.5, 0.5);

        glEnd();

        glLineWidth(1.5f);

        glBegin(GL_LINE_LOOP);

        RenderUtils.setupColor(new Color(0xFFFFFF).hashCode());

        glVertex2d(0.5, 0.5);
        glVertex2d(0.5, -0.5);
        glVertex2d(-0.5, -0.5);
        glVertex2d(-0.5, 0.5);

        glEnd();

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);

        glPopMatrix();
    }

}
