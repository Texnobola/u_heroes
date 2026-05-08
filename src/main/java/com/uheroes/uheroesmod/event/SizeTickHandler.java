package com.uheroes.uheroesmod.event;

import com.uheroes.uheroesmod.registry.ModEffects;
import com.uheroes.uheroesmod.world.data.FluxData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = "uheroes")
public class SizeTickHandler {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (player.getPersistentData().getBoolean("is_blacks")) {
                ScaleData scaleData = ScaleTypes.BASE.getScaleData(player);
                float currentScale = scaleData.getTargetScale();

                if (player.tickCount % 20 == 0) {
                    if (Math.abs(currentScale - 1.0f) > 0.01f) {
                        if (!FluxData.consumeFlux(player, 5)) {
                            scaleData.setTargetScale(1.0f);
                            scaleData.setScaleTickDelay(40);
                            player.addEffect(new MobEffectInstance(ModEffects.STUCK_IN_ATOM, 400, 0, false, true, true));
                        }
                    } else {
                        int flux = FluxData.getFlux(player);
                        if (flux < FluxData.MAX_FLUX) {
                            FluxData.setFlux(player, flux + 2);
                        }
                    }
                }

                // Decrement clone cooldown
                int cooldown = player.getPersistentData().getInt("clone_cooldown");
                if (cooldown > 0) {
                    player.getPersistentData().putInt("clone_cooldown", cooldown - 1);
                }

                // Decrement attack cooldowns
                int gfCooldown = player.getPersistentData().getInt("giant_fist_cooldown");
                if (gfCooldown > 0) {
                    player.getPersistentData().putInt("giant_fist_cooldown", gfCooldown - 1);
                }

                int lsCooldown = player.getPersistentData().getInt("long_slap_cooldown");
                if (lsCooldown > 0) {
                    player.getPersistentData().putInt("long_slap_cooldown", lsCooldown - 1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        
        // Copy persistent data
        newPlayer.getPersistentData().putBoolean("is_blacks", oldPlayer.getPersistentData().getBoolean("is_blacks"));
        newPlayer.getPersistentData().putInt("uheroes_flux", oldPlayer.getPersistentData().getInt("uheroes_flux"));
        newPlayer.getPersistentData().putInt("clone_cooldown", oldPlayer.getPersistentData().getInt("clone_cooldown"));
        newPlayer.getPersistentData().putInt("giant_fist_cooldown", oldPlayer.getPersistentData().getInt("giant_fist_cooldown"));
        newPlayer.getPersistentData().putInt("long_slap_cooldown", oldPlayer.getPersistentData().getInt("long_slap_cooldown"));
    }
}
