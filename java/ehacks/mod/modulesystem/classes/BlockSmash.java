package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class BlockSmash
        extends Module {

    public BlockSmash() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "BlockSmash";
    }

    @Override
    public String getDescription() {
        return "Plays blocksmash effect";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().fallDistance = 100.0f;
        int x = MathHelper.floor_double((double) Wrapper.INSTANCE.player().posX);
        int y = MathHelper.floor_double((double) (Wrapper.INSTANCE.player().posY - 0.20000000298023224 - (double) Wrapper.INSTANCE.player().yOffset));
        int z = MathHelper.floor_double((double) Wrapper.INSTANCE.player().posZ);
        Block block = Wrapper.INSTANCE.player().worldObj.getBlock(x, y - 1, z);
        Wrapper.INSTANCE.player().worldObj.playAuxSFX(2006, x, y, z, MathHelper.ceiling_float_int((float) (Wrapper.INSTANCE.player().fallDistance - 3.0f)));
        block.onFallenUpon(Wrapper.INSTANCE.player().worldObj, x, y, z, (Entity) Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().fallDistance);
    }
}
