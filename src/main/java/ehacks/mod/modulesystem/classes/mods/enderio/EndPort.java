/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes.mods.enderio;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosX;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosY;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author radioegor146
 */
public class EndPort extends Module {

    private Vec3 distVec = null;
    private Entity selectedEnt = null;

    public EndPort() {
        super(ModuleCategory.EHACKS);
    }

    private double size = 0;

    @Override
    public String getName() {
        return "EndPort";
    }

    @Override
    public String getDescription() {
        return "Allows to teleport entity";
    }

    @Override
    public void onMouse(MouseEvent event) {
        if (event.button == 1 && event.buttonstate) {

            if (selectedEnt != null) {
                Wrapper.INSTANCE.world().removeEntityFromWorld(-2);
                selectedEnt = null;
                distVec = null;
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
                return;
            }
            selectedEnt = getMouseOver(1f, 500, false);
            if (!(selectedEnt instanceof EntityFakePlayer)) {
                if (selectedEnt != null) {
                    distVec = Wrapper.INSTANCE.mc().renderViewEntity.getLookVec().normalize();
                    size = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(1f).distanceTo(Vec3.createVectorHelper(selectedEnt.posX, selectedEnt.posY, selectedEnt.posZ));
                    distVec.xCoord *= size;
                    distVec.yCoord *= size;
                    distVec.zCoord *= size;
                    if (event.isCancelable()) {
                        event.setCanceled(true);
                    }
                } else {
                    distVec = null;
                }
            }
        }
        if (selectedEnt != null) {
            if (event.dwheel > 0) {
                size = Math.min(size + event.dwheel / 120f, 200f);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            } else if (event.dwheel < 0) {
                size = Math.max(size + event.dwheel / 120f, 1f);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
        if (event.button == 0) {
            if (selectedEnt != null) {
                tpEntity(selectedEnt, (int) Math.round(Wrapper.INSTANCE.mc().renderViewEntity.posX + distVec.xCoord), (int) Math.round(Wrapper.INSTANCE.mc().renderViewEntity.posY + distVec.yCoord - ((selectedEnt instanceof EntityPlayer) ? 2 : 0)), (int) Math.round(Wrapper.INSTANCE.mc().renderViewEntity.posZ + distVec.zCoord));
                selectedEnt = null;
                distVec = null;
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private Entity getMouseOver(float partialTicks, double distance, boolean canBeCollidedWith) {
        Minecraft mc = Wrapper.INSTANCE.mc();
        Entity pointedEntity = null;
        MovingObjectPosition rayTrace = null;

        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                Vec3 positionVec = mc.renderViewEntity.getPosition(partialTicks);
                Vec3 lookVec = mc.renderViewEntity.getLook(partialTicks);
                Vec3 posDistVec = positionVec.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
                double boxExpand = 1.0F;
                List<Entity> entities = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance).expand(boxExpand, boxExpand, boxExpand));
                double mincalc = Double.MAX_VALUE;
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = entities.get(i);
                    if (!canBeCollidedWith || entity.canBeCollidedWith()) {
                        double borderSize = entity.getCollisionBorderSize();
                        AxisAlignedBB expEntityBox = entity.boundingBox.expand(borderSize, borderSize, borderSize);
                        MovingObjectPosition calculateInterceptPos = expEntityBox.calculateIntercept(positionVec, posDistVec);
                        if (calculateInterceptPos != null) {
                            double calcInterceptPosDist = positionVec.distanceTo(calculateInterceptPos.hitVec);
                            if (mincalc > calcInterceptPosDist) {
                                mincalc = calcInterceptPosDist;
                                pointedEntity = entity;
                            }
                        }
                    }
                }
                if (pointedEntity != null) {
                    return pointedEntity;
                }
            }
        }

        return null;
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (selectedEnt == null) {
            return;
        }
        if (distVec != null) {
            distVec = Wrapper.INSTANCE.mc().renderViewEntity.getLookVec().normalize();
            distVec.xCoord *= size;
            distVec.yCoord *= size;
            distVec.zCoord *= size;
        }
        NBTTagCompound tag = new NBTTagCompound();
        selectedEnt.writeToNBT(tag);

        if (!GLUtils.hasClearedDepth) {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GLUtils.hasClearedDepth = true;
        }
        Entity ent = selectedEnt;

        double tXPos = ent.posX;
        double tYPos = ent.posY;
        double tZPos = ent.posZ;

        double xPos = (Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosX + distVec.xCoord) + (Wrapper.INSTANCE.mc().renderViewEntity.posX - Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosX) * event.partialTicks;
        double yPos = (Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosY + distVec.yCoord + ((ent instanceof EntityPlayer) ? 1 : 0)) + (Wrapper.INSTANCE.mc().renderViewEntity.posY - Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosY) * event.partialTicks;
        double zPos = (Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosZ + distVec.zCoord) + (Wrapper.INSTANCE.mc().renderViewEntity.posZ - Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosZ) * event.partialTicks;

        ent.posX = ent.lastTickPosX = xPos;
        ent.posY = ent.lastTickPosY = yPos;
        ent.posZ = ent.lastTickPosZ = zPos;

        float f1 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * event.partialTicks;
        RenderHelper.enableStandardItemLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0F, 240 / 1.0F);
        if (Wrapper.INSTANCE.world().loadedEntityList.contains(selectedEnt)) {
            GL11.glColor4f(0, 1.0F, 0, 1f);
        } else {
            GL11.glColor4f(1.0F, 0, 0, 1f);
        }
        RenderManager.instance.func_147939_a(ent, xPos - renderPosX, yPos - renderPosY, zPos - renderPosZ, f1, event.partialTicks, false);

        ent.posX = ent.lastTickPosX = tXPos;
        ent.posY = ent.lastTickPosY = tYPos;
        ent.posZ = ent.lastTickPosZ = tZPos;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void onTicks() {

    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("crazypants.enderio.network.PacketHandler").getField("INSTANCE");
            Class.forName("crazypants.enderio.api.teleport.TravelSource").getField("BLOCK");
            Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getConstructor(Entity.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Class.forName("crazypants.enderio.api.teleport.TravelSource"));
            Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getDeclaredField("entityId");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    public void tpEntity(Entity ent, int x, int y, int z) {
        try {
            SimpleNetworkWrapper snw = (SimpleNetworkWrapper) Class.forName("crazypants.enderio.network.PacketHandler").getField("INSTANCE").get(null);
            Object travelSource = Class.forName("crazypants.enderio.api.teleport.TravelSource").getField("BLOCK").get(null);
            Object packet = Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getConstructor(Entity.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Class.forName("crazypants.enderio.api.teleport.TravelSource")).newInstance(ent, x, y, z, 0, false, travelSource);
            Field f = Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getDeclaredField("entityId");
            f.setAccessible(true);
            f.set(packet, ent.getEntityId());
            snw.sendToServer((IMessage) packet);
        } catch (Exception e) {
        }
    }

    @Override
    public String getModName() {
        return "EnderIO";
    }
}
