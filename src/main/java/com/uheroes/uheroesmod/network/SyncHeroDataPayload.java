package com.uheroes.uheroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SyncHeroDataPayload(boolean isBlacks, int flux) implements CustomPacketPayload {
    public static final Type<SyncHeroDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("uheroes", "sync_hero_data"));
    
    public static final StreamCodec<ByteBuf, SyncHeroDataPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, SyncHeroDataPayload::isBlacks,
        ByteBufCodecs.INT, SyncHeroDataPayload::flux,
        SyncHeroDataPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
