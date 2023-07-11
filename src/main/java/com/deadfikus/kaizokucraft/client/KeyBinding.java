package com.deadfikus.kaizokucraft.client;

public class KeyBinding extends net.minecraft.client.settings.KeyBinding {

    private boolean pressHandled;

    public KeyBinding(String name, int key, String category) {
        super(name, key, category);
    }

    public boolean getPressed() {
        if (isDown() && !pressHandled) {
            pressHandled = true;
            return true;
        }
        if (!isDown()) {
            pressHandled = false;
        }
        return false;
    }
}
