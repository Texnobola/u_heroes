package com.uheroes.uheroesmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.uheroes.uheroesmod.world.data.FluxData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "uheroes")
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("uheroes")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("flux")
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, FluxData.MAX_FLUX))
                                                .executes(context -> setFlux(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target"),
                                                        IntegerArgumentType.getInteger(context, "amount")
                                                ))
                                        )
                                )
                        )
                        .then(Commands.literal("add")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(-FluxData.MAX_FLUX, FluxData.MAX_FLUX))
                                                .executes(context -> addFlux(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target"),
                                                        IntegerArgumentType.getInteger(context, "amount")
                                                ))
                                        )
                                )
                        )
                )
        );
    }

    private static int setFlux(CommandSourceStack source, ServerPlayer target, int amount) {
        FluxData.setFlux(target, amount);
        source.sendSuccess(() -> Component.literal("Set Flux of " + target.getScoreboardName() + " to " + amount), true);
        return 1;
    }

    private static int addFlux(CommandSourceStack source, ServerPlayer target, int amount) {
        int newAmount = FluxData.getFlux(target) + amount;
        FluxData.setFlux(target, newAmount);
        source.sendSuccess(() -> Component.literal("Added " + amount + " Flux to " + target.getScoreboardName() + " (Now: " + FluxData.getFlux(target) + ")"), true);
        return 1;
    }
}
