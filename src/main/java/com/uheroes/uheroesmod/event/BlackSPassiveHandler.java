package com.uheroes.uheroesmod.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = "uheroes")
public class BlackSPassiveHandler {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (player.getPersistentData().getBoolean("is_blacks")) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false));
            }
        }
    }
}
