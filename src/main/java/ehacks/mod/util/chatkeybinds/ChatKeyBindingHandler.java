/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.chatkeybinds;

import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author radioegor146
 */
public class ChatKeyBindingHandler {

    public static ChatKeyBindingHandler INSTANCE = new ChatKeyBindingHandler();
    private final HashSet<Integer> pressedKeys = new HashSet<>();
    private final boolean[] keyStates = new boolean[256];
    public ArrayList<ChatKeyBinding> keyBindings = new ArrayList<>();

    public boolean checkAndSaveKeyState(int key) {
        if (Wrapper.INSTANCE.mc().currentScreen != null) {
            return false;
        }
        if (InteropUtils.isKeyDown(key) != this.keyStates[key]) {
            pressedKeys.add(key);
            return InteropUtils.isKeyDown(key);
        }
        return false;
    }

    public void handle() {
        for (ChatKeyBinding keyBinding : keyBindings) {
            if (Wrapper.INSTANCE.world() == null || !this.checkAndSaveKeyState(keyBinding.getKeyCode())) {
                continue;
            }
            keyBinding.press();
        }
        for (int key : pressedKeys) {
            this.keyStates[key] = !this.keyStates[key];
        }
        pressedKeys.clear();
    }
}
