package io.github.ytg1234.manhunt.base.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.haveMod
import mc.aegis.AegisCommandBuilder
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.command.ServerCommandSource

object ClearCacheCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            AegisCommandBuilder("clearManhuntCache") {
                requires { it.hasPermissionLevel(2) }
                executes { ctx ->
                    haveMod.clear()
                    ctx.source.minecraftServer.playerManager.playerList.forEach {
                        ServerPlayNetworking.send(it, SERVER_QUESTION_PACKET_ID, PacketByteBufs.empty())
                    }
                    Command.SINGLE_SUCCESS
                }
            }.build()
        )
    }
}
