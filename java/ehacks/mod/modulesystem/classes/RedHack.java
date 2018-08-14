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
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import org.lwjgl.input.Keyboard;

public class RedHack
extends Mod {
    
    public RedHack() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "RedHack";
    }
    
    @Override
    public String getDescription() {
        return "Allows you to give any ItemStack to your hand\nUsage:\n  Numpad2 - Gives an item";
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("mrtjp.projectred.transportation.TransportationSPH$");
            Class.forName("codechicken.lib.packet.PacketCustom");
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[ChestMagic] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("mrtjp.projectred.transportation.TransportationSPH$");
            Class.forName("codechicken.lib.packet.PacketCustom");
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
            int slotId = 27 + Wrapper.INSTANCE.player().inventory.currentItem;
            if (Statics.STATIC_ITEMSTACK == null)
                return;
            setRed(Statics.STATIC_ITEMSTACK, slotId);
            EHacksClickGui.log("[RedHack] Set");
        }
        prevState = newState;
    }
    
    public void setRed(ItemStack item, int slotId) {
        try {
            Object packetInstance = Class.forName("codechicken.lib.packet.PacketCustom").getConstructor(Object.class, Integer.TYPE).newInstance("PR|Transp", 4);
            Class.forName("codechicken.lib.packet.PacketCustom").getMethod("writeByte", Byte.TYPE).invoke(packetInstance, (byte)slotId);
            Class.forName("codechicken.lib.packet.PacketCustom").getMethod("writeItemStack", ItemStack.class).invoke(packetInstance, item);
            Class.forName("codechicken.lib.packet.PacketCustom").getMethod("sendToServer").invoke(packetInstance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public String getModName() {
        return "Project Red";
    }
}

