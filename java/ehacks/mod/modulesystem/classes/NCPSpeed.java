package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemFood;
import net.minecraft.util.AxisAlignedBB;

public class NCPSpeed
        extends Module {

    private final double motionSpeed = 3.14;
    private final float timerSpeed = 1.1f;
    private final boolean iceSpeed = true;
    private float ground = 0.0f;
    private int motionDelay;
    private boolean canStep;

    public NCPSpeed() {
        super(ModuleCategory.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "NCPSpeed";
    }

    @Override
    public String getDescription() {
        return "Speed from NoCheatPlus";
    }

    @Override
    public void onTicks() {
        boolean using = Wrapper.INSTANCE.player().isUsingItem() && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemFood;
        double speed = this.motionSpeed;
        double slow = 1.458;
        double offset = 4.7;
        float timer = Float.valueOf(this.timerSpeed).floatValue();
        boolean shouldOffset = true;
        this.canStep = false;
        for (Object o : Wrapper.INSTANCE.world().getCollidingBoundingBoxes((Entity) Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().boundingBox.copy().offset(Wrapper.INSTANCE.player().motionX / offset, 0.0, Wrapper.INSTANCE.player().motionZ / offset))) {
            if (o == null || !(o instanceof AxisAlignedBB)) {
                continue;
            }
            shouldOffset = false;
            break;
        }
        if (Wrapper.INSTANCE.mcSettings().keyBindForward.isPressed() || Wrapper.INSTANCE.mcSettings().keyBindBack.isPressed() || Wrapper.INSTANCE.mcSettings().keyBindLeft.isPressed() || Wrapper.INSTANCE.mcSettings().keyBindRight.isPressed()) {
            if (Wrapper.INSTANCE.player().onGround && this.ground < 1.0f) {
                this.ground += 0.2f;
            }
            if (!Wrapper.INSTANCE.player().onGround) {
                this.ground = 0.0f;
            }
            if (this.ground == 1.0f) {
                if (!Wrapper.INSTANCE.player().isSprinting()) {
                    offset += 0.8;
                }
                if (Wrapper.INSTANCE.player().moveStrafing != 0.0f) {
                    speed -= 0.1;
                    offset += 0.5;
                }
                if (Wrapper.INSTANCE.player().isInWater()) {
                    speed -= 0.1;
                }
                ++this.motionDelay;
                switch (this.motionDelay) {
                    case 1:
                        TimerUtils.getTimer().timerSpeed = timer;
                        Wrapper.INSTANCE.player().motionX *= speed;
                        Wrapper.INSTANCE.player().motionZ *= speed;
                        this.canStep = false;
                        break;
                    case 2:
                        Wrapper.INSTANCE.player().motionX /= slow;
                        Wrapper.INSTANCE.player().motionZ /= slow;
                        this.canStep = true;
                        break;
                    case 3:
                        if ((double) timer > 1.05) {
                            TimerUtils.getTimer().timerSpeed = 1.05f;
                        }
                        this.canStep = true;
                        break;
                    case 4:
                        if (shouldOffset) {
                            Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX + Wrapper.INSTANCE.player().motionX / offset, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ + Wrapper.INSTANCE.player().motionZ / offset);
                            this.canStep = false;
                        }
                        this.motionDelay = 0;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
