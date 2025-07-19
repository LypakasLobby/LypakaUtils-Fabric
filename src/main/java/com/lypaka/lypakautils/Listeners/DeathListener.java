package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.Handlers.WorldHandlers;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class DeathListener implements ServerLivingEntityEvents.AfterDeath {

    @Override
    public void afterDeath (LivingEntity livingEntity, DamageSource damageSource) {

        if (livingEntity instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) livingEntity;
            int currentX = player.getBlockX();
            int currentY = player.getBlockY();
            int currentZ = player.getBlockZ();
            PlayerLocation playerLocation = PlayerDataHandler.playerLocationMap.get(player.getUuid());
            String location = WorldHandlers.getEntityDimensionID(player) + "," + currentX + "," + currentY + "," + currentZ;
            playerLocation.setLastDeathLocation(location);

        }

    }

}
