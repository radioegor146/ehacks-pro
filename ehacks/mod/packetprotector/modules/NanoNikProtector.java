/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.packetprotector.modules;

import ehacks.mod.packetprotector.IPacketProtector;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.PacketHandler;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.network.play.client.C17PacketCustomPayload;

/**
 *
 * @author radioegor146
 */
public class NanoNikProtector implements IPacketProtector {

    @Override
    public boolean isPacketOk(Object packet, PacketHandler.Side side) {
        try {
            if (packet instanceof C17PacketCustomPayload && "nGuard".equals(((C17PacketCustomPayload) packet).func_149559_c()) && side == PacketHandler.Side.OUT) {
                ByteBuf buf = Unpooled.copiedBuffer(((C17PacketCustomPayload) packet).func_149558_e());
                DataInputStream is = new DataInputStream(new ByteBufInputStream(buf));
                String data = is.readUTF();
                String aim = data.split(";")[0];
                if (data.split(";")[1].equals("https://i.imgur.com/BqOhWwR.png")) {
                    return true;
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream outputStream = new DataOutputStream(bos);
                outputStream.writeUTF(aim + ";https://i.imgur.com/BqOhWwR.png");
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue(new C17PacketCustomPayload("nGuard", bos.toByteArray()));
                InteropUtils.log(String.format("You have been screened with aim: '%s'; Admins have been flek$$ed!", aim), "NanoNikGuard");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
