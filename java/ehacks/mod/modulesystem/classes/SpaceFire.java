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

public class SpaceFire
extends Mod {
    public SpaceFire() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "Space Fire";
    }

    @Override
    public String getDescription() {
        return "Fires entities";
    }
    
    @Override
    public void onTick() {
        
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor();
        } catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[Space Fire] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }
    
    @Override
    public void onMouse(MouseEvent event) {
        try
        {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            if (position.entityHit != null && position.entityHit instanceof EntityLivingBase && Mouse.isButtonDown(0))
            {
                Object packetPipeLine = Class.forName("micdoodle8.mods.galacticraft.core.GalacticraftCore").getField("packetPipeline").get(null);
                Method sendMethod = packetPipeLine.getClass().getMethod("sendToServer", new Class[] { Class.forName("micdoodle8.mods.galacticraft.core.network.IPacket") });
                Object packetObj = Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor(new Class[] { Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket"), Object[].class }).newInstance(Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket").getMethod("valueOf", String.class).invoke(null, "S_SET_ENTITY_FIRE"), new Object[] { position.entityHit.getEntityId() });
                sendMethod.invoke(packetPipeLine, packetObj);
                /*ByteBuf buf = Unpooled.buffer();
                buf.writeByte(0);
                buf.writeInt(7);
                buf.writeInt(position.entityHit.getEntityId());
                C17PacketCustomPayload packet = new C17PacketCustomPayload("GalacticraftCore", buf);
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);*/
                if (event.isCancelable())
                    event.setCanceled(true);
            }
        }
        catch (Exception e)
        {

        }
    }
}

