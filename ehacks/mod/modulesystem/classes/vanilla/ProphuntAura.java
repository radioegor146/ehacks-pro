package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

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
    public void onModuleEnabled() {
        isActive = true;
    }

    @Override
    public void onModuleDisabled() {
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
                    if (Wrapper.INSTANCE.player().getDistanceToEntity(e) > 6 || e.isDead) {
                        continue;
                    }
                    if (AutoBlock.isActive && Wrapper.INSTANCE.player().getCurrentEquippedItem() != null && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
                        lel.useItemRightClick(Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player());
                    }
                    if (Criticals.isActive && !Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) && Wrapper.INSTANCE.player().onGround) {
                        Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
                        Wrapper.INSTANCE.player().fallDistance = 0.1f;
                        Wrapper.INSTANCE.player().onGround = false;
                    }
                    if (AimBot.isActive) {
                        AimBot.faceEntity(e);
                    }
                    Wrapper.INSTANCE.player().setSprinting(false);
                    Wrapper.INSTANCE.player().swingItem();
                    Wrapper.INSTANCE.mc().playerController.attackEntity(Wrapper.INSTANCE.player(), e);
                    this.lastMS = System.nanoTime() / 980000L;
                    break;
                }
            } catch (Exception e) {
            }
        }
    }
}
