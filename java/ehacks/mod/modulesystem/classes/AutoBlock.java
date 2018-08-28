package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class AutoBlock
        extends Module {

    public static boolean isActive = false;

    public AutoBlock() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "AutoBlock";
    }

    @Override
    public String getDescription() {
        return "Your sword will be blocking damage while using it";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    @Override
    public void onTicks() {
        if (!(KillAura.isActive || MobAura.isActive || ProphuntAura.isActive || Forcefield.isActive || TriggerBot.isActive || !Wrapper.INSTANCE.mcSettings().keyBindAttack.getIsKeyPressed() || Wrapper.INSTANCE.player().getCurrentEquippedItem() == null || !(Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword))) {
            ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
            lel.useItemRightClick((World) Wrapper.INSTANCE.world(), (EntityPlayer) Wrapper.INSTANCE.player());
        }
    }
}
