/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands;

import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.modulesystem.classes.ItemCreator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 *
 * @author radioegor146
 */
public class ItemSelectCommand extends CommandBase {
    
        public static ItemStack SelectedStack;
    
        @Override
        public String getCommandName() {
            return "iselect";
        }

        @Override
        public String getCommandUsage(ICommandSender p_71518_1_) {
            return "Item give command";
        }

        @Override
        public List getCommandAliases() {
            return new ArrayList();
        }

        @Override
        public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
            if (p_71515_2_.length > 0)
            {
                try
                {
                    int enchant = 0;
                    boolean hasDamage = false;
                    if (p_71515_2_.length > 1 && "e1".equals(p_71515_2_[1]))
                        enchant = 1;
                    else if (p_71515_2_.length > 1 && "e2".equals(p_71515_2_[1]))
                        enchant = 2;
                    else
                        enchant = 0;
                    String itemToGive = p_71515_2_[0];
                    hasDamage = itemToGive.split(":").length > 1;
                    int itemId = Integer.valueOf(itemToGive.split(":")[0]);
                    int damage = itemToGive.split(":").length > 1 ? Integer.valueOf(itemToGive.split(":")[1]) : 0;
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

                    SelectedStack = toGive;


                    YouAlwaysWinClickGui.log("[Item Selector] ItemStack selected");
                } 
                catch (Exception e)
                {
                    YouAlwaysWinClickGui.log("[Item Selector] Wrong item");
                }
            }
        }

        @Override
        public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
            return true;
        }

        @Override
        public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
            return new ArrayList();
        }

        @Override
        public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
            return false;
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }
