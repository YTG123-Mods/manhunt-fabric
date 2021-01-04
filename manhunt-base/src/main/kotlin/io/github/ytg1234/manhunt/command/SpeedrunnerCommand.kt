package io.github.ytg1234.manhunt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import io.github.ytg1234.manhunt.base.fromCmdContext
import io.github.ytg1234.manhunt.base.hunters
import io.github.ytg1234.manhunt.base.playerHasMod
import io.github.ytg1234.manhunt.base.speedrunner
import io.github.ytg1234.manhunt.util.plus
import io.github.ytg1234.manhunt.util.reset
import mc.aegis.AegisCommandBuilder
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

/**
 * Used to control the [speedrunner].
 *
 * @author YTG1234
 */
object SpeedrunnerCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            AegisCommandBuilder("speedrunner") {
                literal("set") {
                    requires { it.hasPermissionLevel(2) }
                    custom(CommandManager.argument("target", EntityArgumentType.player())) {
                        executes(::executeSet)
                    }
                }
                literal("get") {
                    executes(::executeGet)
                }
                literal("clear") {
                    requires { it.hasPermissionLevel(2) }
                    executes(::executeClear)
                }
            }.build()
        )
    }

    /**
     * Changes the [speedrunner] to another player.
     */
    private fun executeSet(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        val target = EntityArgumentType.getPlayer(context, "target")
        if (hunters.contains(target.uuid)) {
            if (playerHasMod) {
                context.source.sendError(
                    TranslatableText(
                        "text.manhunt.command.speedrunner.error.hunter",
                        target.displayName
                    )
                )
            } else {
                context.source
                    .sendError(
                        reset("Cannot set speedrunner to ") + target.displayName + reset(" because they are a hunter!")
                    )
            }
            return Command.SINGLE_SUCCESS
        }
        speedrunner = target.uuid
        if (playerHasMod) {
            context.source
                .sendFeedback(
                    TranslatableText(
                        "text.manhunt.command.speedrunner.set",
                        fromCmdContext(context, speedrunner).displayName
                    ), true
                )
        } else {
            context.source
                .sendFeedback(
                    reset("Set speedrunner to ") + target.displayName + reset("!"),
                    true
                )
        }
        return Command.SINGLE_SUCCESS
    }

    /**
     * Sends the [speedrunner]'s name.
     */
    private fun executeGet(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        if (speedrunner == null) return 1
        if (playerHasMod) {
            context.source
                .sendFeedback(
                    TranslatableText(
                        "text.manhunt.command.speedrunner.get",
                        fromCmdContext(context, speedrunner).displayName
                    ), false
                )
        } else {
            context.source
                .sendFeedback(
                    reset("Speedrunner is currently: ") + fromCmdContext(context, speedrunner).displayName,
                    true
                )
        }
        return Command.SINGLE_SUCCESS
    }

    /**
     * Sets the [speedrunner] to `null`
     */
    private fun executeClear(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        speedrunner = null
        if (playerHasMod) {
            context.source.sendFeedback(TranslatableText("text.manhunt.command.speedrunner.clear"), true)
        } else {
            context.source.sendFeedback(reset("Speedrunner Cleared!"), true)
        }
        return Command.SINGLE_SUCCESS
    }
}
