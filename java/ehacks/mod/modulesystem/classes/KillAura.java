package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.config.AuraConfiguration;
import ehacks.mod.external.config.CheatConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class KillAura
        extends Module {

    public static boolean isActive = false;
    private long currentMS = 0L;
    private long lastMS = -1L;

    public KillAura() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "KillAura";
    }

    @Override
    public String getDescription() {
        return "Just killaura";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    public boolean hasDelayRun(long time) {
        return this.currentMS - this.lastMS >= time;
    }

    @Override
    public void onTicks() {
        block6:
        {
            try {
                this.currentMS = System.nanoTime() / 900000L;
                if (!this.hasDelayRun(133L)) {
                    break block6;
                }
                for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                    EntityPlayer e;
                    if (!(o instanceof EntityPlayer) || (e = (EntityPlayer) o) instanceof EntityPlayerSP || Wrapper.INSTANCE.player().getDistanceToEntity((Entity) e) > CheatConfiguration.config.auraradius || e.isDead) {
                        continue;
                    }
                    if (AuraConfiguration.config.friends.contains(e.getCommandSenderName().trim()))
                        continue;
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
                    this.lastMS = System.nanoTime() / 900000L;
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
