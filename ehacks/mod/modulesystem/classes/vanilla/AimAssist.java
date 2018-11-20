package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.config.AuraConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class AimAssist
        extends Module {

    public static boolean isActive = false;
    private final double range = 3.8;
    private EntityPlayer curtarget;
    private final List<EntityPlayer> targetlist = new ArrayList<>();

    public AimAssist() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "AimAssist";
    }

    @Override
    public String getDescription() {
        return "Helps you aim at a player";
    }

    @Override
    public void onModuleEnabled() {
        this.curtarget = null;
    }

    @Override
    public void onTicks() {
        Entity entity;
        this.targetlist.clear();
        Wrapper.INSTANCE.world().playerEntities.stream().filter((e) -> !(!this.isAttackable((Entity) e) || (AuraConfiguration.config.friends.contains(((Entity) e).getCommandSenderName())))).forEachOrdered((e) -> {
            this.targetlist.add((EntityPlayer) e);
        });
        if (Wrapper.INSTANCE.mc().objectMouseOver == null) {
            return;
        }
        if (Wrapper.INSTANCE.mc().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && (entity = Wrapper.INSTANCE.mc().objectMouseOver.entityHit) instanceof EntityPlayer) {
            this.curtarget = (EntityPlayer) entity;
            return;
        }
        if (!this.targetlist.contains(this.curtarget) && this.curtarget != null) {
            this.curtarget = null;
            return;
        }
        Random r = new Random();
        if (this.curtarget == null) {
            return;
        }
        Wrapper.INSTANCE.player().rotationYaw = (float) (Wrapper.INSTANCE.player().rotationYaw - (Wrapper.INSTANCE.player().rotationYaw - this.getAngles(this.curtarget)[0]) * 0.5);
        Wrapper.INSTANCE.player().rotationPitch = (float) (Wrapper.INSTANCE.player().rotationPitch - (Wrapper.INSTANCE.player().rotationPitch - this.getAngles(this.curtarget)[1]) * 0.5);
    }

    private float[] getAngles(Entity entity) {
        float xDiff = (float) (entity.posX - Wrapper.INSTANCE.player().posX);
        float yDiff = (float) (entity.boundingBox.minY + entity.getEyeHeight() - Wrapper.INSTANCE.player().boundingBox.maxY);
        float zDiff = (float) (entity.posZ - Wrapper.INSTANCE.player().posZ);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793 - 90.0);
        float pitch = (float) (-Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff))));
        return new float[]{yaw, pitch};
    }

    private boolean isAttackable(Entity e) {
        if (e == null) {
            return false;
        }
        if (e instanceof EntityPlayer) {
            EntityPlayer p2 = (EntityPlayer) e;
            return !p2.isDead && !p2.isInvisible() && Wrapper.INSTANCE.player().getDistanceToEntity(p2) <= this.range && Wrapper.INSTANCE.player().canEntityBeSeen(p2) && p2 != Wrapper.INSTANCE.player();
        }
        return false;
    }
}
