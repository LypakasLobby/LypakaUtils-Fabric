package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.LypakaUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class RespawnListener implements ServerPlayerEvents.AfterRespawn {

    @Override
    public void afterRespawn (ServerPlayerEntity serverPlayerEntity, ServerPlayerEntity serverPlayerEntity1, boolean b) {

        putTheBitchBackIfMissing(serverPlayerEntity, serverPlayerEntity1);

    }

    private static void putTheBitchBackIfMissing (ServerPlayerEntity old, ServerPlayerEntity newPlayer) {

        UUID uuid = old.getUuid();
        LypakaUtils.playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(uuid.toString()));
        LypakaUtils.playerMap.put(uuid, newPlayer);

    }

}
