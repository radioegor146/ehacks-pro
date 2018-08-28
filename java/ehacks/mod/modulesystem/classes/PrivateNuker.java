package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.Tuple;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class PrivateNuker
        extends Module {

    public static boolean isActive = false;
    private static final int radius = 5;

    public PrivateNuker() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "Private Nuker";
    }

    @Override
    public String getDescription() {
        return "Destroys all blocks around you in radius of 5 blocks";
    }

    private Method snd;
    private Constructor msg;
    private Object obj;

    @Override
    public void onEnableMod() {
        isActive = true;
        try {
            msg = Class.forName("openmodularturrets.network.DropBaseMessage").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            msg.setAccessible(true);
            snd = Class.forName("cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.simpleimpl.IMessage"));
            snd.setAccessible(true);
            obj = Class.forName("openmodularturrets.ModularTurrets").getDeclaredField("networking").get(new Object[0]);
        } catch (Exception ex) {
            isActive = false;
            Events.selectedBlock = null;
            this.off();
            EHacksClickGui.logData.add(new Tuple<String, Integer>("[Private Nuker] Not working", 0));
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("openmodularturrets.network.DropBaseMessage");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {
        isActive = false;
        Events.selectedBlock = null;
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mc().playerController.isInCreativeMode()) {
            for (int i = PrivateNuker.radius; i >= -radius; --i) {
                for (int k = PrivateNuker.radius; k >= -radius; --k) {
                    for (int j = -PrivateNuker.radius; j <= radius; ++j) {
                        int x = (int) (Wrapper.INSTANCE.player().posX + (double) i);
                        int y = (int) (Wrapper.INSTANCE.player().posY + (double) j);
                        int z = (int) (Wrapper.INSTANCE.player().posZ + (double) k);
                        Block blockID = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (blockID.getMaterial() == Material.air) {
                            continue;
                        }
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C07PacketPlayerDigging(0, x, y, z, 0));
                    }
                }
            }
        }
        if (Wrapper.INSTANCE.mc().playerController.isNotCreative()) {
            for (int i = PrivateNuker.radius; i >= -radius; --i) {
                for (int k = PrivateNuker.radius; k >= -radius; --k) {
                    for (int j = -PrivateNuker.radius; j <= radius; ++j) {
                        int x = (int) (Wrapper.INSTANCE.player().posX + (double) i);
                        int y = (int) (Wrapper.INSTANCE.player().posY + (double) j);
                        int z = (int) (Wrapper.INSTANCE.player().posZ + (double) k);
                        Block block = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (block.getMaterial() == Material.air) {
                            continue;
                        }
                        try {
                            snd.invoke(obj, msg.newInstance(x, y, z));
                        } catch (Exception ex) {
                            isActive = false;
                            Events.selectedBlock = null;
                            this.off();
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getModName() {
        return "OMTurrets";
    }
}
