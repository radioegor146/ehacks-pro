package ehacks.mod.gui.window;

import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.ServerListPing17;
import ehacks.mod.util.packetquery.StatusResponse;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;

public class WindowCheckVanish
        extends SimpleWindow {

    public WindowCheckVanish() {
        super("CheckVanish", 600, 300);
        this.canExtend = false;
        this.setClientSize(190, 46);
    }

    public static int lastCvResult = -2;
    public static int lastLpResult = -2;
    public static AtomicBoolean cvThreadStarted = new AtomicBoolean(false);
    public static AtomicBoolean lpThreadStarted = new AtomicBoolean(false);

    private CVServerRequester cvRequester;
    private LPServerRequester lpRequester;

    public static int cvLastUpdate = 0;
    public static int lpLastUpdate = 0;
	public static int tabList = 0;

    @Override
    public void draw(int x, int y) {
        super.draw(x, y);
        if (cvLastUpdate > 160) {
            cvLastUpdate = 0;
            if (!cvThreadStarted.get()) {
                cvThreadStarted.set(true);
                cvRequester = new CVServerRequester();
                cvRequester.start();
            }
        }
        if (lpLastUpdate > 160) {
            lpLastUpdate = 0;
            if (!lpThreadStarted.get()) {
                lpThreadStarted.set(true);
                lpRequester = new LPServerRequester();
                lpRequester.start();
            }
        }
        if (this.isOpen()) {
			tabList = Wrapper.INSTANCE.player().sendQueue.playerInfoList.size();
            ServerData serverData = Wrapper.INSTANCE.mc().func_147104_D();
            if (serverData == null) {
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("You are in singleplayer", this.getClientX() + 1, this.getClientY() + 1, GLUtils.getColor(255, 255, 255));
                return;
            }
			if(tabList>=lastCvResult||tabList>=lastLpResult) {
				super.setColor(96,96,96);
			} else {
				super.setColor(255,0,0);
			}
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Server IP: " + serverData.serverIP, this.getClientX() + 1, this.getClientY() + 1, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("TAB-List: " + String.valueOf(tabList), this.getClientX() + 1, this.getClientY() + 13, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("PacketQuery: " + (lastCvResult < 0 ? (lastCvResult == -1 ? "Error" : "Not working") : String.valueOf(lastCvResult)) + (cvThreadStarted.get() ? " [U]" : ""), this.getClientX() + 1, this.getClientY() + 25, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Legacy: " + (lastLpResult < 0 ? (lastLpResult == -1 ? "Error" : "Not working") : String.valueOf(lastLpResult)) + (lpThreadStarted.get() ? " [U]" : ""), this.getClientX() + 1, this.getClientY() + 37, GLUtils.getColor(255, 255, 255));
        }
    }

    private class CVServerRequester extends Thread {

        public CVServerRequester() {
        }

        @Override
        public void run() {
            //YouAlwaysWinClickGui.log("[PacketQueryCV] Updating");
            ServerData serverData = Wrapper.INSTANCE.mc().func_147104_D();
            if (serverData == null) {
                lastCvResult = -2;
                cvThreadStarted.set(false);
                return;
            }
            ServerAddress address = ServerAddress.func_78860_a(serverData.serverIP);
            try {
                ServerListPing17 pinger = new ServerListPing17();
                //pinger.setAddress(new InetSocketAddress("n5.streamcraft.net", 25666));
                pinger.setAddress(new InetSocketAddress(address.getIP(), address.getPort()));
                StatusResponse response = pinger.fetchData();
                lastCvResult = response.getPlayers().getOnline();
                //YouAlwaysWinClickGui.log("[PacketQueryCV] Updated");
            } catch (Exception e) {
                lastCvResult = -1;
                //YouAlwaysWinClickGui.log("[PacketQueryCV] Error on updating: " + e.getMessage());
            }
            cvThreadStarted.set(false);
        }

    }

    private class LPServerRequester extends Thread {

        private final byte[] payload = {-2, 1};

        public LPServerRequester() {
        }

        @Override
        public void run() {
            //YouAlwaysWinClickGui.log("[LegacyPingCV] Updating");
            ServerData serverData = Wrapper.INSTANCE.mc().func_147104_D();
            if (serverData == null) {
                lastLpResult = -2;
                lpThreadStarted.set(false);
                return;
            }
            ServerAddress address = ServerAddress.func_78860_a(serverData.serverIP);
            try {
                String[] data;
                //clientSocket.connect(new InetSocketAddress("n5.streamcraft.net", 25666), 7000);
                try (Socket clientSocket = new Socket()) {
                    //clientSocket.connect(new InetSocketAddress("n5.streamcraft.net", 25666), 7000);
                    clientSocket.connect(new InetSocketAddress(address.getIP(), address.getPort()), 7000);
                    clientSocket.setSoTimeout(7000);
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    dos.write(payload, 0, payload.length);
                    data = br.readLine().split("\u0000\u0000\u0000");
                }
                if (data.length < 2) {
                    lastLpResult = -2;
                    lpThreadStarted.set(false);
                    return;
                }
                lastLpResult = Integer.valueOf(data[data.length - 2].replace("\u0000", ""));
                //YouAlwaysWinClickGui.log("[LegacyPingCV] Updated");
            } catch (Exception e) {
                lastLpResult = -1;
                //YouAlwaysWinClickGui.log("[LegacyPingCV] Error on updating: " + e.getMessage());
            }
            lpThreadStarted.set(false);
        }

    }
}
