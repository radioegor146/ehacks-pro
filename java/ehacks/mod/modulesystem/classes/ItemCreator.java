/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes;

/**
 *
 * @author radioegor146
 * 
            ItemStack stack = null;
            try
            {
                stack = new ItemStack(Item.getItemById(keybind), 1, Item.getItemById(keybind).getItemStackLimit());
            }
            catch (Exception e)
            {
                
            }
 * 
 * 
 * 
 */

import cpw.mods.fml.common.network.ByteBufUtils;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ItemCreator
extends Mod {
    public ItemCreator() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "Item Creator";
    }

    public void giveItem(int itemId, int damage, boolean hasDamage, int enchant) {
        ItemStack toGive = null;
        if (hasDamage)
            toGive = new ItemStack(Item.getItemById(itemId), Item.getItemById(itemId).getItemStackLimit(), (short)damage);
        else
            toGive = new ItemStack(Item.getItemById(itemId), Item.getItemById(itemId).getItemStackLimit());
        
        if (enchant == 1)
        {
            toGive.stackTagCompound = new NBTTagCompound();   
            NBTTagList tagList = new NBTTagList();
            short[] enchs;
            enchs = new short[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 32, 33, 34, 35, 48, 49, 50, 51 };
            for (short en : enchs)
            {
                NBTTagCompound ench = new NBTTagCompound();
                ench.setShort("id", en);
                ench.setShort("lvl", (short)100);
                tagList.appendTag(ench);
            }
            toGive.stackTagCompound.setTag("ench", tagList);
        }
        
        if (enchant == 2)
        {
            toGive.stackTagCompound = new NBTTagCompound();   
            NBTTagList tagList = new NBTTagList();
            short[] enchs;
            enchs = new short[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 32, 33, 34, 35, 48, 49, 50, 51 };
            for (short en : enchs)
            {
                NBTTagCompound ench = new NBTTagCompound();
                ench.setShort("id", en);
                ench.setShort("lvl", (short)32767);
                tagList.appendTag(ench);
            }
            toGive.stackTagCompound.setTag("ench", tagList);
        }
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(3);
        
        ItemStack mail = new ItemStack(Item.getItemById(4123));
        NBTTagList tagList = new NBTTagList();

        NBTTagCompound item = new NBTTagCompound();
        item.setByte("Slot", (byte)0);
        toGive.writeToNBT(item);
        tagList.appendTag((NBTBase)item);

        NBTTagCompound item2 = new NBTTagCompound();
        item2.setByte("Slot", (byte)1);
        toGive.writeToNBT(item2);
        tagList.appendTag((NBTBase)item2);

        NBTTagCompound inv = new NBTTagCompound();
        inv.setTag("Items", (NBTBase)tagList);
        inv.setString("UniqueID", UUID.randomUUID().toString());
        mail.stackTagCompound = new NBTTagCompound();
        mail.stackTagCompound.setTag("Envelope", inv);
        ByteBufUtils.writeItemStack(buf, mail); 
        C17PacketCustomPayload packet = new C17PacketCustomPayload("cfm", buf);
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    @Override
    public void onTicks() {
        if (this.enabled && Events.itemToGive != null) {
            try
            {
                giveItem(Integer.valueOf(Events.itemToGive.split(":")[0]), Events.itemToGive.split(":").length > 1 ? Integer.valueOf(Events.itemToGive.split(":")[1]) : 0, Events.itemToGive.split(":").length > 1, Events.enchant);
                YouAlwaysWinClickGui.log("[Item Creator] Giving " + Events.itemToGive);
            }
            catch (Exception e)
            {
                YouAlwaysWinClickGui.log("[Item Creator] No such item - " + Events.itemToGive);
            }
            Events.itemToGive = null;
        }
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessagePackage").getConstructor();
            Events.itemGiveEnabled = true;
        } catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[Item Creator] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }

    @Override
    public void onDisableMod() {
        Events.itemGiveEnabled = false;
        Events.itemToGive = null;
    }
}


