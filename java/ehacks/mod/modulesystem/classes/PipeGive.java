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
import ehacks.api.module.Module;
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
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import org.lwjgl.input.Keyboard;

public class PipeGive
extends Module {
    
    public PipeGive() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "PipeGive";
    }
    
    @Override
    public String getDescription() {
        return "You can put any ItemStack in Construction Marker from BuildCraft\nUsage: \n  Numpad2 - Perform action on container";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("buildcraft.builders.TileConstructionMarker");
            Class.forName("buildcraft.core.Box");
            try {
                Class.forName("buildcraft.core.lib.utils.NetworkUtils");
            } catch (Exception ex) {
                Class.forName("buildcraft.core.utils.Utils");
            }
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[PipeGive] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("buildcraft.builders.TileConstructionMarker");
            Class.forName("buildcraft.core.Box");
            try {
                Class.forName("buildcraft.core.lib.utils.NetworkUtils");
            } catch (Exception ex) {
                Class.forName("buildcraft.core.utils.Utils");
            }
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }
    
    @Override
    public void onDisableMod() {
        
    }
    
    private boolean prevState = false;
    
    @Override
    public void onTicks() {
        try {
            boolean newState = Keyboard.isKeyDown(Keybinds.give);
            if (newState && !prevState)
            {
                prevState = newState;
                MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
                TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                if (entity != null && Class.forName("buildcraft.builders.TileConstructionMarker").isInstance(entity))
                {
                    setSlot(mop.blockX, mop.blockY, mop.blockZ);
                    EHacksClickGui.log("[PipeGive] Set");
                }
            }
            prevState = newState;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setSlot(int x, int y, int z) throws Exception {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeShort(0); //packetid
        buf.writeInt(x); //x
        buf.writeShort(y); //y
        buf.writeInt(z); //z
        //packetdata
        Class.forName("buildcraft.core.Box").getMethod("writeData", ByteBuf.class).invoke(Class.forName("buildcraft.core.Box").getConstructor().newInstance(), buf);
        buf.writeByte(2);
        try {
            Class.forName("buildcraft.core.lib.utils.NetworkUtils").getMethod("writeStack", ByteBuf.class, ItemStack.class).invoke(null, buf, Statics.STATIC_ITEMSTACK);
        } catch (Exception ex) {
            Class.forName("buildcraft.core.utils.Utils").getMethod("writeStack", ByteBuf.class, ItemStack.class).invoke(null, buf, Statics.STATIC_ITEMSTACK);
        }
        //endpacketdata
        C17PacketCustomPayload packet = new C17PacketCustomPayload("BC-CORE", buf);
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    @Override
    public String getModName() {
        return "BuildCraft";
    }
}

