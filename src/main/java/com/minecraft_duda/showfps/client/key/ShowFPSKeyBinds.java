package com.minecraft_duda.showfps.client.key;

import com.minecraft_duda.showfps.client.gui.ShowFPSConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class ShowFPSKeyBinds {
    public static final KeyMapping OPEN_CONFIG = new KeyMapping(
            "key.showfps.open_config",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            KeyMapping.Category.MISC
    );

    public static void register() {
        KeyMappingHelper.registerKeyMapping(OPEN_CONFIG);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_CONFIG.consumeClick()) {
                client.setScreen(new ShowFPSConfigScreen(client.screen));
            }
        });
    }
}