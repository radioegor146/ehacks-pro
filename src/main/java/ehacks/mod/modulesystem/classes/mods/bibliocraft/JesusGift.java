package ehacks.mod.modulesystem.classes.mods.bibliocraft;

import cpw.mods.fml.common.network.ByteBufUtils;
import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.keybinds.GiveKeybind;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.input.Keyboard;

public class JesusGift
        extends Module {

    public JesusGift() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "JesusGift";
    }

    @Override
    public String getDescription() {
        return "Gives you items through Atlas item from BiblioCraft\nUsage:\n  Numpad0 - perform give";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(GiveKeybind.getKey());
        if (newState && !prevState) {
            prevState = newState;
            if (Statics.STATIC_ITEMSTACK == null) {
                return;
            }
            tryAtlas(Statics.STATIC_ITEMSTACK);
        }
        prevState = newState;
    }

    public void tryAtlas(ItemStack item) {
        ItemStack handItem = Wrapper.INSTANCE.player().getCurrentEquippedItem();
        if (handItem != null && handItem.getUnlocalizedName().equals("item.AtlasBook")) {
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.setByte("autoCenter", (byte) 0);
            itemTag.setInteger("lastGUImode", 0);
            itemTag.setInteger("mapSlot", -1);
            itemTag.setInteger("zoomLevel", 0);
            itemTag.setTag("maps", new NBTTagList());
            NBTTagList inventoryList = new NBTTagList();
            for (int slot = 0; slot < 5; slot++) {
                inventoryList.appendTag(nbtItem(item, slot, true));
            }
            inventoryList.appendTag(nbtItem(handItem, 5, false));
            for (int slot = 6; slot < 42; slot++) {
                inventoryList.appendTag(nbtItem(item, slot, true));
            }
            for (int slot = 42; slot < 48; slot++) {
                inventoryList.appendTag(nbtItem(handItem, slot, false));
            }
            itemTag.setTag("Inventory", inventoryList);
            handItem.setTagCompound(itemTag);
            ByteBuf buf = Unpooled.buffer(0);
            ByteBufUtils.writeItemStack(buf, handItem);
            try {
                Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(Class.forName("jds.bibliocraft.BiblioCraft").getDeclaredField("ch_BiblioAtlasGUIswap").get(null), Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(buf, "BiblioAtlasSWP"));
            } catch (Exception e) {
                InteropUtils.log("Mod error", this);
            }
        } else if (handItem != null && handItem.getUnlocalizedName().equals("item.SlottedBook")) {
            NBTTagCompound itemTag = new NBTTagCompound();
            itemTag.setString("authorName", "FF-Team");
            NBTTagList inventoryList = new NBTTagList();
            inventoryList.appendTag(nbtItem(item, 0, true));
            itemTag.setTag("Inventory", inventoryList);
            handItem.setTagCompound(itemTag);
            ByteBuf buf = Unpooled.buffer(0);
            ByteBufUtils.writeItemStack(buf, handItem);
            try {
                Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(Class.forName("jds.bibliocraft.BiblioCraft").getDeclaredField("ch_BiblioAtlasGUIswap").get(null), Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(buf, "BiblioAtlasSWP"));
            } catch (Exception e) {
                InteropUtils.log("Mod error", this);
            }
        } else {
            InteropUtils.log("Wrong item in hand", this);
        }
    }

    private NBTTagCompound nbtItem(ItemStack item, int slot, boolean withNbt) {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByte("Count", (byte) item.stackSize);
        itemTag.setByte("Slot", (byte) slot);
        itemTag.setShort("id", (short) Item.getIdFromItem(item.getItem()));
        itemTag.setShort("Damage", (short) item.getItemDamage());
        if (item.hasTagCompound() && withNbt) {
            itemTag.setTag("tag", item.getTagCompound());
        }
        return itemTag;
    }

    @Override
    public String getModName() {
        return "BiblioCraft";
    }
}
