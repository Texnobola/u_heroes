package com.uheroes.uheroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SummonClonePayload(boolean isMass) implements CustomPacketPayload {
    public static final Type<SummonClonePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("uheroes", "summon_clone"));

    public static final StreamCodec<ByteBuf, SummonClonePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SummonClonePayload::isMass,
            SummonClonePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
