package io.github.ytg1234.manhunt.base.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.ytg1234.manhunt.base.ManhuntUtils;
import io.github.ytg1234.manhunt.base.command.ClearCacheCommand;
import io.github.ytg1234.manhunt.base.command.HuntersCommand;
import io.github.ytg1234.manhunt.base.command.SpeedrunnerCommand;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.command.ServerCommandSource;

public final class ManhuntEventRegistration {
    private ManhuntEventRegistration() {
    }

    public static void registerCommonEvents() {
        ServerTickEvents.END_SERVER_TICK.register(ManhuntTicks::centralTick);
        CommandRegistrationCallback.EVENT.register(ManhuntEventRegistration::registerCommands);
        UseItemCallback.EVENT.register(ManhuntInteractions::updateCompass);

        ServerPlayNetworking.registerGlobalReceiver(ManhuntUtils.CLIENT_ANSWER_PACKET_ID, (server, player, handler, data, sender) -> {
            server.execute(() -> {
                if (!ManhuntUtils.haveMod.contains(player)) ManhuntUtils.haveMod.add(player);
            });
        });
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        SpeedrunnerCommand.register(dispatcher);
        HuntersCommand.register(dispatcher);
        ClearCacheCommand.register(dispatcher);
    }

    public static void registerClientSideEvents() {
        ClientPlayNetworking.registerGlobalReceiver(ManhuntUtils.SERVER_QUESTION_PACKET_ID, (client, handler, data, sender) -> {
            client.execute(() -> {
                ClientPlayNetworking.send(ManhuntUtils.CLIENT_ANSWER_PACKET_ID, PacketByteBufs.empty());
            });
        });
    }
}
