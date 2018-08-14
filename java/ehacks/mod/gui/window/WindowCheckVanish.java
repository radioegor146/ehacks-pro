package ehacks.mod.gui.window;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import static ehacks.mod.gui.window.WindowPlayerIds.useIt;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;

public class WindowCheckVanish 
extends SimpleWindow {        
    public WindowCheckVanish() {
        super("CheckVanish", 600, 300);
        this.canExtend = false;
        this.height = 48;
        this.width = 190;
    }
    
    public static int lastCvResult = -2;
    public static int lastLpResult = -2;
    public static AtomicBoolean cvThreadStarted = new AtomicBoolean(false);
    public static AtomicBoolean lpThreadStarted = new AtomicBoolean(false);
    
    private CVServerRequester cvRequester;
    private LPServerRequester lpRequester;
    
    public static int cvLastUpdate = 0;
    public static int lpLastUpdate = 0;
    
    @Override
    public void draw(int x, int y) {
        super.draw(x, y);
        if (cvLastUpdate > 160)
        {
            cvLastUpdate = 0;
            if (!cvThreadStarted.get())
            {
                cvThreadStarted.set(true);
                cvRequester = new CVServerRequester();
                cvRequester.start();
            }
        }
        if (lpLastUpdate > 160)
        {
            lpLastUpdate = 0;
            if (!lpThreadStarted.get())
            {
                lpThreadStarted.set(true);
                lpRequester = new LPServerRequester();
                lpRequester.start();
            }
        }
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            ServerData serverData = Wrapper.INSTANCE.mc().func_147104_D();
            if (serverData == null)
            {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("You are in singleplayer", this.getX() + 2 + this.dragX, this.getY() + this.dragY + 14 + 2, 5636095);
                return;
            }
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Server IP: " + serverData.serverIP, this.getX() + 2 + this.dragX, this.getY() + this.dragY + 14 + 2, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("TAB-List: " + String.valueOf(Wrapper.INSTANCE.player().sendQueue.playerInfoList.size()), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 14 + 2 + 12, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("PacketQuery: " + (lastCvResult < 0 ? (lastCvResult == -1 ? "Error" : "Not working") : String.valueOf(lastCvResult)) + (cvThreadStarted.get() ? " [U]" : ""), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 14 + 2 + 24, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Legacy: " + (lastLpResult < 0 ? (lastLpResult == -1 ? "Error" : "Not working") : String.valueOf(lastLpResult)) + (lpThreadStarted.get() ? " [U]" : ""), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 14 + 2 + 36, 5636095);
        }
    }
    
    private class CVServerRequester extends Thread
    {
        public CVServerRequester() {}
        
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
            }
            catch (Exception e) {
                lastCvResult = -1;
                //YouAlwaysWinClickGui.log("[PacketQueryCV] Error on updating: " + e.getMessage());
                e.printStackTrace();
            }
            cvThreadStarted.set(false);
	}

    }
    
    private class LPServerRequester extends Thread
    {
        private final byte[] payload = { -2, 1 };
        
        public LPServerRequester() {}
        
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
                Socket clientSocket = new Socket();
                //clientSocket.connect(new InetSocketAddress("n5.streamcraft.net", 25666), 7000);
                clientSocket.connect(new InetSocketAddress(address.getIP(), address.getPort()), 7000);
                clientSocket.setSoTimeout(7000);
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                dos.write(payload, 0, payload.length);
                String[] data = br.readLine().split("\u0000\u0000\u0000");
                clientSocket.close();
                if (data.length < 2)
                {
                    lastLpResult = -2;
                    lpThreadStarted.set(false);
                    return;
                }
                lastLpResult = Integer.valueOf(data[data.length - 2].replace("\u0000", ""));
                //YouAlwaysWinClickGui.log("[LegacyPingCV] Updated");
            } 
            catch(Exception e) {
                lastLpResult = -1;
                //YouAlwaysWinClickGui.log("[LegacyPingCV] Error on updating: " + e.getMessage());
            }
            lpThreadStarted.set(false);
	}

    }
    
    @Override
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = false;
        if (x >= this.getX() + this.dragX + 2 && y >= this.getY() + 14 + this.dragY + 2 && (double)x <= this.getX() + 90 + 100 + this.dragX - 2 && y <= this.getY() + 14 + this.dragY + 12 && button == 0 && this.isOpen() && this.isExtended()) {
            useIt = !useIt;
            retval = true;
        }
        if (x >= this.xPos + 70 + 100 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + 78 + 100 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isPinned = !this.isPinned;
            retval = true;
        }
        if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY && x <= this.xPos + 69 + 100 + this.dragX && y <= this.yPos + 12 + this.dragY) {
            EHacksClickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.lastDragX = x - this.dragX;
            this.lastDragY = y - this.dragY;
            retval = true;
        }
        return retval;
    }
}