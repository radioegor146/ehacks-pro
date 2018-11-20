package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class Breadcrumb
        extends Module {

    public static CopyOnWriteArrayList<double[]> positionsList = new CopyOnWriteArrayList<>();
    static int count = 0;

    public Breadcrumb() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "Breadcrumb";
    }

    @Override
    public String getDescription() {
        return "Draws a line behind you";
    }

    @Override
    public void onTicks() {
        if (this.isActive()) {
            if (++count >= 50) {
                count = 0;
                if (positionsList.size() > 5) {
                    positionsList.remove(0);
                }
            }
            Wrapper.INSTANCE.world().playerEntities.forEach((o) -> {
                EntityPlayer player1;
                if (!(!(o instanceof EntityPlayer) || !((player1 = (EntityPlayer) o) == Wrapper.INSTANCE.player() && (Wrapper.INSTANCE.player().movementInput.moveForward != 0.0f || Wrapper.INSTANCE.player().movementInput.moveStrafe != 0.0f)))) {
                    double x = RenderManager.renderPosX;
                    double y = RenderManager.renderPosY;
                    double z = RenderManager.renderPosZ;
                    positionsList.add(new double[]{x, y - player1.height, z});
                }
            });
        }
    }

    private static double posit(double val) {
        return val == 0.0 ? val : (val < 0.0 ? val * -1.0 : val);
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        GL11.glPushMatrix();
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glBegin(3);
        positionsList.forEach((pos) -> {
            double distance = Breadcrumb.posit(Math.hypot(pos[0] - RenderManager.renderPosX, pos[1] - RenderManager.renderPosY));
            if (!(distance > 100.0)) {
                GL11.glColor4f(0.0f, 1.0f, 0.0f, (1.0f - (float) (distance / 100.0)));
                GL11.glVertex3d((pos[0] - RenderManager.renderPosX), (pos[1] - RenderManager.renderPosY), (pos[2] - RenderManager.renderPosZ));
            }
        });
        GL11.glEnd();
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }
}
