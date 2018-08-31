package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.network.ByteBufUtils;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.lwjgl.input.Keyboard;

public class ItemCreator
        extends Module {

    public ItemCreator() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ItemCreator";
    }

    @Override
    public String getDescription() {
        return "Gives you any ItemStack you want\nUsage:\n  Numpad0 - perform give";
    }

    public void giveItem(ItemStack stack) {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(10);

        ItemStack mail = new ItemStack(Items.stick);
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < 6; i++) {
            NBTTagCompound item = new NBTTagCompound();
            item.setByte("Slot", (byte)i);
            stack.writeToNBT(item);
            tagList.appendTag((NBTBase)item);
        }

        NBTTagCompound inv = new NBTTagCompound();
        inv.setTag("Items", (NBTBase)tagList);
        inv.setString("UniqueID", UUID.randomUUID().toString());
        mail.stackTagCompound = new NBTTagCompound();
        mail.stackTagCompound.setTag("Package", inv);
        ByteBufUtils.writeItemStack(buf, mail);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("cfm", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keybinds.give);
        if (newState && !prevState) {
            prevState = newState;
            if (Statics.STATIC_ITEMSTACK == null) {
                return;
            }
            giveItem(Statics.STATIC_ITEMSTACK);
            InteropUtils.log("Gived", this);
        }
        prevState = newState;
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessagePackage");
        } catch (Exception ex) {
            this.off();
            InteropUtils.log("Not working", this);
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessagePackage");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {
    }

    @Override
    public String getModName() {
        return "Furniture";
    }
}
