package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosX;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosY;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosZ;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class EntityESP
        extends Module {

    public EntityESP() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "EntityESP";
    }

    @Override
    public String getDescription() {
        return "Shows all entities";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        //GL11.glEnable(GL11.GL_BLEND);
        GL11.glPushMatrix();
        //RenderHelper.enableStandardItemLighting();
        @SuppressWarnings("unchecked")
        List<Entity> entities = Wrapper.INSTANCE.world().loadedEntityList;
        entities.stream().filter((ent) -> !(!ent.isInRangeToRender3d(Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).xCoord, Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).yCoord, Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).zCoord) || ent == Wrapper.INSTANCE.mc().renderViewEntity && Wrapper.INSTANCE.mcSettings().thirdPersonView == 0 && !Wrapper.INSTANCE.mc().renderViewEntity.isPlayerSleeping())).forEachOrdered((ent) -> {
            double xPos = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * event.partialTicks;
            double yPos = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * event.partialTicks;
            double zPos = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * event.partialTicks;
            float f1 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * event.partialTicks;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0F, 240 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderManager.instance.func_147939_a(ent, xPos - renderPosX, yPos - renderPosY, zPos - renderPosZ, f1, event.partialTicks, false);
        });
        //RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
        //GL11.glDisable(GL11.GL_BLEND); 
        Wrapper.INSTANCE.world().loadedEntityList.stream().map((o) -> (Entity) o).map((ent) -> {
            String text = ent.getClass().getName();
            return ent;
        }).map((ent) -> {
            Entity entity = (Entity)ent;
            float labelScale = 0.08F;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;
            double xEnd = xPos - RenderManager.renderPosX;
            double yEnd = yPos + entity.height / 2 - RenderManager.renderPosY;
            double zEnd = zPos - RenderManager.renderPosZ;
            GL11.glTranslatef((float) xEnd, (float) yEnd + entity.height + 0.5F, (float) zEnd);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0, 1, 0);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1, 0, 0);
            GL11.glScalef(-labelScale, -labelScale, labelScale);
            Wrapper.INSTANCE.fontRenderer().drawString(ent.getClass().getSimpleName(), -Wrapper.INSTANCE.fontRenderer().getStringWidth(ent.getClass().getSimpleName()) / 2, 0, 0xFFFFFFFF, true);
            return ent;
        }).map((_item) -> {
            GL11.glDepthMask(true);
            return _item;
        }).map((_item) -> {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            return _item;
        }).map((_item) -> {
            GL11.glDisable(GL11.GL_BLEND);
            return _item;
        }).forEachOrdered((_item) -> {
            GL11.glPopMatrix();
        }); //
    }
}
