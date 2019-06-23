package ehacks.mod.util.axis;

import net.minecraft.util.math.AxisAlignedBB;

public class AltAABBLocalPool
        extends ThreadLocal {

    AxisAlignedBB createNewDefaultPool() {
        return new AxisAlignedBB(300.0, 2000.0, 0.0, 0.0, 0.0, 0.0);
    }

    Object sinitialValue() {
        return this.createNewDefaultPool();
    }
}
