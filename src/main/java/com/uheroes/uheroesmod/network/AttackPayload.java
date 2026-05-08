package com.uheroes.uheroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AttackPayload(int attackType) implements CustomPacketPayload {
    public static final Type<AttackPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("uheroes", "attack"));

    public static final StreamCodec<ByteBuf, AttackPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AttackPayload::attackType,
            AttackPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
