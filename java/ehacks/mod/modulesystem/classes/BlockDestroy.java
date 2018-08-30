package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public class BlockDestroy
        extends Module {

    public static boolean isActive = false;

    public BlockDestroy() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "BlockDestroy";
    }

    @Override
    public String getDescription() {
        return "Destroy blocks instantly with a left click";
    }

    private Method snd;
    private Constructor msg;
    private Object obj;

    @Override
    public void onEnableMod() {
        try {
            msg = Class.forName("openmodularturrets.network.DropBaseMessage").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            msg.setAccessible(true);
            snd = Class.forName("cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.simpleimpl.IMessage"));
            snd.setAccessible(true);
            obj = Class.forName("openmodularturrets.ModularTurrets").getDeclaredField("networking").get(new Object[0]);
        } catch (Exception ex) {
            isActive = false;
            this.off();
        }

        isActive = true;
    }

    @Override
    public ModStatus getModStatus() {
        try {
            msg = Class.forName("openmodularturrets.network.DropBaseMessage").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            msg.setAccessible(true);
            snd = Class.forName("cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.simpleimpl.IMessage"));
            snd.setAccessible(true);
            obj = Class.forName("openmodularturrets.ModularTurrets").getDeclaredField("networking").get(new Object[0]);
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }
    
    private boolean prevState;
    
    @Override
    public void onTicks() {
        try {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            boolean nowState = Mouse.isButtonDown(0);
            if (position.sideHit != -1 && nowState && !prevState) {
                snd.invoke(obj, msg.newInstance(position.blockX, position.blockY, position.blockZ));
            }
            prevState = nowState;
        } catch (Exception e) {

        }
    }

    @Override
    public String getModName() {
        return "OMTurrets";
    }
}
