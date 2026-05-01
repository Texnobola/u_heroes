package com.uheroes.uheroesmod.client.event;

import com.uheroes.uheroesmod.network.SizeChangePayload;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "uheroes", value = Dist.CLIENT)
public class KeybindManager {

    public static final KeyMapping GROW_KEY = new KeyMapping("key.uheroes.grow", GLFW.GLFW_KEY_G, "key.categories.uheroes");
    public static final KeyMapping SHRINK_KEY = new KeyMapping("key.uheroes.shrink", GLFW.GLFW_KEY_H, "key.categories.uheroes");

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (GROW_KEY.consumeClick()) {
            PacketDistributor.sendToServer(new SizeChangePayload(true));
        }
        while (SHRINK_KEY.consumeClick()) {
            PacketDistributor.sendToServer(new SizeChangePayload(false));
        }
    }

    @EventBusSubscriber(modid = "uheroes", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(GROW_KEY);
            event.register(SHRINK_KEY);
        }
    }
}
