/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands.classes;

import ehacks.mod.commands.ICommand;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Statics;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;

/**
 *
 * @author radioegor146
 */
public class ItemSelectCommand implements ICommand {

    @Override
    public String getName() {
        return "iselect";
    }

    @Override
    public void process(String[] p_71515_2_) {
        if (p_71515_2_.length > 0) {
            try {
                int enchant;
                boolean hasDamage;
                if (p_71515_2_.length > 1 && "e1".equals(p_71515_2_[1])) {
                    enchant = 1;
                } else if (p_71515_2_.length > 1 && "e2".equals(p_71515_2_[1])) {
                    enchant = 2;
                } else {
                    enchant = 0;
                }
                String itemToGive = p_71515_2_[0];
                hasDamage = itemToGive.split(":").length > 1;
                int itemId = Integer.valueOf(itemToGive.split(":")[0]);
                int damage = itemToGive.split(":").length > 1 ? Integer.valueOf(itemToGive.split(":")[1]) : 0;
                ItemStack toGive;
                if (hasDamage) {
                    toGive = new ItemStack(Item.getItemById(itemId), Item.getItemById(itemId).getItemStackLimit(), (short) damage);
                } else {
                    toGive = new ItemStack(Item.getItemById(itemId), Item.getItemById(itemId).getItemStackLimit());
                }

                if (enchant == 1) {
                    toGive.stackTagCompound = new NBTTagCompound();
                    NBTTagList tagList = new NBTTagList();
                    short[] enchs;
                    enchs = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 32, 33, 34, 35, 48, 49, 50, 51};
                    for (short en : enchs) {
                        NBTTagCompound ench = new NBTTagCompound();
                        ench.setShort("id", en);
                        ench.setShort("lvl", (short) 100);
                        tagList.appendTag(ench);
                    }
                    toGive.stackTagCompound.setTag("ench", tagList);
                }

                if (enchant == 2) {
                    toGive.stackTagCompound = new NBTTagCompound();
                    NBTTagList tagList = new NBTTagList();
                    short[] enchs;
                    enchs = new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 32, 33, 34, 35, 48, 49, 50, 51};
                    for (short en : enchs) {
                        NBTTagCompound ench = new NBTTagCompound();
                        ench.setShort("id", en);
                        ench.setShort("lvl", (short) 32767);
                        tagList.appendTag(ench);
                    }
                    toGive.stackTagCompound.setTag("ench", tagList);
                }

                Statics.STATIC_ITEMSTACK = toGive;
                Statics.STATIC_NBT = Statics.STATIC_ITEMSTACK.getTagCompound() == null ? new NBTTagCompound() : Statics.STATIC_ITEMSTACK.getTagCompound();

                InteropUtils.log("ItemStack selected", "Item Selector");
            } catch (Exception e) {
                InteropUtils.log("&cWrong item", "Item Selector");
            }
            return;
        }
        EHacksGui.clickGui.consoleGui.printChatMessage(new ChatComponentText("\u00a7c/" + this.getName() + " " + this.getCommandArgs()));
    }

    @Override
    public String getCommandDescription() {
        return "Selects an itemstack by id";
    }

    @Override
    public String getCommandArgs() {
        return "<id>[:damage] [e1|e2]";
    }

    @Override
    public String[] autoComplete(String[] args) {
        return new String[0];
    }
}
