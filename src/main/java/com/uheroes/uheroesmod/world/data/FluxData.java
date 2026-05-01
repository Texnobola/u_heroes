package com.uheroes.uheroesmod.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class FluxData {
    public static final int MAX_FLUX = 100;
    private static final String FLUX_KEY = "uheroes_flux";

    public static int getFlux(Player player) {
        CompoundTag data = player.getPersistentData();
        if (data.contains(FLUX_KEY)) {
            return data.getInt(FLUX_KEY);
        }
        return MAX_FLUX;
    }

    public static void setFlux(Player player, int amount) {
        CompoundTag data = player.getPersistentData();
        int clamped = Math.max(0, Math.min(amount, MAX_FLUX));
        data.putInt(FLUX_KEY, clamped);
    }

    public static boolean consumeFlux(Player player, int amount) {
        int current = getFlux(player);
        if (current >= amount) {
            setFlux(player, current - amount);
            return true;
        }
        return false;
    }
}
