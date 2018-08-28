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
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.InflaterInputStream;

/**
 *
 * @author radioegor146
 */
public class IC2NuclearControlProtector implements IPacketProtector {

    @Override
    public boolean isPacketOk(Object packet) {
        try {
            if (packet instanceof FMLProxyPacket && "ic2".equals(((FMLProxyPacket) packet).channel())) {
                FMLProxyPacket fmlPacket = (FMLProxyPacket) packet;
                ByteBuf buf = Unpooled.copiedBuffer(fmlPacket.payload());
                DataInputStream is = new DataInputStream((InputStream) new ByteBufInputStream(buf));
                if (is.read() != 0) {
                    return true;
                }
                int state = is.read();
                if ((state >> 2) != 0) {
                    return true;
                }
                ByteArrayOutputStream largePacketBuffer = new ByteArrayOutputStream();
                if ((state & 1) != 0) {
                    largePacketBuffer = new ByteArrayOutputStream(16384);
                }
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    largePacketBuffer.write(buffer, 0, len);
                }
                if ((state & 2) == 0) {
                    return true;
                }
                ByteArrayInputStream inflateInput = new ByteArrayInputStream(largePacketBuffer.toByteArray());
                InflaterInputStream inflate = new InflaterInputStream(inflateInput);
                ByteArrayOutputStream inflateBuffer = new ByteArrayOutputStream(16384);
                while ((len = inflate.read(buffer)) != -1) {
                    inflateBuffer.write(buffer, 0, len);
                }
                inflate.close();
                byte[] subData = inflateBuffer.toByteArray();
                DataInputStream nis = new DataInputStream(new ByteArrayInputStream(subData));
                if (nis.readInt() != Wrapper.INSTANCE.player().dimension) {
                    return true;
                }
                int x = nis.readInt();
                int y = nis.readInt();
                int z = nis.readInt();
                byte[] fieldData = new byte[nis.readInt()];
                nis.readFully(fieldData);
                ByteArrayInputStream fieldDataBuffer = new ByteArrayInputStream(fieldData);
                DataInputStream fieldDataStream = new DataInputStream(fieldDataBuffer);
                HashMap<String, Object> fieldValues = new HashMap();
                do {
                    String fieldName = null;
                    try {
                        fieldName = fieldDataStream.readUTF();
                    } catch (EOFException e) {
                        break;
                    }
                    Object value = Class.forName("ic2.core.network.DataEncoder").getMethod("decode", DataInputStream.class).invoke(null, fieldDataStream);
                    if ("colorBackground".equals(fieldName) && (((Integer) value) > 15 || ((Integer) value) < 0)) {
                        return false;
                    }
                    if ("colorText".equals(fieldName) && (((Integer) value) > 15 || ((Integer) value) < 0)) {
                        return false;
                    }
                    fieldValues.put(fieldName, value);
                } while (true);
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
