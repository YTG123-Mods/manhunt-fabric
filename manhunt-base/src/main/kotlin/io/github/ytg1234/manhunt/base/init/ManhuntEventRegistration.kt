package io.github.ytg1234.manhunt.base.init

import com.mojang.brigadier.CommandDispatcher
import io.github.ytg1234.manhunt.base.CLIENT_ANSWER_PACKET_ID
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.haveMod
import io.github.ytg1234.manhunt.command.ClearCacheCommand
import io.github.ytg1234.manhunt.command.HuntersCommand
import io.github.ytg1234.manhunt.command.SpeedrunnerCommand
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.command.ServerCommandSource

/**
 * Registers all events for Manhunt.
 *
 * @author YTG1234
 */
object ManhuntEventRegistration {
    /**
     * Registers events that are applicable both to
     * server and client-side.
     */
    fun registerCommonEvents() {
        ServerTickEvents.END_SERVER_TICK.register(ManhuntTicks::centralTick)

        CommandRegistrationCallback.EVENT.register(::registerCommands)
        UseItemCallback.EVENT.register(ManhuntInteractions::pointCompass)

        ServerPlayNetworking.registerGlobalReceiver(CLIENT_ANSWER_PACKET_ID) { server, player, _, _, _ ->
            server.execute { if (player !in server.haveMod) server.haveMod.add(player) }
        }
    }

    /**
     * Registers Manhunt's commands.
     */
    private fun registerCommands(dispatcher: CommandDispatcher<ServerCommandSource>, dedicated: Boolean) {
        SpeedrunnerCommand.registerCmd(dispatcher)
        HuntersCommand.registerCmd(dispatcher)
        ClearCacheCommand.registerCmd(dispatcher)
    }

    /**
     * Registers client-only events.
     */
    @Environment(EnvType.CLIENT)
    fun registerClientSideEvents() {
        ClientPlayNetworking.registerGlobalReceiver(
            SERVER_QUESTION_PACKET_ID
        ) { client, _, _, _ ->
            client.execute { ClientPlayNetworking.send(CLIENT_ANSWER_PACKET_ID, PacketByteBufs.empty()) }
        }
    }
}
