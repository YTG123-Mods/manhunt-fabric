package io.github.ytg1234.manhunt.base.init

import com.mojang.brigadier.CommandDispatcher
import io.github.ytg1234.manhunt.base.CLIENT_ANSWER_PACKET_ID
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.haveMod
import io.github.ytg1234.manhunt.command.ClearCacheCommand
import io.github.ytg1234.manhunt.command.HuntersCommand
import io.github.ytg1234.manhunt.command.SpeedrunnerCommand
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.quiltmc.qsl.lifecycle.api.event.ServerTickEvents
import org.quiltmc.qsl.networking.api.PacketByteBufs
import org.quiltmc.qsl.networking.api.ServerPlayNetworking
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking

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
        ServerTickEvents.END.register(ManhuntTicks::centralTick)

        CommandRegistrationCallback.EVENT.register(::registerCommands)
        UseItemCallback.EVENT.register(ManhuntInteractions::pointCompass)

        ServerPlayNetworking.registerGlobalReceiver(CLIENT_ANSWER_PACKET_ID) { server, player, _, _, _ ->
            server.execute { if (!haveMod.contains(player)) haveMod.add(player) }
        }
    }

    /**
     * Registers Manhunt's commands.
     */
    private fun registerCommands(dispatcher: CommandDispatcher<ServerCommandSource>, @Suppress("UNUSED_PARAMETER") integrated: Boolean, @Suppress("UNUSED_PARAMETER") dedicated: Boolean) {
        SpeedrunnerCommand.registerCmd(dispatcher)
        HuntersCommand.registerCmd(dispatcher)
        ClearCacheCommand.registerCmd(dispatcher)
    }

    /**
     * Registers client-only events.
     */
    fun registerClientSideEvents() {
        ClientPlayNetworking.registerGlobalReceiver(
            SERVER_QUESTION_PACKET_ID
        ) { client, _, _, _ ->
            client.execute { ClientPlayNetworking.send(CLIENT_ANSWER_PACKET_ID, PacketByteBufs.empty()) }
        }
    }
}
