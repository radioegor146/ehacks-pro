/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.debugme;

import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.Random;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author radioegor146
 */
public class Debug {

    public static Random random = new Random();

    public static int[] getMop() {
        RayTraceResult preMop = Wrapper.INSTANCE.mc().objectMouseOver;
        Entity ent = Wrapper.INSTANCE.mc().pointedEntity;
        int[] arrn = new int[5];
        arrn[0] = preMop.getBlockPos().getX();
        arrn[1] = preMop.getBlockPos().getY();
        arrn[2] = preMop.getBlockPos().getZ();
        arrn[3] = preMop.sideHit.getHorizontalIndex(); //возможно не то
        arrn[4] = ent != null ? ent.getEntityId() : 0;
        int[] mop = arrn;
        return mop;
    }

    public static int[] getMyCoords() {
        return getCoords(getPlayer());
    }

    public static int[] getCoords(Entity entity) {
        int[] coords = new int[]{(int) Math.floor(entity.posX), (int) Math.floor(entity.posY) - 2, (int) Math.floor(entity.posZ)};
        return coords;
    }

    public static int[] getCoords(TileEntity tileEntity) {
        int[] coords = new int[]{tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ()};
        return coords;
    }

    public static EntityPlayer getPlayer(String nick) {
        return getWorld().getPlayerEntityByName(nick);
    }

    public static EntityPlayer getPlayer() {
        return Wrapper.INSTANCE.player();
    }

    public static String getUUID(Entity entity) {
        return entity.getUniqueID().toString();
    }

    public static void log(String data) {
        InteropUtils.log(data, "DebugMe");
    }

    public static int getEntityId(Entity entity) {
        return entity.getEntityId();
    }

    public static int getMyEntityId() {
        return getEntityId(getPlayer());
    }

    public static World getWorld() {
        return Wrapper.INSTANCE.world();

    }

    public static int getDimensionId() {
        return getWorld().provider.getDimension();
    }

    public static NBTTagCompound jsonToNBT(String json) {
        NBTBase nbtTag = null;
        try {
            nbtTag = JsonToNBT.getTagFromJson(json.replaceAll("'", "\""));
        } catch (Exception err) {
            return null;
        }
        return (NBTTagCompound) nbtTag;
    }

    public static String NBTToJson(NBTTagCompound nbt) {
        return nbt.toString().replaceAll("\\s+", "").replaceAll("\"", "'").replaceAll(",}", "}").replaceAll(",]", "]");
    }

    public static ItemStack getItem() {
        return getItem(null);
    }

    public static ItemStack getItem(NBTTagCompound tag) {
        return getItem(getPlayer().getHeldItemMainhand(), tag);
    }

    public static ItemStack getItem(ItemStack toItem, NBTTagCompound tag) {
        if (toItem != null && tag != null) {
            toItem.setTagCompound(tag);
        }
        return toItem;
    }

    public static TileEntity getTileEntity() {
        return getTileEntity(null);
    }

    public static TileEntity getTileEntity(NBTTagCompound tag) {
        int[] mop = getMop();
        return getTileEntity(getTileEntity(mop[0], mop[1], mop[2]), tag);
    }

    public static TileEntity getTileEntity(TileEntity toTile, NBTTagCompound tag) {
        if (toTile != null && tag != null) {
            toTile.readFromNBT(tag);
        }
        return toTile;
    }

    public static TileEntity getTileEntity(int x, int y, int z) {
        return getWorld().getTileEntity(new BlockPos(x, y, z));
    }

    public static Entity getEntity() {
        return getEntity(null);
    }

    public static Entity getEntity(NBTTagCompound tag) {
        int[] mop = getMop();
        return getEntity(getEntity(mop[4]), tag);
    }

    public static Entity getEntity(Entity toEntity, NBTTagCompound tag) {
        if (toEntity != null && tag != null) {
            toEntity.readFromNBT(tag);
        }
        return toEntity;
    }

    public static Entity getEntity(int id) {
        return getWorld().getEntityByID(id);
    }

    public static List<Entity> getNearEntities() {
        return getWorld().loadedEntityList;
    }

    public static List<EntityPlayer> getNearPlayers() {
        return getWorld().playerEntities;
    }

    public static String getLongString(int len) {
        return getLongString(random.str(), len);
    }

    public static String getLongString(String text, int len) {
        String out = text;
        for (int i = 1; i <= len; ++i) {
            out += random.str();
        }
        return out;
    }

    public static void sendServerChatMessage(Object serverChatMessageText) {
        Wrapper.INSTANCE.player().sendChatMessage(serverChatMessageText.toString());
    }

    public static int getWindowId() {
        return getPlayer().openContainer.windowId;
    }

    public static void sendTabComplete(String in) {
        Wrapper.INSTANCE.player().connection.sendPacket(new CPacketTabComplete());
    }

    public static void setFakeSlot(ItemStack item, int slot) {
        getPlayer().inventory.setInventorySlotContents(slot, item);
    }

    public static GuiContainer getGuiContainer() {
        return getGuiContainer(Wrapper.INSTANCE.mc().currentScreen);
    }

    public static GuiContainer getGuiContainer(GuiScreen screen) {
        return screen instanceof GuiContainer ? (GuiContainer) screen : null;
    }

