package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;

import java.lang.reflect.Constructor;

public class MCFMDoorMat extends Module {
    private Constructor doorMatGuiConstructor;

    public MCFMDoorMat() {
        super(ModuleCategory.BEUHACKS);
    }

    @Override
    public String getName() {
        return "MCFMDoorMat";
    }

    @Override
    public String getDescription() {
        return "Change text on door mats by clicking RMB.";
    }

    @Override
    public ModStatus getModStatus() {
        try {
            this.doorMatGuiConstructor = Class.forName("com.mrcrayfish.furniture.gui.GuiDoorMat").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            return ModStatus.WORKING;
        } catch (Exception ex) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public String getModName() {
        return "MCFM";
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }

    @Override
    public void onMouse(MouseEvent event) {
        RayTraceResult rtx = Wrapper.INSTANCE.mc().objectMouseOver; // Да да, это топовое название
        TileEntity tile = Wrapper.INSTANCE.world().getTileEntity(rtx.getBlockPos());

        try {
            if (event.getButton() != 1
                    || tile == null
                    || !Class.forName("com.mrcrayfish.furniture.tileentity.TileEntityDoorMat").isInstance(tile)) {
                return;
            }

            int tileX = tile.getPos().getX();
            int tileY = tile.getPos().getY();
            int tileZ = tile.getPos().getZ();

            GuiScreen doorMatGui = (GuiScreen) this.doorMatGuiConstructor.newInstance(tileX, tileY, tileZ);

            Wrapper.INSTANCE.mc().displayGuiScreen(doorMatGui);

            if (event.isCancelable()) {
                event.setCanceled(true);
            }
        } catch (Exception e) {
            // Аксель, го в раст!
            InteropUtils.log("Error on trying to open DoorMat GUI.", getName());
        }
    }
}