package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Random;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class PacketFlooder
        extends Module {

    private final ArrayList<FakePacket> validPackets = new ArrayList<FakePacket>();

    private final Random rand = new Random();

    public PacketFlooder() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "PacketFlooder";
    }

    @Override
    public String getDescription() {
        return "Floods a lot of random packets to server\n\u00A7cTURN IT ON EVERY TIME YOU USE PACKETHACKS!";
    }

    @Override
    public void onEnableMod() {
        validPackets.clear();
        for (FakePacket packet : FakePacket.values()) {
            if (checkClass(packet.clazz())) {
                validPackets.add(packet);
            }
        }
        InteropUtils.log("Enabled: " + String.valueOf(validPackets.size()) + "/" + String.valueOf(FakePacket.values().length) + " packets", this);
    }

    public boolean checkClass(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onDisableMod() {
    }

    private int tempTimeout = 0;
    private int nextSet = 2;

    @Override
    public void onTicks() {
        if (tempTimeout == 0) {
            sendRandomPacket();
        }
        tempTimeout++;
        if (tempTimeout == nextSet) {
            tempTimeout = 0;
            nextSet = rand.num(2, 16);
        }
    }

    enum FakePacket {

        NEI_SendLoginState("codechicken.lib.packet.PacketCustom"),
        NEI_SpawnerID("codechicken.lib.packet.PacketCustom"),
        CRAYFISH_MessageTVServer("com.mrcrayfish.furniture.network.message.MessageTVServer"),
        CRAYFISH_MessageEmptyBin("com.mrcrayfish.furniture.network.message.MessageEmptyBin"),
        CRAYFISH_MessageMicrowave("com.mrcrayfish.furniture.network.message.MessageMicrowave"),
        FORESTRY_PacketBeeLogicEntityRequest("forestry.apiculture.network.packets.PacketBeeLogicEntityRequest"),
        FORESTRY_PacketGuiSelectRequest("forestry.core.network.packets.PacketGuiSelectRequest"),
        FORESTRY_PacketPipetteClick("forestry.core.network.packets.PacketPipetteClick"),
        FORESTRY_PacketLetterTextSet("forestry.mail.network.packets.PacketLetterTextSet"),
        ADVSOLAR_PacketGUIPressButton("advsolar.network.PacketGUIPressButton"),
        AE2_PacketValueConfig("appeng.core.sync.packets.PacketValueConfig"),
        AE2_PacketPartialItem("appeng.core.sync.packets.PacketPartialItem"),
        AE2_PacketCompassRequest("appeng.core.sync.packets.PacketCompassRequest"),
        BUILDCRAFT_PacketTabletMessage("buildcraft.core.tablet.PacketTabletMessage"),
        BUILDCRAFT_PacketSlotChange("buildcraft.core.lib.network.PacketSlotChange"),
        CARPENTER_PacketEnrichPlant("com.carpentersblocks.network.PacketEnrichPlant"),
        CARPENTER_PacketSlopeSelect("com.carpentersblocks.network.PacketSlopeSelect"),
        CODECHICKEN_ChunkLoaderCPH("codechicken.chunkloader.ChunkLoaderCPH"),
        ENDERIO_PacketSlotVisibility("crazypants.enderio.conduit.gui.PacketSlotVisibility"),
        ENDERIO_PacketMoveItems("crazypants.enderio.machine.invpanel.PacketMoveItems"),
        ENDERIO_PacketDrainPlayerXP("crazypants.enderio.xp.PacketDrainPlayerXP"),
        EXTRACELLS_PacketFluidStorage("extracells.network.packet.part.PacketFluidStorage"),
        FLANS_PacketDriveableGUI("com.flansmod.common.network.PacketDriveableGUI"),
        FLANS_PacketDriveableKey("com.flansmod.common.network.PacketDriveableKey"),
        FLANS_PacketDriveableKeyHeld("com.flansmod.common.network.PacketDriveableKeyHeld"),
        IC2NC_PacketServerUpdate("shedar.mods.ic2.nuclearcontrol.network.message.PacketServerUpdate"),
        IC2NC_PacketClientSound("shedar.mods.ic2.nuclearcontrol.network.message.PacketClientSound"),
        IC2NC_PacketClientRequest("shedar.mods.ic2.nuclearcontrol.network.message.PacketClientRequest"),
        MALISISDOORS_DoorFactoryMessage("net.malisis.doors.network.DoorFactoryMessage$Packet"),
        MEKANISM_PacketTileEntity("mekanism.common.network.PacketTileEntity$TileEntityMessage"),
        MEKANISM_PacketNewFilter("mekanism.common.network.PacketNewFilter$NewFilterMessage"),
        MEKANISM_PacketEditFilter("mekanism.common.network.PacketEditFilter$EditFilterMessage"),
        OPENBLOCKS_PlayerActionEvent("openblocks.events.PlayerActionEvent$Type"),
        OPENBLOCKS_PlayerMovementEvent("openmods.movement.PlayerMovementEvent$Type"),
        OC_SendClipboard("li.cil.oc.client.PacketSender"),
        OC_SendCopyToAnalyzer("li.cil.oc.client.PacketSender"),
        OC_SendRobotStateRequest("li.cil.oc.client.PacketSender"),
        TINKER_SignDataPacket("tconstruct.util.network.SignDataPacket"),
        TINKER_SmelteryPacket("tconstruct.util.network.SmelteryPacket"),
        TINKER_ToolStationPacket("tconstruct.util.network.ToolStationPacket"),
        THERMAL_TeleportChannelRegistry("cofh.thermalexpansion.core.TeleportChannelRegistry"),
        THERMAL_SendLexiconStudyPacketToServer("cofh.thermalfoundation.network.PacketTFBase"),
        THERMAL_SendLexiconStudySelectPacketToServer("cofh.thermalfoundation.network.PacketTFBase"),
        WRADDON_SendOpenSniffer("codechicken.wirelessredstone.addons.WRAddonCPH"),
        WRADDON_SendCloseSniffer("codechicken.wirelessredstone.addons.WRAddonCPH"),
        WRADDON_SendResetMap("codechicken.wirelessredstone.addons.WRAddonCPH"),
        BIBLIOCRAFT_BiblioClipBlock("jds.bibliocraft.BiblioCraft"),
        BIBLIOCRAFT_BiblioPaintingC("jds.bibliocraft.BiblioCraft"),
        BIBLIOCRAFT_BiblioTypeGui("jds.bibliocraft.BiblioCraft"),
        PROJECTRED_TileAutoCrafter("mrtjp.projectred.expansion.TileAutoCrafter"),
        PROJECTRED_TileFilteredImporter("mrtjp.projectred.expansion.TileFilteredImporter"),
        PROJECTRED_TileProjectBench("mrtjp.projectred.expansion.TileProjectBench"),
        THAUMCRAFT_Note("thaumcraft.common.lib.network.misc.PacketNote"),
        THAUMCRAFT_FocusChange("thaumcraft.common.lib.network.misc.PacketFocusChangeToServer"),
        THAUMCRAFT_ItemKey("thaumcraft.common.lib.network.misc.PacketItemKeyToServer");

        private final String clazz;

        private FakePacket(String clazz) {
            this.clazz = clazz;
        }

        public String clazz() {
            return clazz;
        }

    }

    public void sendRandomPacket() {
        if (validPackets.isEmpty()) {
            return;
        }
        FakePacket packet = validPackets.get(rand.num(validPackets.size()));
        try {
            Class packetClass = Class.forName(packet.clazz());
            for (int i = 0; i < rand.num(10, 30); i++) {
                switch (packet) {
                    case NEI_SendLoginState:
                        packetClass.getDeclaredMethod("sendToServer").invoke(packetClass.getDeclaredConstructor(Object.class, int.class).newInstance("NEI", 10));
                        break;
                    case NEI_SpawnerID:
                        Object SpawnerID_constructor = packetClass.getDeclaredConstructor(Object.class, int.class).newInstance("NEI", 15);
                        SpawnerID_constructor.getClass().getDeclaredMethod("writeCoord", int.class, int.class, int.class).invoke(SpawnerID_constructor, rand.x(), rand.y(), rand.z());
                        SpawnerID_constructor.getClass().getDeclaredMethod("writeString", String.class).invoke(SpawnerID_constructor, rand.str());
                        SpawnerID_constructor.getClass().getDeclaredMethod("sendToServer").invoke(SpawnerID_constructor);
                        break;
                    case CRAYFISH_MessageTVServer:
                    case CRAYFISH_MessageMicrowave:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("com.mrcrayfish.furniture.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class).newInstance(0, rand.x(), rand.y(), rand.z()));
                        break;
                    case CRAYFISH_MessageEmptyBin:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("com.mrcrayfish.furniture.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z()));
                        break;
                    case FORESTRY_PacketBeeLogicEntityRequest:
                        Class.forName("forestry.core.proxy.ProxyNetwork").getDeclaredMethod("sendToServer", Class.forName("forestry.core.network.IForestryPacketServer")).invoke(Class.forName("forestry.core.proxy.Proxies").getDeclaredField("net").get(null), packetClass.getDeclaredConstructor(Entity.class).newInstance(rand.ent()));
                        break;
                    case FORESTRY_PacketGuiSelectRequest:
                        Class.forName("forestry.core.proxy.ProxyNetwork").getDeclaredMethod("sendToServer", Class.forName("forestry.core.network.IForestryPacketServer")).invoke(Class.forName("forestry.core.proxy.Proxies").getDeclaredField("net").get(null), packetClass.getDeclaredConstructor(int.class, int.class).newInstance(rand.num(), rand.num()));
                        break;
                    case FORESTRY_PacketPipetteClick:
                        Class.forName("forestry.core.proxy.ProxyNetwork").getDeclaredMethod("sendToServer", Class.forName("forestry.core.network.IForestryPacketServer")).invoke(Class.forName("forestry.core.proxy.Proxies").getDeclaredField("net").get(null), packetClass.getDeclaredConstructor(TileEntity.class, int.class).newInstance(rand.tile(), rand.num()));
                        break;
                    case FORESTRY_PacketLetterTextSet:
                        Class.forName("forestry.core.proxy.ProxyNetwork").getDeclaredMethod("sendToServer", Class.forName("forestry.core.network.IForestryPacketServer")).invoke(Class.forName("forestry.core.proxy.Proxies").getDeclaredField("net").get(null), packetClass.getDeclaredConstructor(String.class).newInstance(rand.str()));
                        break;
                    case ADVSOLAR_PacketGUIPressButton:
                        Class.forName("advsolar.network.ASPPacketHandler").getDeclaredMethod("sendToServer", Class.forName("advsolar.network.IPacket")).invoke(null, packetClass.getDeclaredConstructor().newInstance());
                        break;
                    case AE2_PacketValueConfig:
                        Class.forName("appeng.core.sync.network.NetworkHandler").getDeclaredMethod("sendToServer", Class.forName("appeng.core.sync.AppEngPacket")).invoke(Class.forName("appeng.core.sync.network.NetworkHandler").getDeclaredField("instance").get(null), packetClass.getDeclaredConstructor(String.class, String.class).newInstance(rand.str(), rand.str()));
                        break;
                    case AE2_PacketPartialItem:
                        Class.forName("appeng.core.sync.network.NetworkHandler").getDeclaredMethod("sendToServer", Class.forName("appeng.core.sync.AppEngPacket")).invoke(Class.forName("appeng.core.sync.network.NetworkHandler").getDeclaredField("instance").get(null), packetClass.getDeclaredConstructor(int.class, int.class, byte[].class).newInstance(rand.num(), rand.num(), new byte[0]));
                        break;
                    case AE2_PacketCompassRequest:
                        Class.forName("appeng.core.sync.network.NetworkHandler").getDeclaredMethod("sendToServer", Class.forName("appeng.core.sync.AppEngPacket")).invoke(Class.forName("appeng.core.sync.network.NetworkHandler").getDeclaredField("instance").get(null), packetClass.getDeclaredConstructor(long.class, int.class, int.class, int.class).newInstance((long) rand.num(), rand.x(), rand.y(), rand.z()));
                        break;
                    case BUILDCRAFT_PacketTabletMessage:
                        Class.forName("buildcraft.BuildCraftMod").getDeclaredMethod("sendToServer", Class.forName("buildcraft.core.lib.network.Packet")).invoke(Class.forName("buildcraft.BuildCraftCore").getDeclaredField("instance").get(null), packetClass.getDeclaredConstructor(NBTTagCompound.class).newInstance(rand.nbt()));
                        break;
                    case BUILDCRAFT_PacketSlotChange:
                        Class.forName("buildcraft.BuildCraftMod").getDeclaredMethod("sendToServer", Class.forName("buildcraft.core.lib.network.Packet")).invoke(Class.forName("buildcraft.BuildCraftCore").getDeclaredField("instance").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class, int.class, ItemStack.class).newInstance(70, rand.x(), rand.y(), rand.z(), 0, rand.item()));
                        break;
                    case CARPENTER_PacketEnrichPlant:
                        Class.forName("com.carpentersblocks.util.handler.PacketHandler").getDeclaredMethod("sendPacketToServer", Class.forName("com.carpentersblocks.network.ICarpentersPacket")).invoke(null, packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z(), rand.num()));
                        break;
                    case CARPENTER_PacketSlopeSelect:
                        Class.forName("com.carpentersblocks.util.handler.PacketHandler").getDeclaredMethod("sendPacketToServer", Class.forName("com.carpentersblocks.network.ICarpentersPacket")).invoke(null, packetClass.getDeclaredConstructor(int.class, boolean.class).newInstance(rand.num(9), rand.bool()));
                        break;
                    case CODECHICKEN_ChunkLoaderCPH:
                        packetClass.getDeclaredMethod("sendGuiClosing").invoke(null);
                        break;
                    case ENDERIO_PacketSlotVisibility:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("crazypants.enderio.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(boolean.class, boolean.class).newInstance(rand.bool(), rand.bool()));
                        break;
                    case ENDERIO_PacketMoveItems:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("crazypants.enderio.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class).newInstance(rand.num(), rand.num(), rand.num(), rand.num()));
                        break;
                    case ENDERIO_PacketDrainPlayerXP:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("crazypants.enderio.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(TileEntity.class, int.class, boolean.class).newInstance(rand.tile(), rand.num(), rand.bool()));
                        break;
                    case EXTRACELLS_PacketFluidStorage:
                        Class.forName("extracells.network.AbstractPacket").getDeclaredMethod("sendPacketToServer").invoke(packetClass.getDeclaredConstructor(EntityPlayer.class).newInstance(Wrapper.INSTANCE.player()));
                        break;
                    case FLANS_PacketDriveableGUI:
                    case FLANS_PacketDriveableKey:
                        Class.forName("com.flansmod.common.network.PacketHandler").getDeclaredMethod("sendToServer", Class.forName("com.flansmod.common.network.PacketBase")).invoke(Class.forName("com.flansmod.common.FlansMod").getDeclaredField("packetHandler").get(null), packetClass.getDeclaredConstructor(int.class).newInstance(rand.num()));
                        break;
                    case FLANS_PacketDriveableKeyHeld:
                        Class.forName("com.flansmod.common.network.PacketHandler").getDeclaredMethod("sendToServer", Class.forName("com.flansmod.common.network.PacketBase")).invoke(Class.forName("com.flansmod.common.FlansMod").getDeclaredField("packetHandler").get(null), packetClass.getDeclaredConstructor(int.class, boolean.class).newInstance(rand.num(), rand.bool()));
                        break;
                    case IC2NC_PacketServerUpdate:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("shedar.mods.ic2.nuclearcontrol.network.ChannelHandler").getDeclaredField("network").get(null), packetClass.getDeclaredConstructor(ItemStack.class).newInstance(rand.item()));
                        break;
                    case IC2NC_PacketClientSound:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("shedar.mods.ic2.nuclearcontrol.network.ChannelHandler").getDeclaredField("network").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, byte.class, String.class).newInstance(rand.x(), rand.y(), rand.z(), (byte) 0, rand.str()));
                        break;
                    case IC2NC_PacketClientRequest:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("shedar.mods.ic2.nuclearcontrol.network.ChannelHandler").getDeclaredField("network").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z()));
                        break;
                    case MALISISDOORS_DoorFactoryMessage:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("net.malisis.doors.MalisisDoors").getDeclaredField("network").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class).newInstance(rand.num(), rand.x(), rand.y(), rand.z()));
                        break;
                    case MEKANISM_PacketTileEntity:
                        Class.forName("mekanism.common.PacketHandler").getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("mekanism.common.Mekanism").getDeclaredField("packetHandler").get(null), packetClass.getDeclaredConstructor(Class.forName("mekanism.api.Coord4D"), ArrayList.class).newInstance(Class.forName("mekanism.api.Coord4D").getDeclaredConstructor(int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z()), new ArrayList()));
                        break;
                    case MEKANISM_PacketNewFilter:
                        Class.forName("mekanism.common.PacketHandler").getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("mekanism.common.Mekanism").getDeclaredField("packetHandler").get(null), packetClass.getDeclaredConstructor(Class.forName("mekanism.api.Coord4D"), Object.class).newInstance(Class.forName("mekanism.api.Coord4D").getDeclaredConstructor(int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z()), new Object()));
                        break;
                    case MEKANISM_PacketEditFilter:
                        Class.forName("mekanism.common.PacketHandler").getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("mekanism.common.Mekanism").getDeclaredField("packetHandler").get(null), packetClass.getDeclaredConstructor(Class.forName("mekanism.api.Coord4D"), boolean.class, Object.class, Object.class).newInstance(Class.forName("mekanism.api.Coord4D").getDeclaredConstructor(int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z()), rand.bool(), new Object(), new Object()));
                        break;
                    case OPENBLOCKS_PlayerActionEvent:
                        Class.forName("openmods.network.event.NetworkEvent").getDeclaredMethod("sendToServer").invoke(Class.forName("openblocks.events.PlayerActionEvent").getDeclaredConstructor(packetClass).newInstance(packetClass.getDeclaredField("BOO").get(null)));
                        break;
                    case OPENBLOCKS_PlayerMovementEvent:
                        Class.forName("openmods.network.event.NetworkEvent").getDeclaredMethod("sendToServer").invoke(Class.forName("openblocks.events.ElevatorActionEvent").getDeclaredConstructor(int.class, int.class, int.class, int.class, packetClass).newInstance(0, rand.x(), 0, rand.z(), packetClass.getDeclaredField("JUMP").get(null)));
                        break;
                    case OC_SendClipboard:
                        packetClass.getDeclaredMethod("sendClipboard", String.class, String.class).invoke(null, rand.str(), rand.str());
                        break;
                    case OC_SendCopyToAnalyzer:
                        packetClass.getDeclaredMethod("sendCopyToAnalyzer", String.class, int.class).invoke(null, rand.str(), rand.num());
                        break;
                    case OC_SendRobotStateRequest:
                        packetClass.getDeclaredMethod("sendRobotStateRequest", int.class, int.class, int.class, int.class).invoke(null, 0, rand.x(), rand.y(), rand.z());
                        break;
                    case TINKER_SignDataPacket:
                        Class.forName("tconstruct.util.network.PacketPipeline").getDeclaredMethod("sendToServer", Class.forName("mantle.common.network.AbstractPacket")).invoke(Class.forName("tconstruct.TConstruct").getDeclaredField("packetPipeline").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class, String[].class).newInstance(0, rand.x(), rand.y(), rand.z(), new String[0]));
                        break;
                    case TINKER_SmelteryPacket:
                        Class.forName("tconstruct.util.network.PacketPipeline").getDeclaredMethod("sendToServer", Class.forName("mantle.common.network.AbstractPacket")).invoke(Class.forName("tconstruct.TConstruct").getDeclaredField("packetPipeline").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class, boolean.class, int.class).newInstance(0, rand.x(), rand.y(), rand.z(), rand.bool(), 0));
                        break;
                    case TINKER_ToolStationPacket:
                        Class.forName("tconstruct.util.network.PacketPipeline").getDeclaredMethod("sendToServer", Class.forName("mantle.common.network.AbstractPacket")).invoke(Class.forName("tconstruct.TConstruct").getDeclaredField("packetPipeline").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, String.class).newInstance(rand.x(), rand.y(), rand.z(), rand.str()));
                        break;
                    case THERMAL_TeleportChannelRegistry:
                        packetClass.getDeclaredMethod("requestChannelList", String.class).invoke(null, rand.str());
                        break;
                    case THERMAL_SendLexiconStudyPacketToServer:
                        packetClass.getDeclaredMethod("sendLexiconStudyPacketToServer", int.class).invoke(null, rand.num());
                        break;
                    case THERMAL_SendLexiconStudySelectPacketToServer:
                        packetClass.getDeclaredMethod("sendLexiconStudySelectPacketToServer", int.class, String.class).invoke(null, rand.num(), rand.str());
                        break;
                    case WRADDON_SendOpenSniffer:
                        packetClass.getDeclaredMethod("sendOpenSniffer").invoke(null);
                        break;
                    case WRADDON_SendCloseSniffer:
                        packetClass.getDeclaredMethod("sendCloseSniffer").invoke(null);
                        break;
                    case WRADDON_SendResetMap:
                        packetClass.getDeclaredMethod("sendResetMap").invoke(null);
                        break;
                    case BIBLIOCRAFT_BiblioClipBlock:
                        Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(packetClass.getDeclaredField("ch_BiblioClipBlock").get(null), Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(Unpooled.buffer(), "BiblioClipBlock"));
                        break;
                    case BIBLIOCRAFT_BiblioPaintingC:
                        Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(packetClass.getDeclaredField("ch_BiblioPaintingC").get(null), Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(Unpooled.buffer(), "BiblioPaintingC"));
                        break;
                    case BIBLIOCRAFT_BiblioTypeGui:
                        Class.forName("cpw.mods.fml.common.network.FMLEventChannel").getDeclaredMethod("sendToServer", Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket")).invoke(packetClass.getDeclaredField("ch_BiblioType").get(null), Class.forName("cpw.mods.fml.common.network.internal.FMLProxyPacket").getDeclaredConstructor(ByteBuf.class, String.class).newInstance(Unpooled.buffer(), "BiblioTypeGui"));
                        break;
                    case PROJECTRED_TileAutoCrafter:
                        packetClass.getDeclaredMethod("sendCyclePlanSlot").invoke(packetClass.getDeclaredConstructor().newInstance());
                        break;
                    case PROJECTRED_TileFilteredImporter:
                        packetClass.getDeclaredMethod("clientCycleColourUp").invoke(packetClass.getDeclaredConstructor().newInstance());
                        break;
                    case PROJECTRED_TileProjectBench:
                        packetClass.getDeclaredMethod("sendWriteButtonAction").invoke(packetClass.getDeclaredConstructor().newInstance());
                        break;
                    case THAUMCRAFT_Note:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("thaumcraft.common.lib.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(int.class, int.class, int.class, int.class).newInstance(rand.x(), rand.y(), rand.z(), 0));
                        break;
                    //case THAUMCRAFT_FocusChange:
                    //    SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("thaumcraft.common.lib.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(EntityPlayer.class, String.class).newInstance(Wrapper.INSTANCE.player(), rand.str()));
                    //    break;
                    case THAUMCRAFT_ItemKey:
                        SimpleNetworkWrapper.class.getDeclaredMethod("sendToServer", IMessage.class).invoke(Class.forName("thaumcraft.common.lib.network.PacketHandler").getDeclaredField("INSTANCE").get(null), packetClass.getDeclaredConstructor(EntityPlayer.class, int.class).newInstance(Wrapper.INSTANCE.player(), rand.num()));
                        break;
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public String getModName() {
        return "Forge";
    }
}
