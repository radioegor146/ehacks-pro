/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.packetprotector;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.packetprotector.modules.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author radioegor146
 */
public class MainProtector {

    public static MainProtector INSTANCE = new MainProtector();

    public List<IPacketProtector> protectors = new ArrayList();

    public void init() {
        protectors.clear();

        protectors.add(new DragonsRadioProtector());
        protectors.add(new IC2NuclearControlProtector());

        EHacksClickGui.log("[PacketProtector] Inited " + protectors.size() + " protectors");
    }

    public boolean isPacketOk(Object packet) {
        boolean ok = true;
        for (IPacketProtector protector : protectors) {
            ok &= protector.isPacketOk(packet);
        }
        return ok;
    }
}
