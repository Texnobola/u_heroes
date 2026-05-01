package com.uheroes.uheroesmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeroChooserItem extends Item {
    public HeroChooserItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            player.getPersistentData().putBoolean("is_blacks", true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
            
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            player.sendSystemMessage(Component.literal("[U-Heroes] You have awakened as BlackS"));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
