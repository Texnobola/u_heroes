package com.uheroes.uheroesmod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "uheroes", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class uheroesClient {

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }
}
