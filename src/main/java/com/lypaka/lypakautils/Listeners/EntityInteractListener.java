package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.Handlers.FancyTextHandler;
import com.lypaka.lypakautils.Handlers.PermissionHandler;
import com.lypaka.lypakautils.Handlers.WorldHandlers;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EntityInteractListener implements UseEntityCallback {

    @Override
    public ActionResult interact (PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {

        if (!world.isClient && hand == Hand.MAIN_HAND && playerEntity.isCreative() && entityHitResult != null && !playerEntity.getWorld().isClient) {

            String id = Registries.ITEM.getId(playerEntity.getMainHandStack().getItem()).toString();
            if (id.equalsIgnoreCase("minecraft:golden_sword")) {

                if (PermissionHandler.hasPermission(playerEntity, "lypakautils.admin")) {

                    String worldName = WorldHandlers.getWorldName((ServerPlayerEntity) playerEntity);
                    int x = playerEntity.getBlockPos().getX();
                    int y = playerEntity.getBlockPos().getY();
                    int z = playerEntity.getBlockPos().getZ();
                    String location = worldName + "," + x + "," + y + "," + z;
                    playerEntity.sendMessage(FancyTextHandler.getFormattedText("&eLocation of entity: &b" + location), false);
                    return ActionResult.FAIL;

                }

            }

        }
        return ActionResult.PASS;

    }

}