    public static boolean isDoubleChest(TileEntity tile) {
        int y;
        int z;
        int[] pos;
        int x;
        return tile instanceof TileEntityChest && (getTileEntity((x = (pos = getCoords(tile))[0]) - 1, y = pos[1], z = pos[2]) instanceof TileEntityChest || getTileEntity(x + 1, y, z) instanceof TileEntityChest || getTileEntity(x, y, z - 1) instanceof TileEntityChest || getTileEntity(x, y, z + 1) instanceof TileEntityChest);
    }

    public static void dropSlots(int range) {
        for (int slot = 0; slot < range; ++slot) {
            dropSlot(slot);
        }
    }

    public static void dropSlot(int slot) {
        clickSlot(slot, 1, 4);
    }

    public static void clickSlot(int slot, int shift, int action) {
        Wrapper.INSTANCE.mc().playerController.windowClick(getWindowId(), slot, shift, ClickType.PICKUP, getPlayer());
    }

    public static int getCurrentSlot() {
        return getPlayer().inventory.currentItem;
    }

    public static int getSlots() {
        return getSlots(getTileEntity());
    }

    public static int getSlots(TileEntity tile) {
        if (tile instanceof IInventory) {
            IInventory inventory = (IInventory) tile;
            return inventory.isUsableByPlayer(getPlayer()) ? inventory.getSizeInventory() : 0;
        }
        return 0;
    }

    public static String getEntityInfoString(Entity entity) {
        return "[" + entity.getName() + ": UUID(" + getUUID(entity) + "), ID(" + getEntityId(entity) + ")]";
    }

    public static List<TileEntity> getNearTileEntities() {
        ArrayList<TileEntity> out = new ArrayList<>();
        IChunkProvider chunkProvider = getWorld().getChunkProvider();
        if (chunkProvider instanceof ChunkProviderClient) {
            List<Chunk> chunks = ReflectionHelper.getPrivateValue(ChunkProviderClient.class, (ChunkProviderClient) chunkProvider, Mappings.chunkListing);
            chunks.forEach((chunk) -> {
                chunk.getTileEntityMap().values().stream().filter((entityObj) -> !(!(entityObj instanceof TileEntity))).forEachOrdered((entityObj) -> {
                    out.add((TileEntity) entityObj);
                });
            });
        }
        return out;
    }

    public static NBTTagCompound getPackageNbt(ItemStack item) {
        NBTTagCompound ROOT = new NBTTagCompound();
        ROOT.setTag("Package", new NBTTagCompound());
        NBTTagCompound Package2 = ROOT.getCompoundTag("Package");
        NBTTagList list = new NBTTagList();
        list.appendTag(getNbtItem(item));
        Package2.setTag("Items", list);
        return ROOT;
    }

    public static NBTTagCompound getNbtItem(ItemStack item) {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByte("Count", (byte) item.getMaxStackSize());
        itemTag.setByte("Slot", (byte) 0);
        item.getItem();
        itemTag.setShort("id", (short) Item.getIdFromItem(item.getItem()));
        itemTag.setShort("Damage", (short) item.getItemDamage());
        if (item.hasTagCompound()) {
            itemTag.setTag("tag", item.getTagCompound());
        }
        return itemTag;
    }

    public static List<int[]> getNukerList(int[] center, int radius) {
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = radius; i >= -radius; --i) {
            for (int k = radius; k >= -radius; --k) {
                for (int j = -radius; j <= radius; ++j) {
                    int x = center[0] + i;
                    int y = center[1] + j;
                    int z = center[2] + k;
                    if (getWorld().getBlockState(new BlockPos(x, y, z)).getMaterial() instanceof MaterialTransparent) {
                        continue;
                    }
                    list.add(new int[]{x, y, z});
                }
            }
        }
        return list;
    }

    public static List<int[]> getThorList(String filter) {
        ArrayList<int[]> nearCoords = new ArrayList<>();
        List<Entity> near = getNearEntities();
        for (Entity entity : near) {
            if (filter.equals("pl")) {
                if (entity == getPlayer() || !(entity instanceof EntityPlayer)) {
                    continue;
                }
                nearCoords.add(getCoords(entity));
                continue;
            }
            if (filter.equals("ent")) {
                if (entity == getPlayer() || entity instanceof EntityLightningBolt) {
                    continue;
                }
                nearCoords.add(getCoords(entity));
                continue;
            }
            EntityPlayer player = getPlayer(filter);
            if (player == null) {
                continue;
            }
            nearCoords.add(getCoords(player));
            break;
        }
        return nearCoords;
    }

    public static void sendProxyPacket(String channel, Object... data) {
        ByteBuf buf = Unpooled.buffer();
        for (Object o : data) {
            if (o instanceof Integer) {
                buf.writeInt(((int) o));
                continue;
            }
            if (o instanceof Byte) {
                buf.writeByte(((byte) o));
                continue;
            }
            if (o instanceof Short) {
                buf.writeShort(((short) o));
                continue;
            }
            if (o instanceof String) {
                ByteBufUtils.writeUTF8String(buf, ((String) o));
                continue;
            }
            if (o instanceof ItemStack) {
                ByteBufUtils.writeItemStack(buf, ((ItemStack) o));
                continue;
            }
            if (!(o instanceof NBTTagCompound)) {
                continue;
            }
            ByteBufUtils.writeTag(buf, ((NBTTagCompound) o));
        }
        NetworkDispatcher.get(new NetworkManager(EnumPacketDirection.CLIENTBOUND)).sendProxy(new FMLProxyPacket(new PacketBuffer(buf), channel));
    }
}
