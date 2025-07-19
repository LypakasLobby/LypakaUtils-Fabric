package com.lypaka.lypakautils;

import com.lypaka.lypakautils.API.PlayerLandMovementCallback;
import com.lypaka.lypakautils.API.PlayerWaterMovementCallback;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.ConfigurationLoaders.PlayerConfigManager;
import com.lypaka.lypakautils.Listeners.*;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.impactdev.impactor.api.economy.EconomyService;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LypakaUtils implements ModInitializer {

    public static final String MOD_ID = "lypakautils";
    public static final String MOD_NAME = "LypakaUtils";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);
    public static BasicConfigManager configManager;
    public static PlayerConfigManager playerConfigManager;
    public static Map<UUID, ServerPlayerEntity> playerMap = new HashMap<>();
    public static Map<UUID, LPPlayer> playerPermissionMap = new HashMap<>();

    @Override
    public void onInitialize() {

        logger.info("Loading LypakaUtils: Fabric Edition Version 0.0.3");
        Path dir = ConfigUtils.checkDir(Paths.get("./config/lypakautils"));
        String[] files = new String[]{"lypakautils.conf", "permission-groups.conf"};
        configManager = new BasicConfigManager(files, dir, LypakaUtils.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        playerConfigManager = new PlayerConfigManager("account.conf", "accounts", dir, LypakaUtils.class, MOD_NAME, MOD_ID, logger);
        playerConfigManager.init();
        ConfigGetters.load();

        ServerLivingEntityEvents.AFTER_DEATH.register(new DeathListener());
        UseEntityCallback.EVENT.register(new EntityInteractListener());
        ServerPlayConnectionEvents.JOIN.register(new LoginListener());
        ServerPlayConnectionEvents.DISCONNECT.register(new LogoffListener());
        ServerPlayerEvents.AFTER_RESPAWN.register(new RespawnListener());
        ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedListener());
        if (ConfigGetters.tickListenerEnabled) {

            ServerTickEvents.END_SERVER_TICK.register(new TickListener());

        }

        PlayerLandMovementCallback.EVENT.register((player, steps) -> {
            if (player.isOnGround()) {

                PlayerDataHandler.setLastKnownLandLocation(player.getUuid(), player.getBlockX(), player.getBlockY(), player.getBlockZ());


            }
        });
        PlayerWaterMovementCallback.EVENT.register((player, steps) -> {

        });

    }

}
