package org.returnclient.module.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventMotion;
import org.returnclient.event.impl.EventPacket;
import org.returnclient.event.impl.EventRender2D;
import org.returnclient.event.impl.EventRender3D;
import org.returnclient.module.Module;
import org.returnclient.notifications.Notification;
import org.returnclient.options.OptionBoolean;
import org.returnclient.options.OptionMode;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.*;

import java.awt.*;

public class Scaffold extends Module {

    TimerUtils boostTimer = new TimerUtils();
    TimerUtils verusTimer = new TimerUtils();

    TimerUtils watchdogTimer = new TimerUtils();
    TimerUtils packetTimer = new TimerUtils();

    BlockData data;

    OptionMode mode;

    OptionValue aps;
    OptionValue timerBoostValue;

    OptionBoolean timerBoost;
    OptionBoolean towerMove;
    OptionBoolean tower;
    OptionBoolean swing;

    String[] modes = { "Watchdog", "Normal" };

    public Scaffold() {
        super("Scaffold", Category.movement);
        Return.getInstance().optionManager.addMode(mode = new OptionMode("Mode", this, "Watchdog", modes));
        Return.getInstance().optionManager.addValue(aps = new OptionValue("APS", this, 17.5, 12.5, 22.5));
        Return.getInstance().optionManager.addValue(timerBoostValue = new OptionValue("Timer Boost", this, 1.2, 1.0, 2.0));
        Return.getInstance().optionManager.addBoolean(timerBoost = new OptionBoolean("Timer Boost", this, true));
        Return.getInstance().optionManager.addBoolean(towerMove = new OptionBoolean("Tower Move", this, true));
        Return.getInstance().optionManager.addBoolean(tower = new OptionBoolean("Tower", this, true));
        Return.getInstance().optionManager.addBoolean(swing = new OptionBoolean("Swing", this, true));
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @EventTarget
    public void onPacket(EventPacket e) {
        if(mode.is("Watchdog")) {
            if(packetTimer.hasTimeElapsed(250L, true)) {
                if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                    e.setCancelled(false);
                }
            } else {
                if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onMotion(EventMotion e) {

        BlockPos position = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);

        data = getBlockData(position);

        if(data != null) {
            float[] rotation = BlockUtils.getRotationToBlock(data.position);

            if(data.face == EnumFacing.UP) {
                mc.thePlayer.rotationPitchHead = 90.0f;
                e.setPitch(90.0f);
            }

            e.setYaw(rotation[0]);
            e.setPitch(rotation[1]);

            mc.thePlayer.rotationYawHead = rotation[0];
            mc.thePlayer.renderYawOffset = rotation[0];
            mc.thePlayer.rotationPitchHead = rotation[1];
        }

        if(mode.is("Normal")) {
            if (mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir && data != null) {

                if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {

                    if (timerBoost.isToggled()) {
                        if (boostTimer.hasTimeElapsed(1000 / (long) aps.getValue(), true)) {
                            mc.timer.timerSpeed = (float) timerBoostValue.getValue();
                        }
                    }

                    if (tower.isToggled()) {

                        if (mc.thePlayer.movementInput.jump) {

                            if (towerMove.isToggled()) {

                                mc.thePlayer.motionX = 0.0;
                                mc.thePlayer.motionZ = 0.0;
                            }
                        }
                    } else {
                        if (data.face == EnumFacing.UP) {
                            return;
                        }
                    }

                    if (verusTimer.hasTimeElapsed(1000 / (long) aps.getValue(), true)) {

                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.position, data.face, new Vec3(data.position.getX(), data.position.getY(), data.position.getZ()))) {
                            if (swing.isToggled()) {
                                mc.thePlayer.swingItem();
                            } else {
                                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            }
                            data = null;
                        }
                    }
                }
            }
        } else if(mode.is("Watchdog")) {

            if (mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir && data != null) {

                if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {

                    mc.thePlayer.setSprinting(false);

                    if (timerBoost.isToggled()) {
                        if (boostTimer.hasTimeElapsed(1000 / (long) aps.getValue(), true)) {
                            mc.timer.timerSpeed = (float) timerBoostValue.getValue();
                        }
                    }

                    if (tower.isToggled()) {

                        if (mc.thePlayer.movementInput.jump) {

                            if (towerMove.isToggled()) {

                                mc.thePlayer.motionX = 0.0;
                                mc.thePlayer.motionZ = 0.0;
                            }
                        }
                    } else {
                        if (data.face == EnumFacing.UP) {
                            return;
                        }
                    }

                    if (watchdogTimer.hasTimeElapsed(1000 / (long) aps.getValue(), true)) {

                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.position, data.face, new Vec3(data.position.getX(), data.position.getY(), data.position.getZ()))) {
                            if (swing.isToggled()) {
                                mc.thePlayer.swingItem();
                            } else {
                                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            }
                            data = null;
                        }
                    }
                }
            }
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {

        ScaledResolution scaledResolution = e.getScaledResolution();

        float stackSize = 0;

        for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
            if(stack != null && stack.getItem() instanceof ItemBlock) {
                stackSize += stack.stackSize;
            }
        }

