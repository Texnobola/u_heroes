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

        int gfCooldown = player.getPersistentData().getInt("giant_fist_cooldown");
        String gfText = "Giant Fist: " + (gfCooldown > 0 ? (gfCooldown / 20) + "s" : "READY");
        guiGraphics.drawString(minecraft.font, gfText, x, y - 10, gfCooldown > 0 ? 0xFF0000 : 0x00FF00);

        int lsCooldown = player.getPersistentData().getInt("long_slap_cooldown");
        String lsText = "Long Slap: " + (lsCooldown > 0 ? (lsCooldown / 20) + "s" : "READY");
        guiGraphics.drawString(minecraft.font, lsText, x, y - 20, lsCooldown > 0 ? 0xFF0000 : 0x00FF00);
    }
}
