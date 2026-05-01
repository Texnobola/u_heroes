package com.uheroes.uheroesmod.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlackSCloneEntity extends PathfinderMob {

    public BlackSCloneEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        if (!this.getPersistentData().contains("lifespan")) {
            this.getPersistentData().putInt("lifespan", 1200);
        }
        if (!this.getPersistentData().contains("owner_id")) {
            this.getPersistentData().putString("owner_id", "");
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            int lifespan = this.getPersistentData().getInt("lifespan");
            lifespan--;
            this.getPersistentData().putInt("lifespan", lifespan);

            if (lifespan <= 0) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 20, 0.5, 0.5, 0.5, 0.05);
                }
                this.discard();
            }
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        String ownerId = this.getPersistentData().getString("owner_id");
        if (player.getUUID().toString().equals(ownerId)) {
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 20, 0.5, 0.5, 0.5, 0.05);
            }
            if (!this.level().isClientSide()) {
                this.discard();
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return InteractionResult.PASS;
    }
}
