package com.uheroes.uheroesmod.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StuckInAtomEffect extends MobEffect {
    public StuckInAtomEffect() {
        super(MobEffectCategory.HARMFUL, 0x00FFFF);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("uheroes", "stuck_in_atom_slow"), -10.0f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
