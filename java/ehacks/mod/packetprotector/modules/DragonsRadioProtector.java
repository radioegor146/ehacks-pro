/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.packetprotector.modules;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import ehacks.mod.packetprotector.IPacketProtector;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author radioegor146
 */
public class DragonsRadioProtector implements IPacketProtector {

    @Override
    public boolean isPacketOk(Object packet) {
        try {
            if (packet instanceof FMLProxyPacket && "DragonsRadioMod".equals(((FMLProxyPacket)packet).channel())) {
                FMLProxyPacket fmlPacket = (FMLProxyPacket)packet;
                ByteBuf buf = Unpooled.copiedBuffer(fmlPacket.payload());
                if (buf.readByte() != 0) return true;
                buf.readInt();
                TileEntity entity = Wrapper.INSTANCE.world().getTileEntity((int)buf.readDouble(), (int)buf.readDouble(), (int)buf.readDouble());
                    if (entity == null || !Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio").isInstance(entity))
                        return false;
                    else
                        return true;
            }
            else
                return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
}
