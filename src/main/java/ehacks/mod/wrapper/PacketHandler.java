package ehacks.mod.wrapper;

import ehacks.mod.packetprotector.MainProtector;
import ehacks.mod.util.InteropUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

/**
 * @author radioegor146
 */
public class PacketHandler extends ChannelDuplexHandler {

    private Events eventHandler;

    private MainProtector protector;

    public PacketHandler(Events eventHandler) {
        try {
            ChannelPipeline pipeline = Wrapper.INSTANCE.mc().getConnection().getNetworkManager().channel().pipeline();

            if (pipeline.get("PacketHandler") != null) { // Дикий костыль, который фиксит двойной вызов этого конструктора.
                return;                                 // TODO: необходимо нормально пофиксить
            }

            this.protector = new MainProtector();
            this.protector.init();
            this.eventHandler = eventHandler;

            pipeline.addBefore("packet_handler", "PacketHandler", this);
            InteropUtils.log("Attached", "PacketHandler");
        } catch (Exception exception) {
            InteropUtils.log("Error on attaching - " + exception.toString(), "PacketHandler");
            exception.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!eventHandler.onPacket(packet, Side.IN) || !protector.isPacketOk(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!eventHandler.onPacket(packet, Side.OUT) || !protector.isPacketOk(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public enum Side {
        IN,
        OUT
    }
}
