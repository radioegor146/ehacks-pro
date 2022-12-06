package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.config.CheatConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.material.Material;
import ehacks.mod.wrapper.Statics;
import net.minecraft.util.Vec3;
import net.minecraft.item.Item;
import ehacks.mod.gui.xraysettings.XRayBlock;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class ClickBlock
        extends Module {

    public static boolean isActive = false;

    public ClickBlock() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "ClickBlock";
    }

    @Override
    public String getDescription() {
        return "Right click selected blocks. Select required blocks with XRay settings.";
    }

    @Override
    public void onModuleEnabled() {
        isActive = true;
    }

    @Override
    public void onModuleDisabled() {
        isActive = false;
    }

    @Override
    public void onTicks() {
        int radius = CheatConfiguration.config.nukerradius;
        for (int i = radius; i >= -radius; --i) {
            for (int k = radius; k >= -radius; --k) {
				block2:
                for (int j = -radius; j <= radius; ++j) {
                    int x = (int) (Wrapper.INSTANCE.player().posX + i);
                    int y = (int) (Wrapper.INSTANCE.player().posY + j);
                    int z = (int) (Wrapper.INSTANCE.player().posZ + k);
                    Block blockID = Wrapper.INSTANCE.world().getBlock(x, y, z);
					for (Object block2 : XRayBlock.blocks) {
                        XRayBlock block = (XRayBlock) block2;
                        if (!block.enabled || (Block.blockRegistry.getObject(block.id)) != blockID || block.meta != -1 && block.meta != Wrapper.INSTANCE.world().getBlockMetadata(x, y, z)) {
                            continue;
                        }
                        Wrapper.INSTANCE.mc().playerController.onPlayerRightClick(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getHeldItem(), x, y, z, 0, Vec3.createVectorHelper(0, 0, 0));
                        continue block2;
                    }
                }
            }
        }
    }
}
