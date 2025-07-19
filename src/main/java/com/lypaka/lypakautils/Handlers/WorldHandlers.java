package com.lypaka.lypakautils.Handlers;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class WorldHandlers {

    public static Map<String, ServerWorld> worldMap = new HashMap<>();

    public static void load (MinecraftServer server) {

        for (ServerWorld world : server.getWorlds()) {

            String worldName = world.getRegistryKey().getValue().toString();
            worldMap.put(worldName, world);
            System.out.println("adding world with name: " + worldName);

        }

    }

    public static String getWorldName (ServerPlayerEntity player) {

        return getWorldName(player.getWorld());

    }

    public static String getWorldName (World world) {

        return world.getRegistryKey().getValue().toString();

    }

    public static String getEntityDimensionID (Entity entity) {

        return entity.getWorld().getRegistryKey().getValue().toString();

    }

}
