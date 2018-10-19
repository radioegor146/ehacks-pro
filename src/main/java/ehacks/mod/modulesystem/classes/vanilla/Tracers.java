package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class Tracers
        extends Module {

    public Tracers() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "Tracers";
    }

    @Override
    public String getDescription() {
        return "Traces a line to the players in MP";
    }

    @Override
    public void onModuleDisabled() {
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        try {
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            Wrapper.INSTANCE.world().loadedEntityList.stream().filter((entities) -> !(entities == Wrapper.INSTANCE.player() || !(entities instanceof EntityPlayer) || (entities instanceof EntityFakePlayer) || ((Entity) entities).isDead || ((EntityPlayer) entities).isInvisible())).map((entities) -> (EntityPlayer) entities).map((entity1) -> {
                Entity entity = (Entity)entity1;
                float distance = Wrapper.INSTANCE.mc().renderViewEntity.getDistanceToEntity(entity);
                double posX = entity.posX - RenderManager.renderPosX;
                double posY = entity.posY + (entity.height / 2.0f) - RenderManager.renderPosY;
                double posZ = entity.posZ - RenderManager.renderPosZ;
                String playerName = Wrapper.INSTANCE.player().getGameProfile().getName();
                if (distance <= 6.0f) {
                    GL11.glColor3f(1.0f, 0.0f, 0.0f);
                } else if (distance <= 96.0f) {
                    GL11.glColor3f(1.0f, (distance / 100.0f), 0.0f);
                } else if (distance > 96.0f) {
                    GL11.glColor3f(0.1f, 0.6f, 255.0f);
                }
                GL11.glBegin(1);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(posX, posY, posZ);
                return entity;
            }).forEachOrdered((_item) -> {
                GL11.glEnd();
            });
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        } catch (Exception exception) {
            // empty catch block
        }
    }
}
