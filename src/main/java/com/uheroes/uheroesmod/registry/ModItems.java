package com.uheroes.uheroesmod.registry;

import com.uheroes.uheroesmod.uheroes;
import com.uheroes.uheroesmod.item.HeroChooserItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(uheroes.MODID);

    public static final DeferredItem<HeroChooserItem> HERO_CHOOSER = ITEMS.register("hero_chooser", 
            () -> new HeroChooserItem(new Item.Properties()));

    public static final DeferredItem<Item> THIEF_CHIP = ITEMS.register("thief_chip", 
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
