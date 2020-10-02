package com.ytg123.manhunt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.ytg123.manhunt.ManhuntUtils;
import com.ytg123.manhunt.init.ManhuntPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.*;

public class ClearCacheCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("clearManhuntCache").executes(context -> {
                    ManhuntUtils.haveMod.clear();
                    context.getSource().getMinecraftServer().getPlayerManager().getPlayerList().forEach(player -> {
                        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ManhuntPackets.SERVER_QUESTION_PACKET_ID, new PacketByteBuf(Unpooled.buffer()));
                    });
                    return 1;
                })
        );
    }
}
