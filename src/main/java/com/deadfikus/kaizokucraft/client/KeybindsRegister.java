package com.deadfikus.kaizokucraft.client;

import com.deadfikus.kaizokucraft.ModMain;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeybindsRegister {
    private static final String s = "key." + ModMain.MODID + ".";
    public static final String category = "KaizokuCraft";
    public static final KeyBinding[] abilityUses = new KeyBinding[5];
    public static final KeyBinding questsMenu;


    static {
        for (int i = 0; i < 5; i++) {
            abilityUses[i] = new KeyBinding(s + "ability" + (i+1), 49 + i, category);
        }
        questsMenu = new KeyBinding(s + "quests_menu", 79, category);
    }

    public static void register() {
        for (net.minecraft.client.settings.KeyBinding key :
                abilityUses) {
            ClientRegistry.registerKeyBinding(key);
        }
        ClientRegistry.registerKeyBinding(questsMenu);

    }

}

