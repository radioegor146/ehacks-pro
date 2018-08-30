package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.axis.AltAxisAlignedBB;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class ChestFinder
        extends Module {

    public ChestFinder() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "ChestFinder";
    }

    @Override
    public String getDescription() {
        return "Shows you all chests";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        for (Object o : Wrapper.INSTANCE.world().loadedTileEntityList) {
            double renderY;
            AltAxisAlignedBB boundingBox;
            double renderX;
            TileEntityChest chest;
            double renderZ;
            if (o instanceof TileEntityChest) {
                chest = (TileEntityChest) o;
                renderX = (double) chest.xCoord - RenderManager.renderPosX;
                renderY = (double) chest.yCoord - RenderManager.renderPosY;
                renderZ = (double) chest.zCoord - RenderManager.renderPosZ;
                GL11.glPushMatrix();
                GL11.glTranslated((double) renderX, (double) renderY, (double) renderZ);
                GL11.glColor3f((float) 1.0f, (float) 1.0f, (float) 0.0f);
                if (chest.adjacentChestXPos != null) {
                    boundingBox = AltAxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 2.0, 1.0, 1.0);
                    GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 0.0f, (float) 0.1f);
                    GLUtils.startDrawingESPs(boundingBox, 0.3f, 0.8f, 1.0f);
                } else if (chest.adjacentChestZPos != null) {
                    boundingBox = AltAxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 2.0);
                    GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 0.0f, (float) 0.1f);
                    GLUtils.startDrawingESPs(boundingBox, 0.3f, 0.8f, 1.0f);
                } else if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null && chest.adjacentChestXNeg == null && chest.adjacentChestZNeg == null) {
                    boundingBox = AltAxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                    GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 0.0f, (float) 0.1f);
                    GLUtils.startDrawingESPs(boundingBox, 0.3f, 0.8f, 1.0f);
                }
                GL11.glPopMatrix();
                continue;
            }
            if (!(o instanceof TileEntityEnderChest)) {
                continue;
            }
            TileEntityEnderChest chestEnder = (TileEntityEnderChest) o;
            renderX = (double) chestEnder.xCoord - RenderManager.renderPosX;
            renderY = (double) chestEnder.yCoord - RenderManager.renderPosY;
            renderZ = (double) chestEnder.zCoord - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glTranslated((double) renderX, (double) renderY, (double) renderZ);
            GL11.glColor3f((float) 1.0f, (float) 1.0f, (float) 0.0f);
            boundingBox = AltAxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 0.0f, (float) 0.1f);
            GLUtils.startDrawingESPs(boundingBox, 0.3f, 0.0f, 0.5f);
            GL11.glPopMatrix();
        }
    }
}
