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
import ehacks.api.module.Module;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import static ehacks.mod.modulesystem.classes.BlockDestroy.isActive;
import ehacks.mod.util.ExceptionUtils;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
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

public class Test
extends Module {
    public Test() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "Test";
    }
    
    @Override
    public void onEnableMod() {

    }
    
    @Override
    public String getModName() {
        return "Test";
    }
    
    @Override
    public String getDescription() {
        return "Test";
    }
    
    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        if (side == PacketHandler.Side.OUT && (packet instanceof net.minecraft.network.play.client.C07PacketPlayerDigging))
            return false;
        return true;
    }
}

