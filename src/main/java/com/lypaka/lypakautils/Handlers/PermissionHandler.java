package com.lypaka.lypakautils.Handlers;

import com.lypaka.lypakautils.LypakaUtils;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayerEntity;

public class PermissionHandler {

    // Having to do this stupid check and cast because Cobblemon doesn't use actually useful mappings is an incredibly inconvenient pain in the ass. Thanks guys.
    public static boolean hasPermission (Object object, String permission) {

        if (object instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) object;
            if (player.hasPermissionLevel(4)) return true;
            if (permission == null || permission.isEmpty()) return true;

            // Check LuckPerms first
            if (isAvailable()) {

                Boolean result = checkPermission(player, permission);
                if (result != null) return result; // If LuckPerms provided a result, use it

            }

            LypakaUtils.logger.warn("Couldn't detect a permissions handler system! Defaulting all permissions checks to fail.");

        }

        return false;

    }

    // Check if the LuckPerms API is loaded
    public static boolean isAvailable() {

        try {

            Class.forName("me.lucko.fabric.api.permissions.v0.Permissions");
            return true;

        } catch (ClassNotFoundException e) {

            return false;

        }

    }

    // Check permission via LuckPerms
    public static Boolean checkPermission (ServerPlayerEntity player, String permission) {

        try {

            return Permissions.check(player, permission);

        } catch (Throwable t) {

            return null; // Fallback if anything goes wrong

        }

    }

}
