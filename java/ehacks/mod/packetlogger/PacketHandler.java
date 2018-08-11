package ehacks.mod.packetlogger;

import io.netty.buffer.ByteBuf;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import ehacks.mod.wrapper.PacketHandler.Side;
import java.lang.reflect.Modifier;

public class PacketHandler {
    public static List<String> inBlackList = new ArrayList<String>();
    public static List<String> logBlackList = new ArrayList<String>();
    public static List<String> outBlackList = new ArrayList<String>();

    public static void newList() {
        inBlackList.clear();
        logBlackList.clear();
        outBlackList.clear();
        logBlackList.add("net.minecraft.network.play.client.C03PacketPlayer");
        logBlackList.add("net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition");
        logBlackList.add("net.minecraft.network.play.client.C00PacketKeepAlive");
        logBlackList.add("net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook");
        logBlackList.add("net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook");
        logBlackList.add("net.minecraft.network.play.client.C0APacketAnimation");
        logBlackList.add("net.minecraft.network.play.client.C0BPacketEntityAction");
        logBlackList.add("net.minecraft.network.play.server.S14PacketEntity.S15PacketEntityRelMove");
        logBlackList.add("net.minecraft.network.play.server.S19PacketEntityHeadLook");
        logBlackList.add("net.minecraft.network.play.server.S12PacketEntityVelocity");
        logBlackList.add("net.minecraft.network.play.server.S00PacketKeepAlive");
        logBlackList.add("net.minecraft.network.play.server.S1CPacketEntityMetadata");
        logBlackList.add("net.minecraft.network.play.server.S03PacketTimeUpdate");
        logBlackList.add("net.minecraft.network.play.server.S18PacketEntityTeleport");
        logBlackList.add("net.minecraft.network.play.server.S19PacketEntityStatus");
        logBlackList.add("net.minecraft.network.play.server.S14PacketEntity.S16PacketEntityLook");
        logBlackList.add("net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove");
        logBlackList.add("net.minecraft.network.play.server.S28PacketEffect");
        logBlackList.add("net.minecraft.network.play.server.S29PacketSoundEffect");
        logBlackList.add("net.minecraft.network.play.server.S2FPacketSetSlot");
        logBlackList.add("net.minecraft.network.play.server.S02PacketChat");
        logBlackList.add("net.minecraft.network.play.server.S1DPacketEntityEffect");
        logBlackList.add("net.minecraft.network.play.server.S21PacketChunkData");
        logBlackList.add("net.minecraft.network.play.server.S26PacketMapChunkBulk");
        logBlackList.add("DragonsRadioMod");
        logBlackList.add("GraviSuite");
        logBlackList.add("ic2");
    }

    private Gui gui;
    
    public PacketHandler(Gui gui) {
        this.gui = gui;
        PacketHandler.newList();
    }

    public boolean handlePacket(Object packet, Side side, List blackList) throws Exception {
        Class packetClass = packet.getClass();
        ArrayList<String> outMessages = new ArrayList<String>();
        String packetClassName = packetClass.getCanonicalName().replaceAll("\\$", ".");
        if (!blackList.contains(packetClassName)) {
            if (side != null)
                outMessages.add("[" + side.toString() + "] " + packetClassName);
            int fieldsCount = 0;
            for (Field f : packetClass.getDeclaredFields())
                if ((f.getModifiers() & Modifier.STATIC) == 0)
                    fieldsCount++;
            int fieldsReady = 0;
            for (int i = 0; i < packetClass.getDeclaredFields().length; i++) {
                Field field = packetClass.getDeclaredFields()[i];
                if ((field.getModifiers() & Modifier.STATIC) != 0)
                    continue;
                fieldsReady++;
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(packet);
                if ((!(fieldValue instanceof String)) || (!blackList.contains((String)fieldValue))) {
                    if (side == null) 
                        continue;
                    String outMessage = "";
                    outMessages.add((fieldsReady == fieldsCount ? "  \u2514 " : "  \u251C ") + fieldName + ": " + fieldValue);
                    if (fieldValue instanceof String[]) {
                        String[] stringArray;
                        for (String s : stringArray = (String[])fieldValue) {
                            outMessage = outMessage + (outMessage.isEmpty() ? "" : ", ") + s;
                        }
                        outMessages.add((fieldsReady == fieldsCount ? "    " : "  \u2502 ") + "   \u2514 String[]: " + outMessage);
                        continue;
                    }
                    if (fieldValue instanceof ByteBuf)
                    {
                        ByteBuf buf = (ByteBuf)fieldValue;
                        for (byte b : buf.array()) {
                            if ((outMessage = outMessage + (int)b + " ").length() <= 80) continue;
                            outMessage = outMessage + "...";
                            break;
                        }
                        outMessages.add((fieldsReady == fieldsCount ? "    " : "  \u2502 ") + "   \u2514 ByteBuf: " + outMessage);
                    }
                    continue;
                }
                return false;
            }
        } else {
            return false;
        }
        if (side != null && ((side == Side.IN && gui.logInPackets.isSelected()) || (side == Side.OUT && gui.logOutPackets.isSelected()))) {
            for (String message : outMessages) {
                gui.logMessage(message);
                if (gui.moreInfo.isSelected()) 
                    continue;
                break;
            }
            gui.logMessage("");
        }
        return true;
    }
}

