package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.world.World;

public class WaterFall
        extends Module {

    private int delay;

    public WaterFall() {
        super(ModuleCategory.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "WaterFall";
    }

    @Override
    public String getDescription() {
        return "Automatically put water bucket on fall";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().fallDistance >= 5.0f) {
            this.switchToItem(I18n.format((String) "item.bucketWater.name", (Object[]) new Object[0]));
            Block blocks = Wrapper.INSTANCE.world().getBlock((int) Wrapper.INSTANCE.player().posX, (int) Wrapper.INSTANCE.player().posY - 3, (int) Wrapper.INSTANCE.player().posZ);
            if (blocks.getMaterial() != Material.air && this.hasItem(I18n.format((String) "item.bucketWater.name", (Object[]) new Object[0]))) {
                this.useItem();
                ++this.delay;
                if (this.delay >= 20) {
                    this.switchToItem(I18n.format((String) "item.bucket.name", (Object[]) new Object[0]));
                    this.useItem();
                    this.delay = 0;
                }
            }
        }
    }

    private void useItem() {
        ItemStack item = Wrapper.INSTANCE.player().inventory.getCurrentItem();
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C05PacketPlayerLook(90.0f, 90.0f, false));
        Wrapper.INSTANCE.mc().playerController.sendUseItem((EntityPlayer) Wrapper.INSTANCE.player(), (World) Wrapper.INSTANCE.world(), item);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C08PacketPlayerBlockPlacement((int) Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.world().getHeightValue((int) Wrapper.INSTANCE.player().posX, (int) Wrapper.INSTANCE.player().posZ), (int) Wrapper.INSTANCE.player().posZ, -1, item, 0.0f, 0.0f, 0.0f));
    }

    private boolean hasItem(String blockTileName) {
        for (int i = 36; i <= 44; ++i) {
            if (!Wrapper.INSTANCE.player().inventoryContainer.getSlot(i).getHasStack() || !(Wrapper.INSTANCE.player().inventoryContainer.getSlot(i).getStack().getDisplayName()).equalsIgnoreCase(blockTileName)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private void switchToItem(String itemName) {
        for (int i = 36; i <= 44; ++i) {
            if (!Wrapper.INSTANCE.player().inventoryContainer.getSlot(i).getHasStack() || !(Wrapper.INSTANCE.player().inventoryContainer.getSlot(i).getStack().getDisplayName()).equalsIgnoreCase(itemName)) {
                continue;
            }
            Wrapper.INSTANCE.player().inventory.currentItem = i - 36;
            break;
        }
    }
}
