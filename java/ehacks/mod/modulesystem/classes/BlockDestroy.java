/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.Tuple;
import ehacks.mod.gui.EHacksClickGui;
import static ehacks.mod.modulesystem.classes.PrivateNuker.isActive;
import ehacks.mod.wrapper.Events;
import static ehacks.mod.wrapper.Events.selectedBlock;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BlockDestroy
extends Module {
    public static boolean isActive = false;

    public BlockDestroy() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "BlockDestroy";
    }
    
    @Override
    public String getDescription() {
        return "Destroy blocks instantly with a left click";
    }
    
    private Method snd;
    private Constructor msg;
    private Object obj;

    @Override
    public void onEnableMod() {
        try {
            msg = Class.forName("openmodularturrets.network.DropBaseMessage").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            msg.setAccessible(true);
            snd = Class.forName("cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.simpleimpl.IMessage"));
            snd.setAccessible(true);
            obj = Class.forName("openmodularturrets.ModularTurrets").getDeclaredField("networking").get(new Object[0]);
        }
        catch (Exception ex) {
            isActive = false;
            Events.selectedBlock = null;
            this.off();
            EHacksClickGui.logData.add(new Tuple<String, Integer>("[Block Destroyer] Not working", 0));
        }
        
        isActive = true;
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            msg = Class.forName("openmodularturrets.network.DropBaseMessage").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            msg.setAccessible(true);
            snd = Class.forName("cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.simpleimpl.IMessage"));
            snd.setAccessible(true);
            obj = Class.forName("openmodularturrets.ModularTurrets").getDeclaredField("networking").get(new Object[0]);
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }
    
    @Override
    public void onClick(PlayerInteractEvent event) {
        Block block = Wrapper.INSTANCE.world().getBlock(event.x, event.y, event.z);
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && PrivateNuker.isActive) {
            Block previous = selectedBlock;
            selectedBlock = block;
            if (previous == null || previous != null && !previous.getLocalizedName().equalsIgnoreCase(selectedBlock.getLocalizedName())) {
                EHacksClickGui.logData.add(new Tuple<String, Integer>("[Block Destroyer] Selected block: " + selectedBlock.getLocalizedName(), 0));
            }
        } else if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && AutoTool.isActive) {
            int blockId = Block.getIdFromBlock((Block)block);
            int slot = 0;
            float fl = 0.1f;
            for (int looper = 36; looper < 45; ++looper) {
                try {
                    ItemStack currentSlotThatIsSelected = Wrapper.INSTANCE.player().inventoryContainer.getSlot(looper).getStack();
                    if (currentSlotThatIsSelected.func_150997_a(Block.getBlockById((int)blockId)) <= fl) continue;
                    slot = looper - 36;
                    fl = currentSlotThatIsSelected.func_150997_a(Block.getBlockById((int)blockId));
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Wrapper.INSTANCE.player().inventory.currentItem = slot;
        } else if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && BlockDestroy.isActive) {
            
            try {
                snd.invoke(obj, msg.newInstance(event.x, event.y, event.z));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public String getModName() {
        return "OMTurrets";
    }
}

