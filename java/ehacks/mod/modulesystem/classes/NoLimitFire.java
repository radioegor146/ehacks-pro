/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.world.World
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.commands.ACommandAuraRange;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.window.WindowPlayerIds;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.relationsystem.Friend;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class NoLimitFire
extends Mod {
    public NoLimitFire() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitFire";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor();
        }
        catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[NoLimitFire] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }

    @Override
    public void onDisableMod() {
        
    }

    private float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    private boolean isWithinRange(float range, Entity e) {
        return this.getDistanceToEntity(e, (Entity)Wrapper.INSTANCE.player()) <= range;
    }

    @Override
    public void onTicks() {
        try {
            List<Entity> players = (List<Entity>)(WindowPlayerIds.useIt ? (Object)WindowPlayerIds.getPlayers() : Wrapper.INSTANCE.world().loadedEntityList);
            for (Object o : players) {
                if (((Entity)o).getEntityId() != Wrapper.INSTANCE.player().getEntityId())
                    fireEntity(((Entity)o).getEntityId());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void fireEntity(int entityId) {
        try {
            Object packetPipeLine = Class.forName("micdoodle8.mods.galacticraft.core.GalacticraftCore").getField("packetPipeline").get(null);
            Method sendMethod = packetPipeLine.getClass().getMethod("sendToServer", new Class[] { Class.forName("micdoodle8.mods.galacticraft.core.network.IPacket") });
            Object packetObj = Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor(new Class[] { Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket"), Object[].class }).newInstance(Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket").getMethod("valueOf", String.class).invoke(null, "S_SET_ENTITY_FIRE"), new Object[] { entityId });
            sendMethod.invoke(packetPipeLine, packetObj);
        } catch (Exception ex) {
            
        }
    }
}

