package ehacks.mod.wrapper;

import ehacks.mod.packetprotector.MainProtector;
import ehacks.mod.util.InteropUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;

/**
 * @author radioegor146
 */
public class PacketHandler extends ChannelDuplexHandler {

    private Events eventHandler;

    private MainProtector protector;

    public PacketHandler(Events eventHandler) {
        this.protector = new MainProtector();
        this.protector.init();
        this.eventHandler = eventHandler;
        try {
            ChannelPipeline pipeline = new NetworkManager(EnumPacketDirection.CLIENTBOUND).channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", this);
            InteropUtils.log("Attached", "PacketHandler");
        } catch (Exception exception) {
            InteropUtils.log("Error on attaching", "PacketHandler");
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
