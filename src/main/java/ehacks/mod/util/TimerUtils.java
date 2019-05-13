package ehacks.mod.util;

import net.minecraft.util.Timer;

public class TimerUtils
        extends Timer {

    private static Timer timer;

    public TimerUtils(float p_i1018_1_) {
        super(p_i1018_1_);
        timer = this;
    }

    public static Timer getTimer() {
        return timer;
    }
}
