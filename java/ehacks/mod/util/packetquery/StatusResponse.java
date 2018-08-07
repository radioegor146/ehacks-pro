package ehacks.mod.util.packetquery;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author radioegor146
 */
    
public class StatusResponse {
    private String description;
    private Players players;
    private Version version;
    private String favicon;
    private int time;

    public String getDescription() {
        return description;
    }

    public Players getPlayers() {
        return players;
    }

    public Version getVersion() {
        return version;
    }

    public String getFavicon() {
        return favicon;
    }

    public int getTime() {
        return time;
    }      

    public void setTime(int time) {
        this.time = time;
    }

}
