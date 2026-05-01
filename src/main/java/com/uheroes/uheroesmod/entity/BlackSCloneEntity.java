package com.uheroes.uheroesmod.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Optional;
import java.util.UUID;

public class BlackSCloneEntity extends PathfinderMob {

    private static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.defineId(BlackSCloneEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_ID = SynchedEntityData.defineId(BlackSCloneEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public BlackSCloneEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LIFESPAN, 1200);
        builder.define(OWNER_ID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Lifespan", this.entityData.get(LIFESPAN));
        this.entityData.get(OWNER_ID).ifPresent(uuid -> compound.putUUID("Owner", uuid));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Lifespan")) {
            this.entityData.set(LIFESPAN, compound.getInt("Lifespan"));
        }
        if (compound.hasUUID("Owner")) {
            this.entityData.set(OWNER_ID, Optional.of(compound.getUUID("Owner")));
        }
    }

    public void setOwner(UUID uuid) {
        this.entityData.set(OWNER_ID, Optional.ofNullable(uuid));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0f));
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
            int currentLifespan = this.entityData.get(LIFESPAN);
            currentLifespan--;
            this.entityData.set(LIFESPAN, currentLifespan);
            
            if (currentLifespan <= 0) {
                spawnSmokeParticles();
                this.discard();
            }
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        Optional<UUID> ownerOpt = this.entityData.get(OWNER_ID);
        if (ownerOpt.isPresent() && player.getUUID().equals(ownerOpt.get())) {
            spawnSmokeParticles();
            if (!this.level().isClientSide()) {
                this.discard();
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return InteractionResult.PASS;
    }

    private void spawnSmokeParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 20, 0.5, 0.5, 0.5, 0.05);
        }
    }
}
