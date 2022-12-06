package ehacks.mod.gui.window;

import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.ServerListPing17;
import ehacks.mod.util.packetquery.StatusResponse;
import ehacks.mod.wrapper.Wrapper;
import ehacks.mod.gui.EHacksClickGui;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import ehacks.mod.util.InteropUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.GuiPlayerInfo;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import java.util.HashMap;
import java.util.Map;

public class WindowCheckVanish
        extends SimpleWindow {

    public WindowCheckVanish() {
        super("CheckVanish", 600, 300);
        this.canExtend = false;
        this.setClientSize(190, 58);
    }

    public static List<String> lastCvResult = new ArrayList<String>();
    public static List<String> currentCvResult = new ArrayList<String>();
    public static int lastCvCount = 0;
    public static int currentCvCount = 0;
    public static int lastLpResult = -2;
    public static AtomicBoolean cvThreadStarted = new AtomicBoolean(false);
    public static AtomicBoolean lpThreadStarted = new AtomicBoolean(false);

    private CVServerRequester cvRequester;
    private LPServerRequester lpRequester;

    public static int cvLastUpdate = 0;
    public static int lpLastUpdate = 0;
	public static List<String> tabList = new ArrayList<String>();
	public static List<String> oldTabList = new ArrayList<String>();
	
	public static HashMap<String, VanishStatus> Vanished = new HashMap<String, VanishStatus>();
	
	public static void updateVanishList() {
		for(String p : tabList) // Every player in the tablist are not vanished
            Vanished.remove(p);
		for(String p : currentCvResult) // Confirm every vanished player that are in packet query
            if (!tabList.contains(p))
                Vanished.put(p, VanishStatus.Confirmed);
        if (tabList.size() < oldTabList.size() && currentCvCount >= lastCvCount) // Assume that all the players that disppeared from tablist are vanished
            for(String assumption : oldTabList.stream().filter(x -> !tabList.contains(x)).collect(Collectors.toList()))
                if (!Vanished.containsKey(assumption))
                    Vanished.put(assumption, VanishStatus.Assumption);
        if (currentCvCount < lastCvCount && tabList.size() == oldTabList.size()) // Vanished player(s) left the game
        {
            int leftVanishes = 0;
            for(Map.Entry<String, VanishStatus> v : Vanished.entrySet()) // Recheck all confirmed entries
                if (lastCvResult.contains(v.getKey()) && !currentCvResult.contains(v.getKey()))
                {
                    Vanished.remove(v.getKey());
                    leftVanishes++;
                }
                else if(!currentCvResult.contains(v.getKey())) Vanished.put(v.getKey(), VanishStatus.Assumption);
            if (currentCvCount < lastCvCount - leftVanishes) // We cannot resolve conflicting assumptions so just remove them all
                for(Map.Entry<String, VanishStatus> s : Vanished.entrySet().stream().filter(x -> x.getValue() == VanishStatus.Assumption).collect(Collectors.toList()))
                    Vanished.remove(s.getKey());
        }
        lastCvCount = currentCvCount;
        oldTabList = tabList;
        lastCvResult = currentCvResult;
	}

    @Override
    public void draw(int x, int y) {
        super.draw(x, y);
		tabList = ((List<GuiPlayerInfo>)Wrapper.INSTANCE.player().sendQueue.playerInfoList).stream().map(r -> r.name).collect(Collectors.toList());
        if (cvLastUpdate > 160 || tabList.size() != oldTabList.size()) {
            cvLastUpdate = 0;
            if (!cvThreadStarted.get()) {
                cvThreadStarted.set(true);
                cvRequester = new CVServerRequester();
                cvRequester.start();
            }
        }
        if (lpLastUpdate > 160 || tabList.size() != oldTabList.size()) {
            lpLastUpdate = 0;
            if (!lpThreadStarted.get()) {
                lpThreadStarted.set(true);
                lpRequester = new LPServerRequester();
                lpRequester.start();
            }
        }
        if (this.isOpen()) {
            ServerData serverData = Wrapper.INSTANCE.mc().func_147104_D();
            if (serverData == null) {
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("You are in singleplayer", this.getClientX() + 1, this.getClientY() + 1, GLUtils.getColor(255, 255, 255));
                return;
            }
			if(Vanished.size() == 0 && tabList.size() >= lastLpResult) {
				super.setColor(96,96,96);
			} else {
				super.setColor(255,0,0);
			}
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Server IP: " + serverData.serverIP, this.getClientX() + 1, this.getClientY() + 1, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("TAB-List: " + String.valueOf(tabList.size()), this.getClientX() + 1, this.getClientY() + 13, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("PacketQuery: " + (lastCvCount < 0 ? (lastCvCount == -1 ? "Error" : "Not working") : String.valueOf(lastCvCount)) + (cvThreadStarted.get() ? " [U]" : ""), this.getClientX() + 1, this.getClientY() + 25, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Legacy: " + (lastLpResult < 0 ? (lastLpResult == -1 ? "Error" : "Not working") : String.valueOf(lastLpResult)) + (lpThreadStarted.get() ? " [U]" : ""), this.getClientX() + 1, this.getClientY() + 37, GLUtils.getColor(255, 255, 255));
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("List of vanished players:", this.getClientX() + 1, this.getClientY() + 49, GLUtils.getColor(255, 255, 255));
			int count = 0;
			for (Map.Entry<String, VanishStatus> name : Vanished.entrySet()) {
                int xPosition = this.getClientX() + 1;
                int yPosition = this.getClientY() + 61 + 12 * count + 1;
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(name.getKey() + (name.getValue() == VanishStatus.Assumption ? "[?]" : ""), xPosition, yPosition, EHacksClickGui.mainColor);
                ++count;
            }
			this.setClientSize(190, 58 + 12 * count);
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
                lastCvCount = -2;
                cvThreadStarted.set(false);
                return;
            }
            ServerAddress address = ServerAddress.func_78860_a(serverData.serverIP);
            try {
                ServerListPing17 pinger = new ServerListPing17();
                //pinger.setAddress(new InetSocketAddress("n5.streamcraft.net", 25666));
                pinger.setAddress(new InetSocketAddress(address.getIP(), address.getPort()));
                StatusResponse response = pinger.fetchData();
				currentCvResult = response.getPlayers().getSample().stream().map(r -> r.getName()).collect(Collectors.toList());
				currentCvCount = response.getPlayers().getOnline();
                //YouAlwaysWinClickGui.log("[PacketQueryCV] Updated");
            } catch (Exception e) {
                lastCvCount = -1;
                //YouAlwaysWinClickGui.log("[PacketQueryCV] Error on updating: " + e.getMessage());
            }
            cvThreadStarted.set(false);
			updateVanishList();
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
	
	public enum VanishStatus {
		Confirmed, Assumption
	}
}
