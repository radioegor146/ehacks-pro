/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.packetprotector.modules;

import ehacks.mod.packetprotector.IPacketProtector;

/**
 *
 * @author radioegor146
 */
public class IC2NuclearControlProtector implements IPacketProtector {

    @Override
    public boolean isPacketOk(Object packet) {
        return true;
    }
    
}
