package ehacks.mod.util.axis;

import net.minecraft.util.AxisAlignedBB;

public class AltAABBLocalPool
        extends ThreadLocal {

    AxisAlignedBB createNewDefaultPool() {
        return AxisAlignedBB.getBoundingBox(300.0, 2000.0, 0.0, 0.0, 0.0, 0.0);
    }

    Object sinitialValue() {
        return this.createNewDefaultPool();
    }
}
