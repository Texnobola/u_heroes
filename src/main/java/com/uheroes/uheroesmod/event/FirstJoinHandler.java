package com.uheroes.uheroesmod.event;

import com.uheroes.uheroesmod.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = "uheroes")
public class FirstJoinHandler {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean("received_hero_chooser")) {
                player.addItem(ModItems.HERO_CHOOSER.get().getDefaultInstance());
                data.putBoolean("received_hero_chooser", true);
            }
        }
    }
}
