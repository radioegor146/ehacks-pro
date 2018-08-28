package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class Forcefield
        extends Module {

    public static boolean isActive = false;

    public Forcefield() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "Forcefield";
    }

    @Override
    public String getDescription() {
        return "Attacks all entities around you";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    private void hitEntity(Entity e, boolean block, boolean criticals, boolean aimbot, boolean auto) {
        if (block && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
            ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
            lel.useItemRightClick((World) Wrapper.INSTANCE.world(), (EntityPlayer) Wrapper.INSTANCE.player());
        }
        if (criticals && !Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) && Wrapper.INSTANCE.player().onGround) {
            Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
            Wrapper.INSTANCE.player().fallDistance = 0.1f;
            Wrapper.INSTANCE.player().onGround = false;
        }
        if (aimbot) {
            AimBot.faceEntity(e);
        }
        Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer) Wrapper.INSTANCE.player(), e);
        Wrapper.INSTANCE.player().swingItem();
    }

    private float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    private boolean isWithinRange(float range, Entity e) {
        return this.getDistanceToEntity(e, (Entity) Wrapper.INSTANCE.player()) <= range;
    }

    @Override
    public void onTicks() {
        try {
            for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                EntityLivingBase entity = null;
                if (o instanceof EntityLivingBase) {
                    entity = (EntityLivingBase) o;
                }
                if (entity == null || !this.isWithinRange(6, (Entity) entity) || entity.isDead || entity == Wrapper.INSTANCE.player()) {
                    continue;
                }
                this.hitEntity((Entity) entity, AutoBlock.isActive, Criticals.isActive, AimBot.isActive, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
