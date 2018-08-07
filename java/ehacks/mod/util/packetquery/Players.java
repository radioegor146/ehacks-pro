/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.packetquery;

import java.util.List;

/**
 *
 * @author radioegor146
 */

    
public class Players {
    private int max;
    private int online;
    private List<Player> sample;

    public int getMax() {
        return max;
    }

    public int getOnline() {
        return online;
    }

    public List<Player> getSample() {
        return sample;
    }        
}