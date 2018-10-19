package ehacks.mod.modulesystem.classes.mods.enderio;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler.Side;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosX;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosY;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author radioegor146
 */
public class SelfEnd extends Module {

    private Vec3 distVec = null;
    private Entity selectedEnt = null;

    public SelfEnd() {
        super(ModuleCategory.EHACKS);
    }

    private double size = 0;

    @Override
    public String getName() {
        return "SelfEnd";
    }

    @Override
    public String getDescription() {
        return "Allows to teleport yourself";
    }

    @Override
    public void onMouse(MouseEvent event) {
        if (event.button == 2 && event.buttonstate) {

            if (selectedEnt != null) {
                Wrapper.INSTANCE.world().removeEntityFromWorld(-2);
                selectedEnt = null;
                distVec = null;
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
                return;
            }
            selectedEnt = Wrapper.INSTANCE.player();
            if (!(selectedEnt instanceof EntityFakePlayer)) {
                if (selectedEnt != null) {
                    distVec = Wrapper.INSTANCE.mc().renderViewEntity.getLookVec().normalize();
                    size = 3f;
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
        if (distVec != null) {
            distVec = Wrapper.INSTANCE.mc().renderViewEntity.getLookVec().normalize();
            distVec.xCoord *= size;
            distVec.yCoord *= size;
            distVec.zCoord *= size;
        }
        if (event.button == 0) {
            if (selectedEnt != null) {
                tpEntity(selectedEnt, (int) Math.round(Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosX + distVec.xCoord), (int) Math.round(Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosY + distVec.yCoord - 2), (int) Math.round(Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosZ + distVec.zCoord));
                selectedEnt = null;
                distVec = null;
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
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
        double yPos = (Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosY + distVec.yCoord + 1) + (Wrapper.INSTANCE.mc().renderViewEntity.posY - Wrapper.INSTANCE.mc().renderViewEntity.lastTickPosY) * event.partialTicks;
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

    public int getDeltaVal(String from, int absval) throws Exception {
        if (from.equals("~")) {
            return absval;
        }
        if (from.startsWith("~")) {
            from = from.substring(1);
            int tval = Integer.parseInt(from);
            return tval + absval;
        }
        return Integer.parseInt(from);
    }

    @Override
    public boolean onPacket(Object packet, Side side) {
        if (packet instanceof C01PacketChatMessage) {
            String text = ((C01PacketChatMessage) packet).func_149439_c().trim();
            if (text.startsWith("/")) {
                text = text.substring(1);
                String[] params = text.split(" ");
                if (params[0].equals("tp") && params.length == 4) {
                    try {
                        int x = getDeltaVal(params[1], (MathHelper.floor_double(Wrapper.INSTANCE.player().posX)));
                        int y = getDeltaVal(params[2], (MathHelper.floor_double(Wrapper.INSTANCE.player().posY)));
                        int z = getDeltaVal(params[3], (MathHelper.floor_double(Wrapper.INSTANCE.player().posZ)));
                        InteropUtils.log("Teleported by SelfEnd", this);
                        tpEntity(Wrapper.INSTANCE.player(), x, y - 2, z);
                        return false;
                    } catch (Exception e) {

                    }
                }
                if (params[0].equals("tp") && params.length == 5) {
                    if (params[1].equals(Wrapper.INSTANCE.player().getCommandSenderName())) {
                        try {
                            int x = getDeltaVal(params[2], (MathHelper.floor_double(Wrapper.INSTANCE.player().posX)));
                            int y = getDeltaVal(params[3], (MathHelper.floor_double(Wrapper.INSTANCE.player().posY)));
                            int z = getDeltaVal(params[4], (MathHelper.floor_double(Wrapper.INSTANCE.player().posZ)));
                            InteropUtils.log("Teleported by SelfEnd", this);
                            tpEntity(Wrapper.INSTANCE.player(), x, y - 2, z);
                            return false;
                        } catch (Exception e) {

                        }
                    }
                    for (EntityPlayer entPly : (List<EntityPlayer>)Wrapper.INSTANCE.world().playerEntities) {
                        if (params[1].equals(entPly.getCommandSenderName())) {
                            try {
                                int x = getDeltaVal(params[2], (MathHelper.floor_double(Wrapper.INSTANCE.player().posX)));
                                int y = getDeltaVal(params[3], (MathHelper.floor_double(Wrapper.INSTANCE.player().posY)));
                                int z = getDeltaVal(params[4], (MathHelper.floor_double(Wrapper.INSTANCE.player().posZ)));
                                InteropUtils.log("Teleported by SelfEnd", this);
                                tpEntity(entPly, x, y - 2, z);
                                return false;
                            } catch (Exception e) {

                            }
                        }
                    }
                }
                if (params[0].equals("tppos") && params.length == 4) {
                    try {
                        int x = getDeltaVal(params[1], (MathHelper.floor_double(Wrapper.INSTANCE.player().posX)));
                        int y = getDeltaVal(params[2], (MathHelper.floor_double(Wrapper.INSTANCE.player().posY)));
                        int z = getDeltaVal(params[3], (MathHelper.floor_double(Wrapper.INSTANCE.player().posZ)));
                        InteropUtils.log("Teleported by SelfEnd", this);
                        tpEntity(Wrapper.INSTANCE.player(), x, y - 2, z);
                        return false;
                    } catch (Exception e) {

                    }
                }
            }
        }
        return true;
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
