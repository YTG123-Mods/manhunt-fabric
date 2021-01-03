package io.github.ytg1234.manhunt.base

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.github.ytg1234.manhunt.api.event.callback.CompassUpdateCallback
import io.github.ytg1234.manhunt.base.config.ManhuntConfig
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import java.util.*

const val MOD_ID = "manhunt"
const val MOD_NAME = "Manhunt Fabric"

@JvmField val hunters: MutableList<UUID> = mutableListOf()
@JvmField val SERVER_QUESTION_PACKET_ID: Identifier = Identifier(MOD_ID, "question")
@JvmField val CLIENT_ANSWER_PACKET_ID: Identifier = Identifier(MOD_ID, "answer")
@JvmField val haveMod: MutableList<PlayerEntity> = mutableListOf()
@JvmField val LOGGER = LogManager.getLogger(MOD_NAME)
@JvmField val speedrunner: UUID? = null
@JvmField var CONFIG: ManhuntConfig? = null
@JvmField var runActive = false

@Throws(CommandSyntaxException::class)
fun playerHasMod(context: CommandContext<ServerCommandSource>): Boolean {
    return context.source.entity != null &&
            context.source.entity is PlayerEntity &&
            haveMod.contains(context.source.player)
}

fun fromCmdContext(ctx: CommandContext<ServerCommandSource>, uuid: UUID?): ServerPlayerEntity {
    return ctx.source.minecraftServer.playerManager.getPlayer(uuid)!!
}

fun fromServer(server: MinecraftServer, uuid: UUID?): ServerPlayerEntity {
    return server.playerManager.getPlayer(uuid)!!
}

fun updateCompass(compass: ItemStack, target: ServerPlayerEntity?): ItemStack {
    // Is target null?
    if (target == null) {
        LOGGER.warn("Compass target is null, cannot update compass! Please fix!")
        return compass.copy()
    }
    // Is dimension disabled?
    if (CONFIG!!.disabledDimensions.contains(target.serverWorld.registryKey.value.toString())) return compass.copy()

    // Continue Updating
    val oldCompass = compass.copy()
    var newCompass = compass.copy()
    val itemTag = newCompass.orCreateTag.copy()
    itemTag.putBoolean("LodestoneTracked", false)
    itemTag.putString("LodestoneDimension", target.serverWorld.registryKey.value.toString())
    val lodestonePos = CompoundTag()
    lodestonePos.putInt("X", target.x.toInt())
    lodestonePos.putInt("Y", target.y.toInt())
    lodestonePos.putInt("Z", target.z.toInt())
    itemTag.put("LodestonePos", lodestonePos)
    newCompass.tag = itemTag
    newCompass = CompassUpdateCallback.EVENT.invoker().onCompassUpdate(oldCompass, newCompass)
    return newCompass
}

fun applyStatusEffectToPlayer(player: PlayerEntity, effect: StatusEffect?): Boolean {
    return player.addStatusEffect(StatusEffectInstance(effect, 2, 0, false, false))
}
