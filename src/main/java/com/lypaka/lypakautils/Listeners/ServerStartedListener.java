package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.Handlers.EconomyHandler;
import com.lypaka.lypakautils.Handlers.WorldHandlers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerStartedListener implements ServerLifecycleEvents.ServerStarted {

    @Override
    public void onServerStarted (MinecraftServer server) {

        WorldHandlers.load(server);
        EconomyHandler.init();

    }

}
