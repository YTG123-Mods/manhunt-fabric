package io.github.ytg1234.manhunt.base.init

import com.mojang.brigadier.CommandDispatcher
import io.github.ytg1234.manhunt.base.CLIENT_ANSWER_PACKET_ID
import io.github.ytg1234.manhunt.base.SERVER_QUESTION_PACKET_ID
import io.github.ytg1234.manhunt.base.command.ClearCacheCommand
import io.github.ytg1234.manhunt.base.command.HuntersCommand
import io.github.ytg1234.manhunt.base.command.SpeedrunnerCommand
import io.github.ytg1234.manhunt.base.haveMod
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.command.ServerCommandSource

object ManhuntEventRegistration {
    fun registerCommonEvents() {
        ServerTickEvents.END_SERVER_TICK.register(ManhuntTicks::centralTick)

        CommandRegistrationCallback.EVENT.register(::registerCommands)
        UseItemCallback.EVENT.register(ManhuntInteractions::pointCompass)

        ServerPlayNetworking.registerGlobalReceiver(CLIENT_ANSWER_PACKET_ID) { server, player, _, _, _ ->
            server.execute { if (!haveMod.contains(player)) haveMod.add(player) }
        }
    }

    private fun registerCommands(dispatcher: CommandDispatcher<ServerCommandSource>, dedicated: Boolean) {
        SpeedrunnerCommand.register(dispatcher)
        HuntersCommand.register(dispatcher)
        ClearCacheCommand.register(dispatcher)
    }

    fun registerClientSideEvents() {
        ClientPlayNetworking.registerGlobalReceiver(
            SERVER_QUESTION_PACKET_ID
        ) { client, _, _, _ ->
            client.execute { ClientPlayNetworking.send(CLIENT_ANSWER_PACKET_ID, PacketByteBufs.empty()) }
        }
    }
}
