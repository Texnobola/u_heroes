package com.uheroes.uheroesmod.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber(modid = "uheroes")
public class BlackSPassiveHandler {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof Player player) {
            if (player.getPersistentData().getBoolean("is_blacks")) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.SPEED, 40, 0, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 40, 0, true, false));
            }
        }
    }
}
