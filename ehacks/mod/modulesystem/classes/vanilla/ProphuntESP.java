package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.axis.AltAxisAlignedBB;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class ProphuntESP
        extends Module {

    public ProphuntESP() {
        super(ModuleCategory.MINIGAMES);
    }

    @Override
    public String getName() {
        return "ProphuntESP";
    }

    @Override
    public String getDescription() {
        return "Prophunt ESP";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (this.isActive()) {
            Wrapper.INSTANCE.world().loadedEntityList.stream().filter((o) -> !(!(o instanceof EntityFallingBlock))).map((o) -> (EntityFallingBlock) o).map((e1) -> {
                Entity e = (Entity) e1;
                float halfWidth = e.width / 2.0f;
                AltAxisAlignedBB aaabb = AltAxisAlignedBB.getBoundingBox(e.width - halfWidth, e.height, e.width - halfWidth, e.width + halfWidth, e.height + e.height, e.width + halfWidth);
                double renderX = e.lastTickPosX + (e.posX - e.lastTickPosX) * event.partialTicks - RenderManager.renderPosX - e.width;
                double renderY = e.lastTickPosY + (e.posY - e.lastTickPosY) * event.partialTicks - RenderManager.renderPosY - e.height * 1.5;
                double renderZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.partialTicks - RenderManager.renderPosZ - e.width;
                GL11.glPushMatrix();
                GL11.glTranslated(renderX, renderY, renderZ);
                GL11.glColor4f(0.27f, 0.7f, 0.92f, 1.0f);
                return aaabb;
            }).map((aaabb) -> {
                GL11.glColor4f(0.92f, 0.2f, 0.2f, 1.0f);
                return aaabb;
            }).map((aaabb) -> {
                GLUtils.startDrawingESPs((AltAxisAlignedBB) aaabb, 0.27f, 0.7f, 0.5f);
                return aaabb;
            }).forEachOrdered((_item) -> {
                GL11.glPopMatrix();
            });
        }
    }
}
