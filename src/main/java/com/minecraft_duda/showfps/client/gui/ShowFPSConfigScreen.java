package com.minecraft_duda.showfps.client.gui;

import com.minecraft_duda.showfps.client.ShowFPSClient;
import com.minecraft_duda.showfps.client.config.ShowFPSConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ShowFPSConfigScreen extends Screen {
    private final Screen parent;
    private final ShowFPSConfig config;

    public ShowFPSConfigScreen(Screen parent) {
        super(Component.translatable("screen.showfps.config"));
        this.parent = parent;
        this.config = ShowFPSClient.getInstance().getConfig();
    }

    @Override
    protected void init() {
        int buttonWidth = 150;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int y = 40; // 留出标题空间

        // 标题
        this.addRenderableWidget(new StringWidget(
                centerX - 50, 20, 100, 10,
                this.title,
                this.font
        ));

        y += 10; // 和标题隔开

        this.addRenderableWidget(
                CycleButton.onOffBuilder(config.enabled)
                        .create(centerX - buttonWidth / 2, y, buttonWidth, buttonHeight,
                                Component.translatable("option.showfps.enabled"),
                                (button, value) -> config.enabled = value)
        );
        y += 25;

        this.addRenderableWidget(
                CycleButton.onOffBuilder(config.showBackground)
                        .create(centerX - buttonWidth / 2, y, buttonWidth, buttonHeight,
                                Component.translatable("option.showfps.background"),
                                (button, value) -> config.showBackground = value)
        );
        y += 25;

        this.addRenderableWidget(
                CycleButton.onOffBuilder(config.colorByFps)
                        .create(centerX - buttonWidth / 2, y, buttonWidth, buttonHeight,
                                Component.translatable("option.showfps.color_by_fps"),
                                (button, value) -> config.colorByFps = value)
        );
        y += 35;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> {
            saveAndClose();
        }).bounds(centerX - 100, y, 200, 20).build());
    }

    private void saveAndClose() {
        ShowFPSClient.getInstance().saveConfig();
        if (this.minecraft != null) {
            this.minecraft.setScreen(parent);
        }
    }

    // 不需要覆盖 render，一切交给父类
}