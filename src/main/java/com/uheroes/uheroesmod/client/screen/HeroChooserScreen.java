package com.uheroes.uheroesmod.client.screen;

import com.uheroes.uheroesmod.network.HeroSelectPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

public class HeroChooserScreen extends Screen {
    public HeroChooserScreen() {
        super(Component.literal("Choose Your Hero"));
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(Component.literal("BlackS"), (button) -> {
            PacketDistributor.sendToServer(new HeroSelectPayload("blacks"));
            this.onClose();
        }).bounds(this.width / 2 - 50, this.height / 2 - 10, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(0, 0, this.width, this.height, 0x80000000);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
