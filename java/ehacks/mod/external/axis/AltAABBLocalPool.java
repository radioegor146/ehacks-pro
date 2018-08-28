package ehacks.mod.external.axis;

import net.minecraft.util.AxisAlignedBB;

public final class AltAABBLocalPool
        extends ThreadLocal {

    AxisAlignedBB createNewDefaultPool() {
        return AxisAlignedBB.getBoundingBox((double) 300.0, (double) 2000.0, (double) 0.0, (double) 0.0, (double) 0.0, (double) 0.0);
    }

    Object sinitialValue() {
        return this.createNewDefaultPool();
    }
}
