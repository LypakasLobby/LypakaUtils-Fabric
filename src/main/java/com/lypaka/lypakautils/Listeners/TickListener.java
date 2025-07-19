package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.API.PlayerLandMovementCallback;
import com.lypaka.lypakautils.API.PlayerWaterMovementCallback;
import com.lypaka.lypakautils.Handlers.WorldHandlers;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Map;
import java.util.UUID;

public class TickListener implements ServerTickEvents.EndTick {

    private static int count = -1;

    @Override
    public void onEndTick (MinecraftServer server) {

        count++;
        if (count >= 20) {

            count = -1;
            for (Map.Entry<UUID, ServerPlayerEntity> entry : LypakaUtils.playerMap.entrySet()) {

                ServerPlayerEntity player = entry.getValue();
                int currentX = player.getBlockX();
                int currentY = player.getBlockY();
                int currentZ = player.getBlockZ();
                World world = player.getWorld();
                int steps = PlayerDataHandler.calculateStepsTaken(player.getUuid(), currentX, currentY, currentZ);
                if (steps > 0) {

                    String blockID = Registries.BLOCK.getId(world.getBlockState(player.getBlockPos()).getBlock()).toString();
                    if (blockID.contains("water") || blockID.contains("lava")) {

                        PlayerWaterMovementCallback.EVENT.invoker().onPlayerMove(player, steps);

                    } else {

                        PlayerLandMovementCallback.EVENT.invoker().onPlayerMove(player, steps);

                    }
                    String dimension = player.getWorld().getDimension().toString();
                    String worldName = WorldHandlers.getWorldName(player);

                    PlayerLocation location = PlayerDataHandler.playerLocationMap.get(player.getUuid());
                    if (!location.getCurrentDimension().equalsIgnoreCase(dimension)) {

                        //PlayerChangeDimensionEvent dimEvent = new PlayerChangeDimensionEvent(player, location.getLastKnownDimension(), dimension);
                        location.setLastKnownDimension(location.getCurrentDimension());
                        location.setCurrentDimension(dimension);

                    }
                    if (!location.getCurrentWorld().equalsIgnoreCase(worldName)) {

                        //PlayerChangeWorldEvent worldEvent = new PlayerChangeWorldEvent(player, location.getLastKnownWorld(), worldName);
                        location.setLastKnownWorld(location.getCurrentWorld());
                        location.setCurrentWorld(worldName);

                    }

                }

            }

        }

    }

}
