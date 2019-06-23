/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.chatkeybinds;

import ehacks.mod.wrapper.Wrapper;

/**
 * @author radioegor146
 */
public class ChatKeyBinding implements Comparable {

    private final String command;
    private int keyCode;

    public ChatKeyBinding(String command, int keyCode) {
        this.command = command;
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCodeDefault() {
        return 0;
    }

    public String getKeyDescription() {
        return this.command;
    }

    public void press() {
        Wrapper.INSTANCE.player().sendChatMessage(command);
    }

    @Override
    public int compareTo(Object o) {
        return this.getKeyDescription().compareTo(((ChatKeyBinding) o).getKeyDescription());
    }
}
