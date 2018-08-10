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

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Mod;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import static ehacks.mod.modulesystem.classes.BlockDestroy.isActive;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Mouse;

public class FakeDestroy
extends Mod {
    public FakeDestroy() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "FakeDestroy";
    }

    @Override
    public String getDescription() {
        return "Fake destroyer";
    }
    
    private ArrayList<int[]> removedBlocks = new ArrayList<int[]>();
    
    private boolean prevState = false;
    
    @Override
    public void onMouse(MouseEvent event) {
        try
        {
            boolean nowState = Mouse.isButtonDown(0);
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            if (position.sideHit != -1 && !prevState && nowState)
            {
                Wrapper.INSTANCE.world().setBlockToAir(position.blockX, position.blockY, position.blockZ);
                removedBlocks.add(new int[] {position.blockX, position.blockY, position.blockZ});
                if (event.isCancelable())
                    event.setCanceled(true);
            }
        }
        catch (Exception e)
        {

        }
    }
    
    @Override
    public void onEnableMod() {
        removedBlocks = new ArrayList<int[]>();
    }
    
    @Override
    public void onDisableMod() {
        for (int[] block : removedBlocks) {
            
        }
    }
}

 