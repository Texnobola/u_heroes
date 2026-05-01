package com.uheroes.uheroesmod.registry;

import com.uheroes.uheroesmod.effect.StuckInAtomEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "uheroes");

    public static final DeferredHolder<MobEffect, StuckInAtomEffect> STUCK_IN_ATOM = EFFECTS.register("stuck_in_atom", () -> new StuckInAtomEffect());

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
