package com.ytg123.manhunt;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.EndTick;
import net.minecraft.server.MinecraftServer;

public final class EventListener implements EndTick {
    public static final EventListener INSTANCE;

    static {
        INSTANCE = new EventListener();
    }

    private EventListener() {}

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {

    }
}
