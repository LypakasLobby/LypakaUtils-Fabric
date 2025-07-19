package com.lypaka.lypakautils.Listeners;

import com.google.common.reflect.TypeToken;
import com.lypaka.lypakautils.Handlers.WorldHandlers;
import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class LoginListener implements ServerPlayConnectionEvents.Join {

    @Override
    public void onPlayReady (ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {

        ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
        LypakaUtils.playerMap.put(player.getUuid(), player);
        if (!PlayerDataHandler.playerLocationMap.containsKey(player.getUuid())) {

            int x = player.getBlockX();
            int y = player.getBlockY();
            int z = player.getBlockZ();
            String dimension = WorldHandlers.getEntityDimensionID(player);
            String world = WorldHandlers.getWorldName(player);
            PlayerLocation location = new PlayerLocation(x, y, z, x, y, z, dimension, dimension, world, world);
            PlayerDataHandler.playerLocationMap.put(player.getUuid(), location);

        }
        LypakaUtils.playerConfigManager.loadPlayer(player.getUuid());
        try {

            List<String> groups = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUuid(), "Groups").getList(TypeToken.of(String.class));
            List<String> permissions = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUuid(), "Permissions").getList(TypeToken.of(String.class));

            LPPlayer lpPlayer = new LPPlayer(player.getUuid(), groups, permissions);
            LypakaUtils.playerPermissionMap.put(player.getUuid(), lpPlayer);

        } catch (ObjectMappingException e) {

            throw new RuntimeException(e);

        }

    }

}
