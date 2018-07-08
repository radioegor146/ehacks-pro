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

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import static ehacks.mod.modulesystem.classes.BlockDestroy.isActive;
import static ehacks.mod.modulesystem.classes.PrivateNuker.isActive;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
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
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Mouse;

public class ExtendedNuker
extends Mod {
    public int radius = 5;
    
    public ExtendedNuker() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "ExtNuker";
    }

    @Override
    public String getDescription() {
        return "Breaks blocks instantly";
    }
    
    @Override
    public void onTick() {
        
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessageTakeWater").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
        } catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[Extended Nuker] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }
    
    @Override 
    public void onTicks() {
        if (Wrapper.INSTANCE.mc().playerController.isInCreativeMode()) 
        {
            for (int i = radius; i >= - radius; --i) {
                for (int k = radius; k >= - radius; --k) {
                    for (int j = - radius; j <= radius; ++j) {
                        int x = (int)(Wrapper.INSTANCE.player().posX + (double)i);
                        int y = (int)(Wrapper.INSTANCE.player().posY + (double)j);
                        int z = (int)(Wrapper.INSTANCE.player().posZ + (double)k);
                        Block blockID = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (blockID.getMaterial() == Material.air) 
                            continue;
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(0, x, y, z, 0));
                    }
                }
            }
        }
        if (Wrapper.INSTANCE.mc().playerController.isNotCreative()) 
        {
            for (int i = radius; i >= - radius; --i) {
                for (int k = radius; k >= - radius; --k) {
                    for (int j = - radius; j <= radius; ++j) {
                        int x = (int)(Wrapper.INSTANCE.player().posX + (double)i);
                        int y = (int)(Wrapper.INSTANCE.player().posY + (double)j);
                        int z = (int)(Wrapper.INSTANCE.player().posZ + (double)k);
                        Block block = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (block.getMaterial() == Material.air) 
                            continue;
                        ByteBuf buf = Unpooled.buffer();
                        buf.writeByte(14);
                        buf.writeInt(x);
                        buf.writeInt(y);
                        buf.writeInt(z);
                        C17PacketCustomPayload packet = new C17PacketCustomPayload("cfm", buf);
                        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
                    }
                }
            }
        }
    }
}

