package com.mastertdf.jobs.items;

import com.mastertdf.jobs.ModJobs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class JobsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ("jobs"));

    //Items injected here
    public static final RegistryObject<Item> Jobs_Book = ITEMS.register("jobs_book",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Jobs_Book_Artisan = ITEMS.register("jobs_book_artisan",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Jobs_Book_Engineer = ITEMS.register("jobs_book_engineer",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Jobs_Book_Farmer = ITEMS.register("jobs_book_farmer",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Jobs_Book_Hunter = ITEMS.register("jobs_book_hunter",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Jobs_Book_Smith = ITEMS.register("jobs_book_smith",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Jobs_Book_Wizard = ITEMS.register("jobs_book_wizard",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> Shard_of_xp = ITEMS.register("shard_of_xp",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static void register (IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
