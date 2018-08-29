package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.config.CheatConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class MobAura
        extends Module {

    public static boolean isActive = false;
    private long currentMS = 0L;
    private long lastMS = -1L;

    public MobAura() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "MobAura";
    }

    @Override
    public String getDescription() {
        return "Just mobaura";
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
                for (int i = 0; i < Wrapper.INSTANCE.world().loadedEntityList.size(); ++i) {
                    Entity e = (Entity) Wrapper.INSTANCE.world().getLoadedEntityList().get(i);
                    if (e == Wrapper.INSTANCE.player() || e.isDead || Wrapper.INSTANCE.player().getDistanceToEntity(e) >= CheatConfiguration.config.auraradius || !(e instanceof EntityLiving)) {
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
                        AimBot.faceEntity(e);
                    }
                    Wrapper.INSTANCE.player().setSprinting(false);
                    Wrapper.INSTANCE.player().swingItem();
                    Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer) Wrapper.INSTANCE.player(), e);
                    this.lastMS = System.nanoTime() / 980000L;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
