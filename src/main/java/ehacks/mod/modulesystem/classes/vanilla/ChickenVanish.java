package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.entity.passive.EntityChicken;
import org.lwjgl.opengl.GL11;

public class ChickenVanish
        extends Module {

    public ChickenVanish() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "ChickenVanish";
    }

    @Override
    public String getDescription() {
        return "Proof of concept. Traces a line to the chicken when detecting unexpected behaviour(when looking at a player). This can help see vanished players.";
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
            Wrapper.INSTANCE.world().loadedEntityList.stream().filter((entities) -> !(entities == Wrapper.INSTANCE.player() || !(entities instanceof EntityChicken) || ((Entity) entities).isDead || ((Entity)entities).rotationPitch == 0)).map((entities) -> (EntityChicken) entities).map((entity1) -> {
                Entity entity = (Entity) entity1;
                float distance = Wrapper.INSTANCE.mc().renderViewEntity.getDistanceToEntity(entity);
                double posX = entity.posX - RenderManager.renderPosX;
                double posY = entity.posY + (entity.height / 2.0f) - RenderManager.renderPosY;
                double posZ = entity.posZ - RenderManager.renderPosZ;
                if (distance > 7.0f) {
                    GL11.glColor3f(1.0f, 0.0f, 0.0f);
					GL11.glBegin(1);
					GL11.glVertex3d(0.0, 0.0, 0.0);
					GL11.glVertex3d(posX, posY, posZ);
                }
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
