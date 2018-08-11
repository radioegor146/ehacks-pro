/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.ultimatelogger;

import ehacks.mod.wrapper.Wrapper;

/**
 *
 * @author radioegor146
 */
public class ServerLoginData {
    public SessionInfo session = new SessionInfo();
    public String serverip = Wrapper.INSTANCE.mc().func_147104_D() == null ? "single" : Wrapper.INSTANCE.mc().func_147104_D().serverIP;
}