/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.wrapper;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.packetprotector.MainProtector;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

/**
 *
 * @author radioegor146
 */
public class PacketHandler extends ChannelDuplexHandler {

    private Events eventHandler;

    public PacketHandler(Events eventHandler) {
        this.eventHandler = eventHandler;
        try {
            ChannelPipeline pipeline = Wrapper.INSTANCE.mc().getNetHandler().getNetworkManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", (ChannelHandler) this);
            EHacksClickGui.log("[PacketHandler] Attached");
            MainProtector.INSTANCE.init();
        } catch (Exception exception) {
            EHacksClickGui.log("[PacketHandler] Error on attaching");
            exception.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!eventHandler.onPacket(packet, Side.IN) || !MainProtector.INSTANCE.isPacketOk(packet)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!eventHandler.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public static enum Side {
        IN,
        OUT;
    }
}
