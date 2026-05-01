package com.uheroes.uheroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record HeroSelectPayload(String heroId) implements CustomPacketPayload {
    public static final Type<HeroSelectPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("uheroes", "hero_select"));
    public static final StreamCodec<ByteBuf, HeroSelectPayload> CODEC = ByteBufCodecs.STRING_UTF8.map(HeroSelectPayload::new, HeroSelectPayload::heroId);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
