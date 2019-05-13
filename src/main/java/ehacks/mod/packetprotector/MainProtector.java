/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.packetprotector;

import ehacks.mod.packetprotector.modules.*;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.PacketHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author radioegor146
 */
public class MainProtector {

    public List<IPacketProtector> protectors = new ArrayList();

    public void init() {
        protectors.clear();

        protectors.add(new DragonsRadioProtector());
        protectors.add(new IC2NuclearControlProtector());
        protectors.add(new NanoNikProtector());

        InteropUtils.log("Inited " + protectors.size() + " protectors", "PacketProtector");
    }

    public boolean isPacketOk(Object packet, PacketHandler.Side side) {
        boolean ok = true;
        ok = protectors.stream().map((protector) -> protector.isPacketOk(packet, side)).reduce(ok, (accumulator, _item) -> accumulator & _item);
        return ok;
    }
}
