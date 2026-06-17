package com.minecraft_duda.showfps.client;

import com.minecraft_duda.showfps.ShowFPS;
import com.minecraft_duda.showfps.client.config.ShowFPSConfig;
import com.minecraft_duda.showfps.client.key.ShowFPSKeyBinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ShowFPSClient implements ClientModInitializer {
    private static final int PADDING = 10;
    private static ShowFPSClient instance;
    private ShowFPSConfig config;

    @Override
    public void onInitializeClient() {
        instance = this;
        config = ShowFPSConfig.load();
        ShowFPSKeyBinds.register();

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(ShowFPS.MOD_ID, "fps_counter"),
                this::renderFpsHud
        );
    }

    private void renderFpsHud(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (!config.enabled) return;

        deltaTracker.getGameTimeDeltaTicks();

        Minecraft client = Minecraft.getInstance();
        if (client.getDebugOverlay().showDebugScreen()) return;

        int fps = client.getFps();
        String fpsText = fps + " FPS";

        int color = 0xFFFFFFFF;
        if (config.colorByFps) {
            if (fps < 60) color = 0xFFFF5555;
            else if (fps <= 120) color = 0xFF55FF55;
            else color = 0xFF5555FF;
        }

        Font font = client.font;
        int textWidth = font.width(fpsText);
        int textHeight = font.lineHeight;
        int x = PADDING;
        int y = PADDING;

        if (config.showBackground) {
            graphics.fill(x - 2, y - 2, x + textWidth + 2, y + textHeight + 2, 0x80000000);
        }

        graphics.text(font, Component.literal(fpsText), x, y, color, true);
    }

    public static ShowFPSClient getInstance() { return instance; }
    public ShowFPSConfig getConfig() { return config; }
    public void saveConfig() { config.save(); }
}
