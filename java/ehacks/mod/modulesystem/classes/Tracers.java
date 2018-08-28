package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
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
    public void onDisableMod() {
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        try {
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glEnable((int) 3042);
            GL11.glLineWidth((float) 2.0f);
            GL11.glDisable((int) 3553);
            GL11.glDisable((int) 2929);
            GL11.glDepthMask((boolean) false);
            for (Object entities : Wrapper.INSTANCE.world().loadedEntityList) {
                if (entities == Wrapper.INSTANCE.player() || entities == null || !(entities instanceof EntityPlayer) || (entities instanceof EntityFakePlayer) || ((EntityPlayer) entities).isDead || ((EntityPlayer) entities).isInvisible()) {
                    continue;
                }
                EntityPlayer entity = (EntityPlayer) entities;
                float distance = Wrapper.INSTANCE.mc().renderViewEntity.getDistanceToEntity((Entity) entity);
                double posX = entity.posX - RenderManager.renderPosX;
                double posY = entity.posY + (double) (entity.height / 2.0f) - RenderManager.renderPosY;
                double posZ = entity.posZ - RenderManager.renderPosZ;
                String playerName = Wrapper.INSTANCE.player().getGameProfile().getName();
                if (distance <= 6.0f) {
                    GL11.glColor3f((float) 1.0f, (float) 0.0f, (float) 0.0f);
                } else if (distance <= 96.0f) {
                    GL11.glColor3f((float) 1.0f, (float) (distance / 100.0f), (float) 0.0f);
                } else if (distance > 96.0f) {
                    GL11.glColor3f((float) 0.1f, (float) 0.6f, (float) 255.0f);
                }
                GL11.glBegin((int) 1);
                GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
                GL11.glVertex3d((double) posX, (double) posY, (double) posZ);
                GL11.glEnd();
            }
            GL11.glEnable((int) 3553);
            GL11.glEnable((int) 2929);
            GL11.glDepthMask((boolean) true);
            GL11.glDisable((int) 3042);
        } catch (Exception exception) {
            // empty catch block
        }
    }
}
