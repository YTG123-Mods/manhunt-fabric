package io.github.ytg1234.manhunt.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import io.github.ytg1234.manhunt.base.CONFIG
import io.github.ytg1234.manhunt.base.fromCmdContext
import io.github.ytg1234.manhunt.base.hunters
import io.github.ytg1234.manhunt.base.playerHasMod
import io.github.ytg1234.manhunt.base.speedRunner
import io.github.ytg1234.manhunt.util.PermedCommand
import io.github.ytg1234.manhunt.util.plus
import io.github.ytg1234.manhunt.util.reset
import mc.aegis.AegisCommandBuilder
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText

/**
 * Used to manage the [hunter list][hunters].
 *
 * @author YTG1234
 */
object HuntersCommand : PermedCommand("hunters", "manhunt.command.hunters", 2) {
    override val cmd: AegisCommandBuilder.() -> AegisCommandBuilder = {
        literal("clear") {
            executes(::executeClear)
        }
        literal("add") {
            custom(CommandManager.argument("target", EntityArgumentType.player())) {
                executes(::executeAdd)
            }
        }
        literal("get") {
            executes(::executeGet)
        }
        this
    }

    /**
     * Clears the hunter list.
     */
    private fun executeClear(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        context.source.minecraftServer.hunters.clear()
        if (playerHasMod) {
            context.source.sendFeedback(TranslatableText("text.manhunt.command.hunters.clear"), true)
        } else {
            context.source.sendFeedback(LiteralText("Cleared hunter list!"), false)
        }
        return Command.SINGLE_SUCCESS
    }

    /**
     * Adds a hunter to the hunter list.
     */
    private fun executeAdd(context: CommandContext<ServerCommandSource>): Int {
        val target = EntityArgumentType.getPlayer(context, "target")
        val playerHasMod: Boolean = playerHasMod(context)

        // Target is speedrunner
        val minecraftServer = context.source.minecraftServer
        if (target.uuid == minecraftServer.speedRunner) {
            // We check if the source is a player and the player has the mod
            if (playerHasMod) {
                // We send error message using TranslatableText
                context.source.sendError(
                    TranslatableText(
                        "text.manhunt.command.hunters.error.speedrunner",
                        target.displayName
                    )
                )

                // Source is not player or doesn't have mod, sending error message using LiteralText
            } else {
                context.source
                    .sendError(
                        LiteralText("Cannot add ").append(target.displayName)
                            .append(LiteralText(" as a hunter because they are the speedrunner!"))
                    )
            }
            return Command.SINGLE_SUCCESS
        }

        // Check if target is already a hunter
        if (minecraftServer.hunters.contains(target.uuid)) {
            if (playerHasMod) {
                context.source.sendError(
                    TranslatableText(
                        "text.manhunt.command.hunters.error.hunter",
                        target.displayName
                    )
                )
            } else {
                context.source.sendError(reset("Cannot add ") + target.displayName + reset(" as a hunter because they are already a hunter!"))
            }
            return Command.SINGLE_SUCCESS
        }
        if (CONFIG.giveCompassWhenSettingHunters) fromCmdContext(context, target.uuid)!!.equip(
            8,
            ItemStack(Items.COMPASS, 1)
        )
        minecraftServer.hunters.add(target.uuid)
        if (playerHasMod) {
            context.source.sendFeedback(TranslatableText("text.manhunt.command.hunters.add", target.displayName), true)
        } else {
            context.source
                .sendFeedback(
                    reset("Added ") + target.displayName + reset(" to the hunters list!"), true
                )
        }
        return Command.SINGLE_SUCCESS
    }

    /**
     * Sends the hunter list.
     */
    private fun executeGet(context: CommandContext<ServerCommandSource>): Int {
        val playerHasMod: Boolean = playerHasMod(context)
        val hunters = context.source.minecraftServer.hunters
        if (hunters.isEmpty()) return 1
        val hunterNames: MutableList<String> = ArrayList()
        hunters.forEach { element ->
            hunterNames.add(
                fromCmdContext(context, element)!!.displayName.asString()
            )
        }
        if (playerHasMod) {
            context.source.sendFeedback(
                TranslatableText(
                    "text.manhunt.command.hunters.get",
                    java.lang.String.join(", ", hunterNames)
                ), false
            )
        } else {
            context.source.sendFeedback(LiteralText("Hunters are: " + java.lang.String.join(", ", hunterNames)), false)
        }
        return Command.SINGLE_SUCCESS
    }
}
