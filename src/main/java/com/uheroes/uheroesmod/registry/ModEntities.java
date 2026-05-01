package com.uheroes.uheroesmod.registry;

import com.uheroes.uheroesmod.entity.BlackSCloneEntity;
import com.uheroes.uheroesmod.uheroes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = uheroes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, uheroes.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BlackSCloneEntity>> BLACKS_CLONE = ENTITIES.register("blacks_clone",
            () -> EntityType.Builder.of(BlackSCloneEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("blacks_clone")
    );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(BLACKS_CLONE.get(), BlackSCloneEntity.createAttributes().build());
    }
}
