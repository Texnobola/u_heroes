package com.uheroes.uheroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SizeChangePayload(boolean isGrowing) implements CustomPacketPayload {
    public static final Type<SizeChangePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("uheroes", "size_change"));
    public static final StreamCodec<ByteBuf, SizeChangePayload> CODEC = ByteBufCodecs.BOOL.map(SizeChangePayload::new, SizeChangePayload::isGrowing);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
