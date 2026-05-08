package com.uheroes.uheroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.core.UUIDUtil;
import java.util.UUID;

public record AnimationSyncPayload(UUID playerUUID, String animationName) implements CustomPacketPayload {
    public static final Type<AnimationSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("uheroes", "animation_sync"));

    public static final StreamCodec<ByteBuf, AnimationSyncPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            AnimationSyncPayload::playerUUID,
            ByteBufCodecs.STRING_UTF8,
            AnimationSyncPayload::animationName,
            AnimationSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
