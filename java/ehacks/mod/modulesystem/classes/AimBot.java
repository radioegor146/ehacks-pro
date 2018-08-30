package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.config.CheatConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class AimBot
        extends Module {

    public static boolean isActive = false;
    private static final long time = 0L;

    public AimBot() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "AimBot";
    }

    @Override
    public String getDescription() {
        return "Automatically aims you";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    public static void faceEntity(Entity e) {
        double x = e.posX - Wrapper.INSTANCE.player().posX;
        double y = e.posY - Wrapper.INSTANCE.player().posY;
        double z = e.posZ - Wrapper.INSTANCE.player().posZ;
        double d1 = Wrapper.INSTANCE.player().posY + (double) Wrapper.INSTANCE.player().getEyeHeight() - (e.posY + (double) e.getEyeHeight());
        double d3 = MathHelper.sqrt_double((double) (x * x + z * z));
        float f = (float) (Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        float f1 = (float) (-Math.atan2(d1, d3) * 180.0 / 3.141592653589793);
        Wrapper.INSTANCE.player().setPositionAndRotation(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, f, -f1);
    }

    @Override
    public void onTicks() {
        block3:
        {
            try {
                if (KillAura.isActive || MobAura.isActive || ProphuntAura.isActive) {
                    break block3;
                }
                for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                    EntityPlayer e;
                    if (!(o instanceof EntityPlayer) || (e = (EntityPlayer) o) instanceof EntityPlayerSP || Wrapper.INSTANCE.player().getDistanceToEntity((Entity) e) > CheatConfiguration.config.aimbotdistance || e.isDead || !Wrapper.INSTANCE.player().canEntityBeSeen((Entity) e) || !e.isEntityAlive() || e.isDead) {
                        continue;
                    }
                    AimBot.faceEntity((Entity) e);
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
