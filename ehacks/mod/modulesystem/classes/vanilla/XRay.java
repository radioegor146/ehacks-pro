package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
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
    public static int displayListId = 0;
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
    public void onModuleEnabled() {
        cooldownTicks = 0;
    }

    @Override
    public void onModuleDisabled() {
        if (displayListId != 0) {
            GL11.glDeleteLists(displayListId, 1);
        }
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Wrapper.INSTANCE.world() != null && displayListId != 0) {
            double doubleX = RenderManager.renderPosX;
            double doubleY = RenderManager.renderPosY;
            double doubleZ = RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glTranslated((-doubleX), (-doubleY), (-doubleZ));
            GL11.glCallList(displayListId);
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
        if (Wrapper.INSTANCE.world() != null && Wrapper.INSTANCE.player() != null) {
            if (displayListId == 0) {
                displayListId = GL11.glGenLists(5) + 3;
            }
            GL11.glNewList(displayListId, 4864);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glBegin(1);
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
                            XRayBlock block = (XRayBlock) block2;
                            if (!block.enabled || (Block.blockRegistry.getObject(block.id)) != bId || block.meta != -1 && block.meta != Wrapper.INSTANCE.world().getBlockMetadata(i, k, j)) {
                                continue;
                            }
                            GLUtils.renderBlock(i, k, j, block);
                            continue block2;
                        }
                    }
                }
            }
            GL11.glEnd();
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEndList();
        }
    }
}
