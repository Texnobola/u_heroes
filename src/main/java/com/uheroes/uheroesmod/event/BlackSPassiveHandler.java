package com.uheroes.uheroesmod.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

@EventBusSubscriber(modid = "uheroes")
public class BlackSPassiveHandler {

    public static final ResourceLocation MINI_HEALTH_ID = ResourceLocation.fromNamespaceAndPath("uheroes", "mini_health_reduction");

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (player.getPersistentData().getBoolean("is_blacks")) {
                ScaleData scaleData = ScaleTypes.BASE.getScaleData(player);
                float currentScale = scaleData.getTargetScale();

                // Baseline effects that are ALWAYS applied
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 1, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1, true, false));

                // Dynamic effects based on size
                if (Math.abs(currentScale - 1.0f) <= 0.01f) {
                    // Normal size
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false));
                } else if (currentScale > 1.0f) {
                    // Giant
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, true, false));
                } else {
                    // Mini variations
                    if (currentScale <= 0.11f) {
                        // Max Mini: Speed 40, Strength 1, No Resistance
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 39, true, false));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, false));
                    } else {
                        // Mid Mini
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 9, true, false));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 1, true, false));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false));
                    }
                }

                // Dynamic Max Health modifier
                AttributeInstance healthAttr = player.getAttribute(Attributes.MAX_HEALTH);
                if (healthAttr != null) {
                    if (currentScale <= 0.11f) {
                        if (healthAttr.getModifier(MINI_HEALTH_ID) == null) {
                            healthAttr.addTransientModifier(new AttributeModifier(MINI_HEALTH_ID, -10.0, AttributeModifier.Operation.ADD_VALUE));
                            if (player.getHealth() > player.getMaxHealth()) {
                                player.setHealth(player.getMaxHealth());
                            }
                        }
                    } else {
                        if (healthAttr.getModifier(MINI_HEALTH_ID) != null) {
                            healthAttr.removeModifier(MINI_HEALTH_ID);
                        }
                    }
                }
            }
        }
    }
}
