package com.lypaka.lypakautils.API;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerLandMovementCallback {

    void onPlayerMove(ServerPlayerEntity player, int steps);

    Event<PlayerLandMovementCallback> EVENT = EventFactory.createArrayBacked(
            PlayerLandMovementCallback.class,
            (listeners) -> (player, steps) -> {

                for (PlayerLandMovementCallback listener : listeners) {

                    listener.onPlayerMove(player, steps);

                }

            });

}
