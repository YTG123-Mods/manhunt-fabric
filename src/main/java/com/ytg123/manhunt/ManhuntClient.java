package com.ytg123.manhunt;

import com.ytg123.manhunt.init.ManhuntPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ManhuntClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ManhuntPackets.registerPacketsClient();
    }
}
