package com.uheroes.uheroesmod.network;

import com.uheroes.uheroesmod.registry.ModItems;
import com.uheroes.uheroesmod.world.data.FluxData;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

@EventBusSubscriber(modid = "uheroes", bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("uheroes");
        registrar.playToServer(HeroSelectPayload.TYPE, HeroSelectPayload.CODEC, ModNetworking::handleHeroSelect);
        registrar.playToClient(SyncHeroDataPayload.TYPE, SyncHeroDataPayload.CODEC, ModNetworking::handleSyncHeroData);
        registrar.playToServer(SizeChangePayload.TYPE, SizeChangePayload.CODEC, ModNetworking::handleSizeChange);
    }

    private static void handleSyncHeroData(final SyncHeroDataPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                player.getPersistentData().putBoolean("is_blacks", payload.isBlacks());
                player.getPersistentData().putInt("uheroes_flux", payload.flux());
            }
        });
    }

    private static void handleHeroSelect(final HeroSelectPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (payload.heroId().equals("blacks")) {
                player.getPersistentData().putBoolean("is_blacks", true);
                
                ItemStack stack = player.getMainHandItem();
                if (!stack.is(ModItems.HERO_CHOOSER.get())) {
                    stack = player.getOffhandItem();
                }
                
                if (stack.is(ModItems.HERO_CHOOSER.get())) {
                    stack.shrink(1);
                }

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), 
                        SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.sendSystemMessage(Component.literal("You have awakened as BlackS."));
                FluxData.syncToClient(player);
            }
        });
    }

    private static final float[] SIZES = {0.1f, 0.5f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f};

    private static void handleSizeChange(final SizeChangePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null && player.getPersistentData().getBoolean("is_blacks")) {
                ScaleData scaleData = ScaleTypes.BASE.getScaleData(player);
                float currentScale = scaleData.getTargetScale();
                float newScale = currentScale;
                
                if (payload.isGrowing()) {
                    for (float size : SIZES) {
                        if (size > currentScale + 0.01f) {
                            newScale = size;
                            break;
                        }
                    }
                } else {
                    for (int i = SIZES.length - 1; i >= 0; i--) {
                        if (SIZES[i] < currentScale - 0.01f) {
                            newScale = SIZES[i];
                            break;
                        }
                    }
                }
                
                scaleData.setTargetScale(newScale);
                scaleData.setScaleTickDelay(100);
            }
        });
    }
}
