package com.lypaka.lypakautils.Handlers;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class FancyTextHandler {

    /**
     * Takes a String with formatting codes (like &e for yellow) and replaces the & with § (section sign) to apply formatting.
     * Use this for ItemStack display names.
     *
     * @param unformattedString the string with & formatting codes
     * @return formatted string with § codes
     */
    public static String getFormattedString (String unformattedString) {

        return unformattedString.replace("&", "\u00a7");

    }

    /**
     * Converts a string with &-formatting codes into a Minecraft Text object.
     * Use this for sending messages to players, console, item lore, etc.
     *
     * @param unformattedText the input text with formatting codes
     * @return a Text object with formatting applied
     */
    public static Text getFormattedText (String unformattedText) {

        return Text.literal(getFormattedString(unformattedText));

    }

    /**
     * Converts a list of generic objects to a list of Text components.
     * Assumes all entries are Text objects.
     *
     * @param objects list of objects, expected to be Text
     * @return list of Text
     */
    public static List<Text> convertFromObjects (List<Object> objects) {

        List<Text> list = new ArrayList<>();
        for (Object obj : objects) {

            list.add((Text) obj);

        }

        return list;

    }

    /**
     * Sends a Text message to a player, either in chat or action bar.
     *
     * @param object      the player object (must be ServerPlayerEntity)
     * @param message     the Text message
     * @param onActionBar true for action bar, false for regular chat
     */
    public static void sendFancyMessage (Object object, Text message, boolean onActionBar) {

        if (object instanceof ServerPlayerEntity player) {

            player.sendMessage(message, onActionBar);

        }

    }

}
