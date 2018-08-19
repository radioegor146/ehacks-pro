/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import ehacks.api.module.Module;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;

/**
 *
 * @author radioegor146
 */
public class MetaHackAdd extends Module {

    public MetaHackAdd() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "MetaHack-Add";
    }    
    
    @Override
    public String getDescription() {
        return "Allows you to add 1 to metadata of item";
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.riciJak.Ztones.network.ToggleMetaData");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.riciJak.Ztones.network.ToggleMetaData").getConstructor(Boolean.TYPE);
            ByteBuf buf = Unpooled.buffer(0);
            buf.writeInt(0);
            buf.writeBoolean(true);
            C17PacketCustomPayload packet = new C17PacketCustomPayload("Ztones", buf);
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
            this.off();
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[MetaHack-Add] Not working");
        }
    }
    
    @Override
    public String getModName() {
        return "ZTones";
    }
}
