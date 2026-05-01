package com.uheroes.uheroesmod.client.event;

import com.uheroes.uheroesmod.client.gui.FluxHUDOverlay;
import com.uheroes.uheroesmod.client.renderer.BlackSCloneRenderer;
import com.uheroes.uheroesmod.registry.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = "uheroes", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath("uheroes", "flux_hud"), new FluxHUDOverlay());
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLACKS_CLONE.get(), BlackSCloneRenderer::new);
    }
}
