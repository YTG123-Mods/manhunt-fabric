package io.github.ytg1234.manhunt.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.ytg1234.manhunt.ManhuntUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class ClearCacheCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("clearManhuntCache").executes(context -> {
            ManhuntUtils.haveMod.clear();
            context.getSource().getMinecraftServer().getPlayerManager().getPlayerList().forEach(player -> {
                ServerPlayNetworking.send(player, ManhuntUtils.SERVER_QUESTION_PACKET_ID, PacketByteBufs.empty());
            });
            return 1;
        }));
    }
}
