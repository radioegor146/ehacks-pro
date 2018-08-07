/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 */
package ehacks.mod.modulesystem.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import ehacks.api.module.Mod;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.reeszrbteam.Tuple;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class PrivateNuker
extends Mod {
    public static boolean isActive = false;
    private static int radius = 5;

    public PrivateNuker() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "Private Nuker";
    }
    
    private Method snd;
    private Constructor msg;
    private Object obj;

    @Override
    public void onEnableMod() {
        isActive = true;
        try {
            msg = Class.forName("openmodularturrets.network.DropBaseMessage").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            msg.setAccessible(true);
            snd = Class.forName("cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.simpleimpl.IMessage"));
            snd.setAccessible(true);
            obj = Class.forName("openmodularturrets.ModularTurrets").getDeclaredField("networking").get(new Object[0]);
        }
        catch (Exception ex) {
            isActive = false;
            Events.selectedBlock = null;
            this.off();
            YouAlwaysWinClickGui.logData.add(new Tuple<String, Integer>("[Private Nuker] Not working", 0));
        }
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("openmodularturrets.network.DropBaseMessage");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {
        isActive = false;
        Events.selectedBlock = null;
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mc().playerController.isInCreativeMode())
            for (int i = PrivateNuker.radius; i >= - radius; --i) {
                for (int k = PrivateNuker.radius; k >= - radius; --k) {
                    for (int j = - PrivateNuker.radius; j <= radius; ++j) {
                        int x = (int)(Wrapper.INSTANCE.player().posX + (double)i);
                        int y = (int)(Wrapper.INSTANCE.player().posY + (double)j);
                        int z = (int)(Wrapper.INSTANCE.player().posZ + (double)k);
                        Block blockID = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (blockID.getMaterial() == Material.air) 
                            continue;
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(0, x, y, z, 0));
                    }
                }
            }
        if (Wrapper.INSTANCE.mc().playerController.isNotCreative())
            for (int i = PrivateNuker.radius; i >= - radius; --i) {
                for (int k = PrivateNuker.radius; k >= - radius; --k) {
                    for (int j = - PrivateNuker.radius; j <= radius; ++j) {
                        int x = (int)(Wrapper.INSTANCE.player().posX + (double)i);
                        int y = (int)(Wrapper.INSTANCE.player().posY + (double)j);
                        int z = (int)(Wrapper.INSTANCE.player().posZ + (double)k);
                        Block block = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (block.getMaterial() == Material.air) continue;
                        try {
                            snd.invoke(obj, msg.newInstance(x, y, z));
                            continue;
                        }
                        catch (Exception ex) {
                            isActive = false;
                            Events.selectedBlock = null;
                            this.off();
                            ex.printStackTrace();
                        }
                    }
                }
            }
    }
    
    @Override
    public String getModName() {
        return "OMTurrets";
    }
}

