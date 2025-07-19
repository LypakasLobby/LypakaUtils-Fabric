package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class LogoffListener implements ServerPlayConnectionEvents.Disconnect {

    @Override
    public void onPlayDisconnect (ServerPlayNetworkHandler serverPlayNetworkHandler, MinecraftServer minecraftServer) {

        ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
        LypakaUtils.playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(player.getUuid().toString()));
        try {

            LPPlayer lpPlayer = LypakaUtils.playerPermissionMap.get(player.getUuid());
            lpPlayer.save(true);
            // remove from permission map if it causes an issue...but I don't think it will

        } catch (Exception e) {

            // do nothing because I can't be bothered to fix this properly when literally no one (not even me) uses this (yet)

        }

    }

}
