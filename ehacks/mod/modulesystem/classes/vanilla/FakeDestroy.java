package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class FakeDestroy
        extends Module {

    public FakeDestroy() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "FakeDestroy";
    }

    @Override
    public String getDescription() {
        return "Destroy blocks on client with left click";
    }

    private ArrayList<BlockInfo> removedBlocks = new ArrayList<>();

    private class BlockInfo {

        public int[] coords;
        public Block block;
        public int meta;

        public BlockInfo(int[] coords, Block block, int meta) {
            this.coords = coords;
            this.block = block;
            this.meta = meta;
        }
    }

    private boolean prevState = false;

    @Override
    public void onMouse(MouseEvent event) {
        try {
            boolean nowState = Mouse.isButtonDown(0);
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            if (position.sideHit != -1 && !prevState && nowState) {
                removedBlocks.add(new BlockInfo(new int[]{position.blockX, position.blockY, position.blockZ}, Wrapper.INSTANCE.world().getBlock(position.blockX, position.blockY, position.blockZ), Wrapper.INSTANCE.world().getBlockMetadata(position.blockX, position.blockY, position.blockZ)));
                Wrapper.INSTANCE.world().setBlockToAir(position.blockX, position.blockY, position.blockZ);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            prevState = nowState;
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onModuleEnabled() {
        removedBlocks = new ArrayList<>();
    }

    @Override
    public void onModuleDisabled() {
        removedBlocks.forEach((removedBlock) -> {
            Wrapper.INSTANCE.world().setBlock(removedBlock.coords[0], removedBlock.coords[1], removedBlock.coords[2], removedBlock.block, removedBlock.meta, 3);
        });
    }
}
