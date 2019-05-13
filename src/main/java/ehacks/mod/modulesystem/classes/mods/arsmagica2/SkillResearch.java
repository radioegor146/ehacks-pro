package ehacks.mod.modulesystem.classes.mods.arsmagica2;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class SkillResearch
        extends Module {

    public SkillResearch() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "SkillResearch";
    }

    @Override
    public String getDescription() {
        return "Researches all kinds of skills in Ars Magica";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("am2.spell.SkillManager");
            ByteBuf buf = Unpooled.buffer(0);
            buf.writeByte(27);
            buf.writeByte(2);
            buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
            buf.writeInt(31);
            buf.writeBoolean(false);
            buf.writeInt(((Collection<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllShapes").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))).size());
            for (Integer i : (Iterable<? extends Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllShapes").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))) {
                buf.writeInt(i);
            }
            buf.writeInt(((Collection<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllComponents").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))).size());
            for (Integer i : (Iterable<? extends Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllComponents").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))) {
                buf.writeInt(i);
            }
            buf.writeInt(((Collection<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllModifiers").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))).size());
            for (Integer i : (Iterable<? extends Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllModifiers").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))) {
                buf.writeInt(i);
            }
            buf.writeInt(((Collection<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllTalents").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))).size());
            for (Integer i : (Iterable<? extends Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllTalents").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))) {
                buf.writeInt(i);
            }
            buf.writeInt(1337);
            buf.writeInt(1337);
            buf.writeInt(1337);
            C17PacketCustomPayload packet = new C17PacketCustomPayload("AM2DataTunnel", buf);
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);

            buf = Unpooled.buffer(0);
            buf.writeByte(27);
            buf.writeByte(1);
            buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
            buf.writeInt(0);
            buf.writeInt(4);
            packet = new C17PacketCustomPayload("AM2DataTunnel", buf);
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
            this.off();
            InteropUtils.log("Gived", this);
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("am2.spell.SkillManager");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    static Integer[] getIntArray(ArrayList<Integer> arrayList) {
        return arrayList.toArray(new Integer[arrayList.size()]);
    }

    @Override
    public String getModName() {
        return "Ars Magic";
    }
}
