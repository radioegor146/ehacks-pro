package ehacks.mod.modulesystem.classes.mods.ironchest;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.axis.AltAxisAlignedBB;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class IronChestFinder
        extends Module {

    public IronChestFinder() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "IronChestFinder";
    }

    @Override
    public String getModName() {
        return "IronChest";
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("cpw.mods.ironchest.TileEntityIronChest");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }
    
    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("cpw.mods.ironchest.TileEntityIronChest");
        } catch (Exception e) {
            this.off();
        }
    }
    
    @Override
    public String getDescription() {
        return "Shows all chests from IronChests mod around you";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        try {
            for (Object o : Wrapper.INSTANCE.world().loadedTileEntityList) {
                if (Class.forName("cpw.mods.ironchest.TileEntityIronChest").isInstance(o)) {
                    TileEntity chest = (TileEntity)o;
                    double renderX = chest.xCoord - RenderManager.renderPosX;
                    double renderY = chest.yCoord - RenderManager.renderPosY;
                    double renderZ = chest.zCoord - RenderManager.renderPosZ;
                    GL11.glPushMatrix();
                    GL11.glTranslated(renderX, renderY, renderZ);
                    GL11.glColor3f(1.0f, 1.0f, 0.0f);
                    AltAxisAlignedBB boundingBox = AltAxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                    GL11.glColor4f(0.0f, 1.0f, 1.0f, 0.1f);
                    GLUtils.startDrawingESPs(boundingBox, 0.3f, 0.8f, 1.0f);
                    GL11.glPopMatrix();
                }
            }
        } catch (Exception e) {
            
        }
    }
}
