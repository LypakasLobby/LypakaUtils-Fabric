package com.lypaka.lypakautils.Handlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemStackHandler {

    /**
     * Creates an ItemStack from a string ID like "minecraft:stone"
     *
     * @param id the string identifier of the item
     * @return ItemStack of the specified item
     */
    public static ItemStack buildFromStringID (String id) {

        Identifier identifier = Identifier.of(id);
        Item item = Registries.ITEM.get(identifier);
        return new ItemStack(item);

    }

    /**
     * Converts a generic object to an ItemStack (unsafe cast)
     *
     * @param object the object to convert
     * @return the casted ItemStack
     */
    public static ItemStack convertFromObject (Object object) {

        return (ItemStack) object;

    }

}