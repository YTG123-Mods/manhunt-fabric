package io.github.ytg1234.manhunt.base.command

import com.mojang.brigadier.CommandDispatcher
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.haveMod
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import java.util.function.Consumer

object ClearCacheCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("clearManhuntCache").executes { context ->
                haveMod.clear()
                context.source.minecraftServer.playerManager.playerList
                    .forEach(Consumer { player: ServerPlayerEntity? ->
                        ServerPlayNetworking.send(
                            player,
                            SERVER_QUESTION_PACKET_ID,
                            PacketByteBufs.empty()
                        )
                    })
                1
            })
    }
}
