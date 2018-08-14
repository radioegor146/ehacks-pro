/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.debugme;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.handshake.NetworkDispatcher;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.util.Mappings;
import ehacks.mod.wrapper.Wrapper;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ehacks.mod.util.Random;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

/**
 *
 * @author radioegor146
 */
public class Debug {
    public static Debug INSTANCE = new Debug();
    
    public int PUBLIC_INT = 0;
    public boolean PUBLIC_BOOL = false;
    public String PUBLIC_STR = "";
    public Random random = new Random();
    
    public int[] getMop() {
        MovingObjectPosition preMop = Wrapper.INSTANCE.mc().objectMouseOver;
        Entity ent = Wrapper.INSTANCE.mc().pointedEntity;
        int[] arrn = new int[5];
        arrn[0] = preMop.blockX;
        arrn[1] = preMop.blockY;
        arrn[2] = preMop.blockZ;
        arrn[3] = preMop.sideHit;
        arrn[4] = ent != null ? ent.getEntityId() : 0;
        int[] mop = arrn;
        return mop;
    }

    public int[] getMyCoords() {
        return getCoords((Entity)getPlayer());
    }

    public int[] getCoords(Entity entity) {
        int[] coords = new int[]{(int)Math.floor(entity.posX), (int)Math.floor(entity.posY) - 2, (int)Math.floor(entity.posZ)};
        return coords;
    }

    public int[] getCoords(TileEntity tileEntity) {
        int[] coords = new int[]{tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord};
        return coords;
    }

    public EntityPlayer getPlayer(String nick) {
        return getWorld().getPlayerEntityByName(nick);
    }

    public EntityPlayer getPlayer() {
        return Wrapper.INSTANCE.mc().thePlayer;
    }

    public String getUUID(Entity entity) {
        return entity.getUniqueID().toString();
    }

    public void log(String data) {
        EHacksClickGui.log("[DebugMe] [Log] " + data);
    }
    
    public int getEntityId(Entity entity) {
        return entity.getEntityId();
    }

    public int getMyEntityId() {
        return getEntityId((Entity)getPlayer());
    }

    public World getWorld() {
        return Wrapper.INSTANCE.mc().theWorld;
    }

    public int getDimensionId() {
        return getWorld().provider.dimensionId;
    }

    public NBTTagCompound jsonToNBT(String json) {
        NBTBase nbtTag = null;
        try {
            nbtTag = JsonToNBT.func_150315_a((String)json.replaceAll("'", "\""));
        }
        catch (Exception err) {
            return null;
        }
        return (NBTTagCompound)nbtTag;
    }

    public String NBTToJson(NBTTagCompound nbt) {
        return nbt.toString().replaceAll("\\s+", "").replaceAll("\"", "'").replaceAll(",}", "}").replaceAll(",]", "]");
    }

    public ItemStack getItem() {
        return getItem(null);
    }

    public ItemStack getItem(NBTTagCompound tag) {
        return getItem(getPlayer().getCurrentEquippedItem(), tag);
    }

    public ItemStack getItem(ItemStack toItem, NBTTagCompound tag) {
        if (toItem != null && tag != null) {
            toItem.setTagCompound(tag);
        }
        return toItem;
    }

    public TileEntity getTileEntity() {
        return getTileEntity(null);
    }

    public TileEntity getTileEntity(NBTTagCompound tag) {
        int[] mop = getMop();
        return getTileEntity(getTileEntity(mop[0], mop[1], mop[2]), tag);
    }

    public TileEntity getTileEntity(TileEntity toTile, NBTTagCompound tag) {
        if (toTile != null && tag != null) {
            toTile.readFromNBT(tag);
        }
        return toTile;
    }

    public TileEntity getTileEntity(int x, int y, int z) {
        return getWorld().getTileEntity(x, y, z);
    }

    public Entity getEntity() {
        return getEntity(null);
    }

    public Entity getEntity(NBTTagCompound tag) {
        int[] mop = getMop();
        return getEntity(getEntity(mop[4]), tag);
    }

    public Entity getEntity(Entity toEntity, NBTTagCompound tag) {
        if (toEntity != null && tag != null) {
            toEntity.readFromNBT(tag);
        }
        return toEntity;
    }

    public Entity getEntity(int id) {
        return getWorld().getEntityByID(id);
    }

    public List<Entity> getNearEntities() {
        return getWorld().loadedEntityList;
    }

    public List<EntityPlayer> getNearPlayers() {
        return getWorld().playerEntities;
    }

    public boolean switcher() {
        PUBLIC_BOOL = !PUBLIC_BOOL;
        return PUBLIC_BOOL;
    }

    public String getLongString(int len) {
        return getLongString(random.str(), len);
    }

    public String getLongString(String text, int len) {
        String out = text;
        for (int i = 1; i <= len; ++i) {
            out = out + random.str();
        }
        return out;
    }

    public void sendServerChatMessage(Object serverChatMessageText) {
        Wrapper.INSTANCE.mc().thePlayer.sendChatMessage(serverChatMessageText.toString());
    }

    public int getWindowId() {
        return getPlayer().openContainer.windowId;
    }

    public void sendTabComplete(String in) {
        Wrapper.INSTANCE.mc().thePlayer.sendQueue.addToSendQueue((Packet)new C14PacketTabComplete(in));
    }

    public void setFakeSlot(ItemStack item, int slot) {
        getPlayer().inventory.setInventorySlotContents(slot, item);
    }

    public GuiContainer getGuiContainer() {
        return getGuiContainer(Wrapper.INSTANCE.mc().currentScreen);
    }

    public GuiContainer getGuiContainer(GuiScreen screen) {
        return screen instanceof GuiContainer ? (GuiContainer)screen : null;
    }

