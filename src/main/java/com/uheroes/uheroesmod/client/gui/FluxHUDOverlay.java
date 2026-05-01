package com.uheroes.uheroesmod.client.gui;

import com.uheroes.uheroesmod.world.data.FluxData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;

public class FluxHUDOverlay implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;

        if (!player.getPersistentData().getBoolean("is_blacks")) {
            return;
        }

        int flux = FluxData.getFlux(player);
        String text = "Flux: " + flux + " / 100";
        
        int x = 10;
        int y = minecraft.getWindow().getGuiScaledHeight() - 20;

        guiGraphics.drawString(minecraft.font, text, x, y, 0x00FFFF);
    }
}
