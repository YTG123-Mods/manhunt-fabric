package io.github.ytg1234.manhunt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import io.github.ytg1234.manhunt.base.fromCmdContext
import io.github.ytg1234.manhunt.base.isHunter
import io.github.ytg1234.manhunt.base.playerHasMod
import io.github.ytg1234.manhunt.base.speedRunner
import io.github.ytg1234.manhunt.util.PermedCommand
import io.github.ytg1234.manhunt.util.plus
import io.github.ytg1234.manhunt.util.reset
import mc.aegis.AegisCommandBuilder
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

/**
 * Used to control the [speedRunner].
 *
 * @author YTG1234
 */
object SpeedrunnerCommand : PermedCommand("speedrunner", "manhunt.command.speedrunner", 2) {
    override val cmd: AegisCommandBuilder.() -> AegisCommandBuilder = {
        literal("set") {
            custom(CommandManager.argument("target", EntityArgumentType.player())) {
                executes(::executeSet)
            }
        }
        literal("get") {
            executes(::executeGet)
        }
        literal("clear") {
            executes(::executeClear)
        }
        this
    }

    /**
     * Changes the [speedrunner] to another player.
     */
    private fun executeSet(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        val target = EntityArgumentType.getPlayer(context, "target")
        val minecraftServer = context.source.minecraftServer
        //if (minecraftServer.hunters.contains(target.uuid)) {
        if (target.isHunter) {
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
        minecraftServer.speedRunner = target.uuid
        if (playerHasMod) {
            context.source
                .sendFeedback(
                    TranslatableText(
                        "text.manhunt.command.speedrunner.set",
                        fromCmdContext(context, minecraftServer.speedRunner)!!.displayName
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
        val speedRunner = context.source.minecraftServer.speedRunner ?: return 1
        if (playerHasMod) {
            context.source
                .sendFeedback(
                    TranslatableText(
                        "text.manhunt.command.speedrunner.get",
                        fromCmdContext(context, speedRunner)!!.displayName
                    ), false
                )
        } else {
            context.source
                .sendFeedback(
                    reset("Speedrunner is currently: ") + fromCmdContext(context, speedRunner)!!.displayName,
                    true
                )
        }
        return Command.SINGLE_SUCCESS
    }

    /**
     * Sets the [speedRunner] to `null`
     */
    private fun executeClear(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        context.source.minecraftServer.speedRunner = null
        if (playerHasMod) {
            context.source.sendFeedback(TranslatableText("text.manhunt.command.speedrunner.clear"), true)
        } else {
            context.source.sendFeedback(reset("Speedrunner Cleared!"), true)
        }
        return Command.SINGLE_SUCCESS
    }
}