    public boolean isDoubleChest(TileEntity tile) {
        int y;
        int z;
        int[] pos;
        int x;
        if (tile instanceof TileEntityChest && (getTileEntity((x = (pos = getCoords(tile))[0]) - 1, y = pos[1], z = pos[2]) instanceof TileEntityChest || getTileEntity(x + 1, y, z) instanceof TileEntityChest || getTileEntity(x, y, z - 1) instanceof TileEntityChest || getTileEntity(x, y, z + 1) instanceof TileEntityChest)) {
            return true;
        }
        return false;
    }

    public void dropSlots(int range) {
        for (int slot = 0; slot < range; ++slot) {
            dropSlot(slot);
        }
    }

    public void dropSlot(int slot) {
        clickSlot(slot, 1, 4);
    }

    public void clickSlot(int slot, int shift, int action) {
        Wrapper.INSTANCE.mc().playerController.windowClick(getWindowId(), slot, shift, action, getPlayer());
    }

    public int getCurrentSlot() {
        return getPlayer().inventory.currentItem;
    }

    public int getSlots() {
        return getSlots(getTileEntity());
    }

    public int getSlots(TileEntity tile) {
        if (tile instanceof IInventory) {
            IInventory inventory = (IInventory)tile;
            return inventory.isUseableByPlayer(getPlayer()) ? inventory.getSizeInventory() : 0;
        }
        return 0;
    }

    public String getEntityInfoString(Entity entity) {
        return "[" + entity.getCommandSenderName() + ": UUID(" + getUUID(entity) + "), ID(" + getEntityId(entity) + ")]";
    }

    public List<TileEntity> getNearTileEntities() {
        ArrayList<TileEntity> out = new ArrayList<TileEntity>();
        IChunkProvider chunkProvider = getWorld().getChunkProvider();
        if (chunkProvider instanceof ChunkProviderClient) {
            List<Chunk> chunks = ReflectionHelper.getPrivateValue(ChunkProviderClient.class, (ChunkProviderClient)chunkProvider, Mappings.chunkListing);
            for (Chunk chunk : chunks) {
                for (Object entityObj : chunk.chunkTileEntityMap.values()) {
                    if (!(entityObj instanceof TileEntity)) continue;
                    out.add((TileEntity)entityObj);
                }
            }
        }
        return out;
    }

    public NBTTagCompound getPackageNbt(ItemStack item) {
        NBTTagCompound ROOT = new NBTTagCompound();
        ROOT.setTag("Package", (NBTBase)new NBTTagCompound());
        NBTTagCompound Package2 = ROOT.getCompoundTag("Package");
        NBTTagList list = new NBTTagList();
        list.appendTag((NBTBase)getNbtItem(item));
        Package2.setTag("Items", (NBTBase)list);
        return ROOT;
    }

    public NBTTagCompound getNbtItem(ItemStack item) {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByte("Count", (byte)item.stackSize);
        itemTag.setByte("Slot", (byte)0);
        item.getItem();
        itemTag.setShort("id", (short)Item.getIdFromItem((Item)item.getItem()));
        itemTag.setShort("Damage", (short)item.getItemDamage());
        if (item.hasTagCompound()) {
            itemTag.setTag("tag", (NBTBase)item.getTagCompound());
        }
        return itemTag;
    }

    public List<int[]> getNukerList(int[] center, int radius) {
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int i = radius; i >= - radius; --i) {
            for (int k = radius; k >= - radius; --k) {
                for (int j = - radius; j <= radius; ++j) {
                    int x = center[0] + i;
                    int y = center[1] + j;
                    int z = center[2] + k;
                    if (getWorld().getBlock(x, y, z).getMaterial() instanceof MaterialTransparent) continue;
                    list.add(new int[]{x, y, z});
                }
            }
        }
        return list;
    }

    public List<int[]> getThorList(String filter) {
        ArrayList<int[]> nearCoords = new ArrayList<int[]>();
        List<Entity> near = getNearEntities();
        for (Entity entity : near) {
            if (filter.equals("pl")) {
                if (entity == getPlayer() || !(entity instanceof EntityPlayer)) continue;
                nearCoords.add(getCoords(entity));
                continue;
            }
            if (filter.equals("ent")) {
                if (entity == getPlayer() || entity instanceof EntityLightningBolt) continue;
                nearCoords.add(getCoords(entity));
                continue;
            }
            EntityPlayer player = getPlayer(filter);
            if (player == null) continue;
            nearCoords.add(getCoords((Entity)player));
            break;
        }
        return nearCoords;
    }

    public void sendProxyPacket(String channel, Object ... data) {
        ByteBuf buf = Unpooled.buffer();
        for (Object o : data) {
            if (o instanceof Integer) {
                buf.writeInt(((Integer)o).intValue());
                continue;
            }
            if (o instanceof Byte) {
                buf.writeByte((int)((Byte)o).byteValue());
                continue;
            }
            if (o instanceof Short) {
                buf.writeShort((int)((Short)o).shortValue());
                continue;
            }
            if (o instanceof String) {
                ByteBufUtils.writeUTF8String((ByteBuf)buf, (String)((String)o));
                continue;
            }
            if (o instanceof ItemStack) {
                ByteBufUtils.writeItemStack((ByteBuf)buf, (ItemStack)((ItemStack)o));
                continue;
            }
            if (!(o instanceof NBTTagCompound)) continue;
            ByteBufUtils.writeTag((ByteBuf)buf, (NBTTagCompound)((NBTTagCompound)o));
        }
        NetworkDispatcher.get((NetworkManager)Wrapper.INSTANCE.mc().getNetHandler().getNetworkManager()).sendProxy(new FMLProxyPacket(buf, channel));
    }
}
