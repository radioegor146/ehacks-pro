/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.packetprotector;

import ehacks.mod.wrapper.PacketHandler;

/**
 *
 * @author radioegor146
 */
public interface IPacketProtector {

    boolean isPacketOk(Object packet, PacketHandler.Side side);
}
