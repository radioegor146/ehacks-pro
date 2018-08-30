package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTester
        extends Module {

    public ByteBufTester() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ByteBufTester";
    }

    @Override
    public void onEnableMod() {
        ByteBuf buf = Unpooled.buffer();
        this.off();
    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.WORKING;
    }

    @Override
    public String getModName() {
        return "Forge";
    }

    @Override
    public boolean shouldInclude() {
        return false;
    }
}
