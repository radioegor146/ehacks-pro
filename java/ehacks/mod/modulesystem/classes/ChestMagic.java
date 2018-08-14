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

import cpw.mods.fml.client.registry.ClientRegistry;
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
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import org.lwjgl.input.Keyboard;

public class ChestMagic
extends Mod {
    
    public ChestMagic() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ChestMagic";
    }
    
    @Override
    public String getDescription() {
        return "You can fill containers with an item that player is holding in inventory\nUsage: \n  Numpad3 - Select player\n  Numpad2 - Perform action on container";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[ChestMagic] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }
    
    @Override
    public void onDisableMod() {
        
    }
    
    private boolean prevState = false;
    private boolean prevStateT = false;
    
    private int playerId = -1;
    
    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keybinds.give);
        if (newState && !prevState)
        {
            prevState = newState;
            MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
            TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            if (entity != null && entity instanceof IInventory)
            {
                IInventory inv = (IInventory)entity;
                boolean setFull = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
                int count = setFull ? (Wrapper.INSTANCE.player().inventory.getItemStack() == null ? 1 : Wrapper.INSTANCE.player().inventory.getItemStack().getMaxStackSize()) : 1;
                for (int i = 0; i < inv.getSizeInventory(); i++)
                    for (int j = 0; j < count; j++)
                        setSlot(i, mop.blockX, mop.blockY, mop.blockZ, playerId == -1 ? Wrapper.INSTANCE.player().getEntityId() : playerId);
                EHacksClickGui.log("[ChestMagic] Set");
            }
        }
        prevState = newState;
        boolean newStateT = Keyboard.isKeyDown(Keybinds.selectPlayer);
        if (newStateT && !prevStateT)
        {
            prevStateT = newStateT;
            MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
            if (mop.entityHit != null && mop.entityHit instanceof EntityPlayer) {
                playerId = mop.entityHit.getEntityId();
                EHacksClickGui.log("[ChestMagic] Set to player");
                return;
            }
            playerId = -1;
            EHacksClickGui.log("[ChestMagic] Player cleared");
        }
        prevStateT = newStateT;
    }
    
    public void setSlot(int slotId, int x, int y, int z, int playerId) {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        buf.writeShort(20);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(playerId);
        buf.writeInt(slotId);
        buf.writeByte(0);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("MFReloaded", buf);
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    @Override
    public String getModName() {
        return "MFR";
    }
}

