package com.uheroes.uheroesmod.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import com.uheroes.uheroesmod.network.SyncHeroDataPayload;

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
        syncToClient(player);
    }

    public static void syncToClient(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            boolean isBlacks = player.getPersistentData().getBoolean("is_blacks");
            int flux = getFlux(player);
            PacketDistributor.sendToPlayer(serverPlayer, new SyncHeroDataPayload(isBlacks, flux));
        }
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
