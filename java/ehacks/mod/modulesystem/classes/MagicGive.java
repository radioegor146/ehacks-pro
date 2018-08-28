package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

public class MagicGive
        extends Module {

    public MagicGive() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "MagicGive";
    }

    @Override
    public String getDescription() {
        return "You can give yourself any spell you want";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[MagicGive] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {

    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keybinds.give);
        if (newState && !prevState) {
            prevState = newState;
            createSpell();
        }
        prevState = newState;
    }

    public void createSpell() {
        MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
        if (mop.sideHit == -1) {
            return;
        }
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(36);
        buf.writeInt(mop.blockX);
        buf.writeInt(mop.blockY);
        buf.writeInt(mop.blockZ);
        buf.writeByte(2);
        buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
        C17PacketCustomPayload packet = new C17PacketCustomPayload("AM2DataTunnel", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
        EHacksClickGui.log("[MagicGive] Gived");
    }

    @Override
    public String getModName() {
        return "Ars Magic";
    }
}
