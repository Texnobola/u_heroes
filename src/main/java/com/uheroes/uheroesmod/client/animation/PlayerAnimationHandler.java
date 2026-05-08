package com.uheroes.uheroesmod.client.animation;

import dev.kosmx.playerAnim.api.IPlayable;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
public class PlayerAnimationHandler {

    private static final ResourceLocation ATTACK_LAYER_ID = ResourceLocation.fromNamespaceAndPath("uheroes", "attack_layer");

    public static void registerFactory() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            ATTACK_LAYER_ID,
            1000,
            (AbstractClientPlayer player) -> {
                return new ModifierLayer<IAnimation>();
            }
        );
    }

    public static void playAnimation(AbstractClientPlayer player, String animationName) {
        var associatedData = PlayerAnimationAccess.getPlayerAssociatedData(player);
        if (associatedData != null) {
            Object layerObj = associatedData.get(ATTACK_LAYER_ID);
            if (layerObj instanceof ModifierLayer<?> layer) {
                ResourceLocation animLocation = ResourceLocation.fromNamespaceAndPath("uheroes", animationName);
                IPlayable playable = PlayerAnimationRegistry.getAnimation(animLocation);
                
                if (playable != null) {
                    ((ModifierLayer<IAnimation>) layer).setAnimation(playable.playAnimation());
                }
            }
        }
    }
}
