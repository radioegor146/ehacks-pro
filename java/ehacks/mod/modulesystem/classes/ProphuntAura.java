package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ProphuntAura
        extends Module {

    public static boolean isActive = false;
    private long currentMS = 0L;
    private long lastMS = -1L;

    public ProphuntAura() {
        super(ModuleCategory.MINIGAMES);
    }

    @Override
    public String getName() {
        return "ProphuntAura";
    }

    @Override
    public String getDescription() {
        return "Prophunt aura";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    private boolean hasDelayRun(long time) {
        return this.currentMS - this.lastMS >= time;
    }

    @Override
    public void onTicks() {
        block6:
        {
            try {
                this.currentMS = System.nanoTime() / 980000L;
                if (!this.hasDelayRun(133L)) {
                    break block6;
                }
                for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                    if (!(o instanceof EntityFallingBlock)) {
                        continue;
                    }
                    EntityFallingBlock e = (EntityFallingBlock) o;
                    if (Wrapper.INSTANCE.player().getDistanceToEntity((Entity) e) > 6 || e.isDead) {
                        continue;
                    }
                    if (AutoBlock.isActive && Wrapper.INSTANCE.player().getCurrentEquippedItem() != null && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
                        lel.useItemRightClick((World) Wrapper.INSTANCE.world(), (EntityPlayer) Wrapper.INSTANCE.player());
                    }
                    if (Criticals.isActive && !Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) && Wrapper.INSTANCE.player().onGround) {
                        Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
                        Wrapper.INSTANCE.player().fallDistance = 0.1f;
                        Wrapper.INSTANCE.player().onGround = false;
                    }
                    if (AimBot.isActive) {
                        AimBot.faceEntity((Entity) e);
                    }
                    Wrapper.INSTANCE.player().setSprinting(false);
                    Wrapper.INSTANCE.player().swingItem();
                    Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer) Wrapper.INSTANCE.player(), (Entity) e);
                    this.lastMS = System.nanoTime() / 980000L;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
