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
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.input.Keyboard;

public class NoLimitBuffs
extends Mod {
    public NoLimitBuffs() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitBuffs";
    }
    
    @Override
    public String getDescription() {
        return "Allows you to put a lot of modifiers to spell";
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[NoLimitBuffs] Not working");
        }
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {
         
    }
    
    private boolean prevState = false;
    
    static void setFinalStatic(Field field, Object to, Integer[] newValue) throws Exception {
      field.setAccessible(true);

      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

      field.set(to, newValue);
    }
    
    static Object getFinalStatic(Field field, Object from) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        return field.get(from);
    }
    
    static Integer[] getIntArray(ArrayList<Integer> arrayList)
    {
        return arrayList.toArray(new Integer[arrayList.size()]);
    }
    
    @Override
    public void onTicks() {
        try {
            Object currentScreen = Wrapper.INSTANCE.mc().currentScreen;
            if (currentScreen != null && Class.forName("am2.guis.GuiInscriptionTable").isInstance(currentScreen))
            {
                Object container = ((GuiContainer)currentScreen).inventorySlots;
                Object tileEntity = getFinalStatic(Class.forName("am2.containers.ContainerInscriptionTable").getDeclaredField("table"), container);
                HashMap<Object, Integer> modCounts = (HashMap<Object, Integer>)getFinalStatic(Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable").getDeclaredField("modifierCount"), tileEntity);
                for (Map.Entry<Object, Integer> entry : modCounts.entrySet()) {
                    entry.setValue(0);
                }
            }
        } catch (Exception e) {
            EHacksClickGui.log("[NoLimitBuffs] Error");
            e.printStackTrace();
        }
    }
    
    @Override
    public String getModName() {
        return "Ars Magic";
    }
}