        int color = -1;

        if(stackSize > 32) {
            color = new Color(0x39A939).hashCode();
        } else if(stackSize > 16) {
            color = new Color(0xBBBB55).hashCode();
        } else if(stackSize > 0) {
            color = new Color(0xB93939).hashCode();
        }

        if(stackSize > 0) {

            mc.fontRendererObj.drawString(String.format("%.0f", stackSize), scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(String.format("%.0f", stackSize)) / 2 + 1, scaledResolution.getScaledHeight() / 2 - 14.5, 1);
            mc.fontRendererObj.drawString(String.format("%.0f", stackSize), scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(String.format("%.0f", stackSize)) / 2 - 1, scaledResolution.getScaledHeight() / 2 - 14.5, 1);
            mc.fontRendererObj.drawString(String.format("%.0f", stackSize), scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(String.format("%.0f", stackSize)) / 2, scaledResolution.getScaledHeight() / 2 - 14.5 + 1, 1);
            mc.fontRendererObj.drawString(String.format("%.0f", stackSize), scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(String.format("%.0f", stackSize)) / 2, scaledResolution.getScaledHeight() / 2 - 14.5 - 1, 1);
            mc.fontRendererObj.drawString(String.format("%.0f", stackSize), scaledResolution.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(String.format("%.0f", stackSize)) / 2, scaledResolution.getScaledHeight() / 2 - 14.5, color);
        }
    }

    public BlockData getBlockData(BlockPos position) {

        if(mc.theWorld.getBlockState(position.add(0, -1, 0)).getBlock() != Blocks.air) {
            BlockData data = new BlockData(position.add(0, -1, 0), EnumFacing.UP);
            return data;

        } else if(mc.theWorld.getBlockState(position.add(0, 0, 1)).getBlock() != Blocks.air) {
            BlockData data1 = new BlockData(position.add(0, 0, 1), EnumFacing.NORTH);
            return data1;

        } else if(mc.theWorld.getBlockState(position.add(-1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data2 = new BlockData(position.add(-1, 0, 0), EnumFacing.EAST);
            return data2;

        } else if(mc.theWorld.getBlockState(position.add(0, 0, -1)).getBlock() != Blocks.air) {
            BlockData data3 = new BlockData(position.add(0, 0, -1), EnumFacing.SOUTH);
            return data3;

        } else if(mc.theWorld.getBlockState(position.add(1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data4 = new BlockData(position.add(1, 0, 0), EnumFacing.WEST);
            return data4;
        }

        BlockPos position1 = position.add(0, -1, 0);

        if(mc.theWorld.getBlockState(position1.add(0, -1, 0)).getBlock() != Blocks.air) {
            BlockData data = new BlockData(position1.add(0, -1, 0), EnumFacing.UP);
            return data;

        } else if(mc.theWorld.getBlockState(position1.add(0, 0, 1)).getBlock() != Blocks.air) {
            BlockData data1 = new BlockData(position1.add(0, 0, 1), EnumFacing.NORTH);
            return data1;

        } else if(mc.theWorld.getBlockState(position1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data2 = new BlockData(position1.add(-1, 0, 0), EnumFacing.EAST);
            return data2;

        } else if(mc.theWorld.getBlockState(position1.add(0, 0, -1)).getBlock() != Blocks.air) {
            BlockData data3 = new BlockData(position1.add(0, 0, -1), EnumFacing.SOUTH);
            return data3;

        } else if(mc.theWorld.getBlockState(position1.add(1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data4 = new BlockData(position1.add(1, 0, 0), EnumFacing.WEST);
            return data4;
        }

        BlockPos position2 = position.add(0, 0, 1);

        if(mc.theWorld.getBlockState(position2.add(0, -1, 0)).getBlock() != Blocks.air) {
            BlockData data = new BlockData(position2.add(0, -1, 0), EnumFacing.UP);
            return data;

        } else if(mc.theWorld.getBlockState(position2.add(0, 0, 1)).getBlock() != Blocks.air) {
            BlockData data1 = new BlockData(position2.add(0, 0, 1), EnumFacing.NORTH);
            return data1;

        } else if(mc.theWorld.getBlockState(position2.add(-1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data2 = new BlockData(position2.add(-1, 0, 0), EnumFacing.EAST);
            return data2;

        } else if(mc.theWorld.getBlockState(position2.add(0, 0, -1)).getBlock() != Blocks.air) {
            BlockData data3 = new BlockData(position2.add(0, 0, -1), EnumFacing.SOUTH);
            return data3;

        } else if(mc.theWorld.getBlockState(position2.add(1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data4 = new BlockData(position2.add(1, 0, 0), EnumFacing.WEST);
            return data4;
        }

        BlockPos position3 = position.add(-1, 0, 0);

        if(mc.theWorld.getBlockState(position3.add(0, -1, 0)).getBlock() != Blocks.air) {
            BlockData data = new BlockData(position3.add(0, -1, 0), EnumFacing.UP);
            return data;

        } else if(mc.theWorld.getBlockState(position3.add(0, 0, 1)).getBlock() != Blocks.air) {
            BlockData data1 = new BlockData(position3.add(0, 0, 1), EnumFacing.NORTH);
            return data1;

        } else if(mc.theWorld.getBlockState(position3.add(-1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data2 = new BlockData(position3.add(-1, 0, 0), EnumFacing.EAST);
            return data2;

        } else if(mc.theWorld.getBlockState(position3.add(0, 0, -1)).getBlock() != Blocks.air) {
            BlockData data3 = new BlockData(position3.add(0, 0, -1), EnumFacing.SOUTH);
            return data3;

        } else if(mc.theWorld.getBlockState(position3.add(1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data4 = new BlockData(position3.add(1, 0, 0), EnumFacing.WEST);
            return data4;
        }

        BlockPos position4 = position.add(0, 0, -1);

        if(mc.theWorld.getBlockState(position4.add(0, -1, 0)).getBlock() != Blocks.air) {
            BlockData data = new BlockData(position4.add(0, -1, 0), EnumFacing.UP);
            return data;

        } else if(mc.theWorld.getBlockState(position4.add(0, 0, 1)).getBlock() != Blocks.air) {
            BlockData data1 = new BlockData(position4.add(0, 0, 1), EnumFacing.NORTH);
            return data1;

        } else if(mc.theWorld.getBlockState(position4.add(-1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data2 = new BlockData(position4.add(-1, 0, 0), EnumFacing.EAST);
            return data2;

        } else if(mc.theWorld.getBlockState(position4.add(0, 0, -1)).getBlock() != Blocks.air) {
            BlockData data3 = new BlockData(position4.add(0, 0, -1), EnumFacing.SOUTH);
            return data3;

        } else if(mc.theWorld.getBlockState(position4.add(1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data4 = new BlockData(position4.add(1, 0, 0), EnumFacing.WEST);
            return data4;
        }

        BlockPos position5 = position.add(1, 0, 0);

        if(mc.theWorld.getBlockState(position5.add(0, -1, 0)).getBlock() != Blocks.air) {
            BlockData data = new BlockData(position5.add(0, -1, 0), EnumFacing.UP);
            return data;

        } else if(mc.theWorld.getBlockState(position5.add(0, 0, 1)).getBlock() != Blocks.air) {
            BlockData data1 = new BlockData(position5.add(0, 0, 1), EnumFacing.NORTH);
            return data1;

        } else if(mc.theWorld.getBlockState(position5.add(-1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data2 = new BlockData(position5.add(-1, 0, 0), EnumFacing.EAST);
            return data2;

        } else if(mc.theWorld.getBlockState(position5.add(0, 0, -1)).getBlock() != Blocks.air) {
            BlockData data3 = new BlockData(position5.add(0, 0, -1), EnumFacing.SOUTH);
            return data3;

        } else if(mc.theWorld.getBlockState(position5.add(1, 0, 0)).getBlock() != Blocks.air) {
            BlockData data4 = new BlockData(position5.add(1, 0, 0), EnumFacing.WEST);
            return data4;
        }

        return null;
    }

    public class BlockData {

        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

}
