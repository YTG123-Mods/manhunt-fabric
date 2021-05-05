package io.github.ytg1234.manhunt.command

import com.mojang.brigadier.Command
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.haveMod
import io.github.ytg1234.manhunt.util.PermedCommand
import mc.aegis.AegisCommandBuilder
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

/**
 * Used to clear the [haveMod] list and resend the packet.
 *
 * @author YTG1234
 */
object ClearCacheCommand : PermedCommand("clearManhuntCache", "manhunt.command.clearcache", 2) {
    override val cmd: AegisCommandBuilder.() -> AegisCommandBuilder = {
        executes { ctx ->
            val minecraftServer = ctx.source.minecraftServer
            minecraftServer.haveMod.clear()
            minecraftServer.playerManager.playerList.forEach {
                ServerPlayNetworking.send(it, SERVER_QUESTION_PACKET_ID, PacketByteBufs.empty())
            }
            Command.SINGLE_SUCCESS
        }
        this
    }
}
