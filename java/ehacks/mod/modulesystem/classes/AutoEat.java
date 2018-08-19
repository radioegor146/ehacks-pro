/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class AutoEat
extends Module {
    private int counter = 0;

    public AutoEat() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Auto Eat";
    }

    @Override
    public String getDescription() {
        return "Automatically makes you eat the food you're holding";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().getHeldItem() != null && Wrapper.INSTANCE.player().getHeldItem().getItem() instanceof ItemFood) {
            ItemFood item = (ItemFood)Wrapper.INSTANCE.player().getHeldItem().getItem();
            if (!Minecraft.getMinecraft().thePlayer.isEating()) {
                Wrapper.INSTANCE.player().setItemInUse(Wrapper.INSTANCE.player().getHeldItem(), item.getMaxItemUseDuration(Wrapper.INSTANCE.player().getHeldItem()));
                Minecraft.getMinecraft().thePlayer.setEating(true);
            }
        }
    }
}

