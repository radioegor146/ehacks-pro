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
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;

/**
 *
 * @author radioegor146
 */
public class NoLimitDamage extends Mod {

    public NoLimitDamage() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitDamage";
    }

    public boolean lastPressed = false;
    
    @Override
    public void onMouse(MouseEvent event) {
        if (Wrapper.INSTANCE.mc().objectMouseOver.entityHit != null && event.button == 0 && !lastPressed)
        {
            int playerId = Wrapper.INSTANCE.player().getEntityId();
            int entityId = Wrapper.INSTANCE.mc().objectMouseOver.entityHit.getEntityId();
            int dimensionId = Wrapper.INSTANCE.player().dimension;
            ByteBuf buf = Unpooled.buffer();
            buf.writeByte(0);
            buf.writeInt(entityId);
            buf.writeInt(playerId);
            buf.writeInt(dimensionId);
            buf.writeFloat(10000);
            buf.writeBoolean(false);
            C17PacketCustomPayload packet = new C17PacketCustomPayload("taintedmagic", buf);
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
            lastPressed = true;
        }
        if (lastPressed && event.button == 0 && !event.buttonstate)
            lastPressed = false;
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("taintedmagic.common.network.PacketKatanaAttack").getConstructor();
        }
        catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[NoLimitDamage] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }
}
