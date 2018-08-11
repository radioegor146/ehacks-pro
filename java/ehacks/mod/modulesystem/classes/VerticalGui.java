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

import cpw.mods.fml.common.network.ByteBufUtils;
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
import ehacks.mod.commands.ItemSelectCommand;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.window.WindowPlayerIds;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.input.Keyboard;

public class VerticalGui
extends Mod {
    public VerticalGui() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "VerticalGui";
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketFingersToServer");
            YouAlwaysWinClickGui.log("[VerticalGui] Press Y for GUI");
        }
        catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[VerticalGui] Not working");
        }
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketFingersToServer");
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
        boolean newState = Keyboard.isKeyDown(Keyboard.KEY_Y);
        if (newState && !prevState)
        {
            openGui();
        }
        prevState = newState;
    }
    
    public void openGui() {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(9);
        buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumichorizons", buf);
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
        YouAlwaysWinClickGui.log("[VerticalGui] Opened");
    }
    
    @Override
    public String getModName() {
        return "Horizons";
    }
}

