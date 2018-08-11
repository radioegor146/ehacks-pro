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
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
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

public class JesusGift
extends Mod {
    public JesusGift() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "JesusGift";
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
        }
        catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[JesusGift] Not working");
        }
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
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
        boolean newState = Keyboard.isKeyDown(Keybinds.give);
        if (newState && !prevState)
        {
            prevState = newState;
            if (Statics.STATIC_ITEMSTACK == null)
                return;
            tryAtlas(Statics.STATIC_ITEMSTACK);
        }
        prevState = newState;
    }
    
    public void tryAtlas(ItemStack item) {
        ItemStack handItem = Wrapper.INSTANCE.player().getCurrentEquippedItem();
        if (handItem != null && handItem.getUnlocalizedName().equals("item.AtlasBook")) {
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.setByte("autoCenter", (byte)0);
            itemTag.setInteger("lastGUImode", 0);
            itemTag.setInteger("mapSlot", -1);
            itemTag.setInteger("zoomLevel", 0);
            itemTag.setTag("maps", new NBTTagList());
            NBTTagList inventoryList = new NBTTagList();
            for (int slot = 0; slot < 5; slot++) {
                inventoryList.appendTag(nbtItem(item, slot, true));
            }
            inventoryList.appendTag(nbtItem(handItem, 5, false));
            itemTag.setTag("Inventory", inventoryList);
            handItem.setTagCompound(itemTag);
            ByteBuf buf = Unpooled.buffer(0);
            ByteBufUtils.writeItemStack(buf, handItem);
            try {
                Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(Class.forName("jds.bibliocraft.BiblioCraft").getDeclaredField("ch_BiblioAtlasGUIswap").get(null),Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(buf, "BiblioAtlasSWP"));
            } catch(Exception e) {
                YouAlwaysWinClickGui.log("[JesusGift] Mod error");
            }
        } else {
            YouAlwaysWinClickGui.log("[JesusGift] Wrong item in hand");
        }
    }
    
    private NBTTagCompound nbtItem(ItemStack item, int slot, boolean withNbt) {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByte("Count",(byte)item.stackSize);
        itemTag.setByte("Slot",(byte)slot);
        itemTag.setShort("id",(short)item.getItem().getIdFromItem(item.getItem()));
        itemTag.setShort("Damage",(short)item.getItemDamage());
        if (item.hasTagCompound() && withNbt) {
                itemTag.setTag("tag", item.getTagCompound());
        }
        return itemTag;
    }
    
    @Override
    public String getModName() {
        return "BiblioCraft";
    }
}

