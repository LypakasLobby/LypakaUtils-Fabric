package com.lypaka.lypakautils.API;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerWaterMovementCallback {

    void onPlayerMove(ServerPlayerEntity player, int steps);

    Event<PlayerWaterMovementCallback> EVENT = EventFactory.createArrayBacked(
            PlayerWaterMovementCallback.class,
            (listeners) -> (player, steps) -> {

                for (PlayerWaterMovementCallback listener : listeners) {

                    listener.onPlayerMove(player, steps);

                }

            });

}
