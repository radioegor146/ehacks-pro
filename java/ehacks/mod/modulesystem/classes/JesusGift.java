package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.network.ByteBufUtils;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.Keybinds;
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
        return "Gives you items through Atlas item from BiblioCraft\nUsage:\n  Numpad2 - perform give";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[JesusGift] Not working");
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

    @Override
    public void onDisableMod() {

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
            itemTag.setTag("Inventory", inventoryList);
            handItem.setTagCompound(itemTag);
            ByteBuf buf = Unpooled.buffer(0);
            ByteBufUtils.writeItemStack(buf, handItem);
            try {
                Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(Class.forName("jds.bibliocraft.BiblioCraft").getDeclaredField("ch_BiblioAtlasGUIswap").get(null), Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(buf, "BiblioAtlasSWP"));
            } catch (Exception e) {
                EHacksClickGui.log("[JesusGift] Mod error");
            }
        } else {
            EHacksClickGui.log("[JesusGift] Wrong item in hand");
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
