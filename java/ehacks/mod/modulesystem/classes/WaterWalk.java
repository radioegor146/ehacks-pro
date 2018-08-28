package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class WaterWalk
        extends Module {

    private static boolean overwater;
    private int delay;

    public WaterWalk() {
        super(ModuleCategory.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "WaterWalk";
    }

    @Override
    public String getDescription() {
        return "Gives ability to walk on water";
    }

    public static boolean isOnLiquid(AxisAlignedBB boundingBox) {
        boundingBox = boundingBox.contract(0.01, 0.0, 0.01).offset(0.0, -0.01, 0.0);
        boolean onLiquid = false;
        int y = (int) boundingBox.minY;
        for (int x = MathHelper.floor_double((double) boundingBox.minX); x < MathHelper.floor_double((double) (boundingBox.maxX + 1.0)); ++x) {
            for (int z = MathHelper.floor_double((double) boundingBox.minZ); z < MathHelper.floor_double((double) (boundingBox.maxZ + 1.0)); ++z) {
                Block block = Wrapper.INSTANCE.world().getBlock(x, y, z);
                if (block == Blocks.air) {
                    continue;
                }
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                onLiquid = true;
            }
        }
        return onLiquid;
    }

    private static boolean isInLiquid() {
        AxisAlignedBB par1AxisAlignedBB = Wrapper.INSTANCE.player().boundingBox.contract(0.001, 0.001, 0.001);
        int minX = MathHelper.floor_double((double) par1AxisAlignedBB.minX);
        int maxX = MathHelper.floor_double((double) (par1AxisAlignedBB.maxX + 1.0));
        int minY = MathHelper.floor_double((double) par1AxisAlignedBB.minY);
        int maxY = MathHelper.floor_double((double) (par1AxisAlignedBB.maxY + 1.0));
        int minZ = MathHelper.floor_double((double) par1AxisAlignedBB.minZ);
        int maxZ = MathHelper.floor_double((double) (par1AxisAlignedBB.maxZ + 1.0));
        if (!Wrapper.INSTANCE.world().checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
            return false;
        }
        Vec3 vec = Vec3.createVectorHelper((double) 0.0, (double) 0.0, (double) 0.0);
        for (int X = minX; X < maxX; ++X) {
            for (int Y = minY; Y < maxY; ++Y) {
                for (int Z = minZ; Z < maxZ; ++Z) {
                    Block block = Wrapper.INSTANCE.world().getBlock(X, Y, Z);
                    if (!(block instanceof BlockLiquid)) {
                        continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onTicks() {
        if (WaterWalk.isOnLiquid(Wrapper.INSTANCE.player().boundingBox)) {
            ++this.delay;
            if (this.delay == 4) {
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().boundingBox.minY - 0.02, Wrapper.INSTANCE.player().posY - 0.02, Wrapper.INSTANCE.player().posZ, false));
                this.delay = 0;
            }
        }
        if (WaterWalk.isInLiquid()) {
            Wrapper.INSTANCE.player().motionY = 0.085;
        }
        Block Blocks2 = Wrapper.INSTANCE.world().getBlock((int) Wrapper.INSTANCE.player().posX, (int) Wrapper.INSTANCE.player().posY - 2, (int) Wrapper.INSTANCE.player().posZ);
        Block lel = Wrapper.INSTANCE.world().getBlock((int) Wrapper.INSTANCE.player().posX, (int) Wrapper.INSTANCE.player().posY + 1, (int) Wrapper.INSTANCE.player().posZ);
        if (Blocks2 instanceof BlockLiquid) {
            overwater = true;
        } else if (!(Blocks2 instanceof BlockLiquid)) {
            overwater = false;
        } else if (lel instanceof BlockLiquid || Blocks2 instanceof BlockLiquid || !(Blocks2 instanceof BlockLiquid)) {
            // empty if block
        }
    }
}
