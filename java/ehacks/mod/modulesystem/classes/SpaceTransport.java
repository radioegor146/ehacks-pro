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

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import static ehacks.mod.modulesystem.classes.BlockDestroy.isActive;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
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
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class SpaceTransport
extends Mod {
    public SpaceChanger changer;
    
    public SpaceTransport() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "Space Trans";
    }

    @Override
    public String getDescription() {
        return "Moves you";
    }
    
    public class SpaceChanger extends ChannelDuplexHandler {

        boolean state;
        
        public SpaceChanger() {
            Minecraft.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().addBefore("packet_handler", "spacehandler", this);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
            if (state && packet instanceof FMLProxyPacket && "GalacticraftCore".equals(((FMLProxyPacket)packet).channel())) {
                 ByteBuf buf = ((FMLProxyPacket)packet).payload();
                 if (buf.readByte() == 4)
                 {
                     buf.readInt();
                     buf.readDouble();
                     buf.readDouble();
                     buf.readDouble();
                     buf.readFloat();
                     buf.readFloat();
                     double x = buf.readDouble();
                     double y = buf.readDouble();
                     double z = buf.readDouble();
                     if (Math.abs(x - 0.0001337) > 0.0000001 || Math.abs(y - 0.0001337) > 0.0000001 || Math.abs(z - 0.0001337) > 0.0000001)
                     {
                        return;
                     }
                 }
            }
            super.write(ctx, packet, promise);
        }
        
        public void setState(boolean state) {
            this.state = state;
        }

    }
    
    @Override
    public void onTicks() {
        try {
            if (Wrapper.INSTANCE.player().ridingEntity == null || !Class.forName("micdoodle8.mods.galacticraft.core.network.PacketEntityUpdate$IEntityFullSync").isInstance(Wrapper.INSTANCE.player().ridingEntity))
            {
                this.off();
                return;
            }
            double x = Wrapper.INSTANCE.player().ridingEntity.posX;
            double y = Wrapper.INSTANCE.player().ridingEntity.posY;
            double z = Wrapper.INSTANCE.player().ridingEntity.posZ;
            double d = 0.3;
            if (Keyboard.isKeyDown(Keyboard.KEY_W))
            {
                x += d;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S))
            {
                x -= d;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A))
            {
                z += d;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D))
            {
                z -= d;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            {
                y += d;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_Z))
            {
                y -= d;
            }
            ByteBuf buf = Unpooled.buffer();
            buf.writeByte(4);
            buf.writeInt(Wrapper.INSTANCE.player().ridingEntity.getEntityId());
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            buf.writeFloat(Wrapper.INSTANCE.player().ridingEntity.rotationYaw);
            buf.writeFloat(Wrapper.INSTANCE.player().ridingEntity.rotationPitch);
            buf.writeDouble(0);
            buf.writeDouble(0);
            buf.writeDouble(0);
            buf.writeBoolean(true);
            Wrapper.INSTANCE.player().ridingEntity.posX = x;
            Wrapper.INSTANCE.player().ridingEntity.posY = y;
            Wrapper.INSTANCE.player().ridingEntity.posZ = z;
            C17PacketCustomPayload packet = new C17PacketCustomPayload("GalacticraftCore", buf);
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpaceTransport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketEntityUpdate").getConstructor();
            if (Wrapper.INSTANCE.player().ridingEntity == null || !Class.forName("micdoodle8.mods.galacticraft.core.network.PacketEntityUpdate$IEntityFullSync").isInstance(Wrapper.INSTANCE.player().ridingEntity))
            {
                YouAlwaysWinClickGui.log("[Space Transport] Wrong entity");
                this.off();
            }
            try
            {
                //changer = new SpaceChanger();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //changer.setState(true);
        } catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[Space Transport] Not working");
        }
    }
    
    @Override
    public void onDisableMod() {
        //changer.setState(false);
    }
    
    @Override
    public void onMouse(MouseEvent event) {
        
    }
}

