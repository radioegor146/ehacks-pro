package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class XRay
        extends Module {

    public static int radius = 45;
    public static int displayListid = 0;
    public static int cooldownTicks = 0;

    public XRay() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "X-Ray";
    }

    @Override
    public String getDescription() {
        return "XRay";
    }

    @Override
    public void onEnableMod() {
        cooldownTicks = 0;
    }

    @Override
    public void onDisableMod() {
        GL11.glDeleteLists((int) displayListid, (int) 1);
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Wrapper.INSTANCE.world() != null) {
            double doubleX = RenderManager.renderPosX;
            double doubleY = RenderManager.renderPosY;
            double doubleZ = RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glTranslated((double) (-doubleX), (double) (-doubleY), (double) (-doubleZ));
            GL11.glCallList((int) displayListid);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onTicks() {
        if (cooldownTicks < 1) {
            this.compileDL();
            cooldownTicks = 80;
        }
        --cooldownTicks;
    }

    private void compileDL() {
        GL11.glNewList((int) displayListid, (int) 4864);
        GL11.glDisable((int) 3553);
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glBegin((int) 1);
        if (Wrapper.INSTANCE.world() != null && Wrapper.INSTANCE.player() != null) {
            for (int i = (int) Wrapper.INSTANCE.player().posX - XRay.radius; i <= (int) Wrapper.INSTANCE.player().posX + radius; ++i) {
                for (int j = (int) Wrapper.INSTANCE.player().posZ - XRay.radius; j <= (int) Wrapper.INSTANCE.player().posZ + radius; ++j) {
                    int height = Wrapper.INSTANCE.world().getHeightValue(i, j);
                    block2:
                    for (int k = 0; k <= height; ++k) {
                        Block bId = Wrapper.INSTANCE.world().getBlock(i, k, j);
                        if (bId == Blocks.air || bId == Blocks.stone) {
                            continue;
                        }
                        for (Object block2 : XRayBlock.blocks) {
                            Block blocki;
                            XRayBlock block = (XRayBlock) block2;
                            if (!block.enabled || (blocki = (Block) Block.blockRegistry.getObject(block.id)) != bId || block.meta != -1 && block.meta != Wrapper.INSTANCE.world().getBlockMetadata(i, k, j)) {
                                continue;
                            }
                            GLUtils.renderBlock(i, k, j, block);
                            continue block2;
                        }
                    }
                }
            }
            GL11.glEnd();
            GL11.glEnable((int) 2929);
            GL11.glDisable((int) 3042);
            GL11.glEnable((int) 3553);
            GL11.glEndList();
        }
    }
}
