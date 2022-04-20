package io.github.ytg1234.manhunt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.haveMod
import io.github.ytg1234.manhunt.util.PermedCommand
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.qsl.networking.api.PacketByteBufs
import org.quiltmc.qsl.networking.api.ServerPlayNetworking

/**
 * Used to clear the [haveMod] list and resend the packet.
 *
 * @author YTG1234
 */
object ClearCacheCommand : PermedCommand("manhunt.command.clearcache", 2) {
    override val cmd: LiteralArgumentBuilder<ServerCommandSource> = CommandManager.literal("clearManhuntCache").executes { ctx ->
        haveMod.clear()
        ctx.source.server.playerManager.playerList.forEach {
            ServerPlayNetworking.send(it, SERVER_QUESTION_PACKET_ID, PacketByteBufs.empty())
        }
        Command.SINGLE_SUCCESS
    }
}
