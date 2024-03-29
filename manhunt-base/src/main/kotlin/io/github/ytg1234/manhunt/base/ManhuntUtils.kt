package io.github.ytg1234.manhunt.base

import com.mojang.brigadier.context.CommandContext
import io.github.ytg1234.manhunt.api.event.callback.CompassUpdateCallback
import io.github.ytg1234.manhunt.config.ManhuntConfig
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.jetbrains.annotations.Contract
import java.util.UUID

/**
 * Manhunt's mod id.
 */
const val MOD_ID = "manhunt"

/**
 * The name of the mod.
 */
const val MOD_NAME = "Manhunt Fabric"

/**
 * Contains all the currently active hunters.
 */
@JvmField
val hunters: MutableList<UUID> = mutableListOf()

/**
 * Contains the [Identifier] of the question packet.
 */
@JvmField
val SERVER_QUESTION_PACKET_ID: Identifier = Identifier(MOD_ID, "question")

/**
 * Contains the [Identifier] of the answer packet.
 */
@JvmField
val CLIENT_ANSWER_PACKET_ID: Identifier = Identifier(MOD_ID, "answer")

/**
 * Contains every [player][PlayerEntity] that has the mod
 * on their client.
 */
@JvmField
val haveMod: MutableList<PlayerEntity> = mutableListOf()

/**
 * Manhunt's [Log4j logger][org.apache.logging.log4j.Logger]
 */
@JvmField
val LOGGER = LogManager.getLogger(MOD_NAME)!!

/**
 * Contains the currently active speedrunner.
 */
@JvmField
var speedrunner: UUID? = null

/**
 * The one and only Manhunt configuration.
 */
@JvmField
var CONFIG: ManhuntConfig? = null

/**
 * Checks if a player has the Manhunt mod on the client side.
 *
 * @param context the [CommandContext] to get the player from
 *
 * @return if the player has the mod
 */
@Contract(pure = true)
fun playerHasMod(context: CommandContext<ServerCommandSource>): Boolean {
    return context.source.entity != null &&
            context.source.entity is PlayerEntity &&
            haveMod.contains(context.source.player)
}

/**
 * Gets a [PlayerEntity] from a [CommandContext] and a [UUID].
 *
 * @param ctx the context to get the server from
 * @param uuid the UUID of the player to get
 *
 * @return the [ServerPlayerEntity] object
 */
@Contract(pure = true)
fun fromCmdContext(ctx: CommandContext<ServerCommandSource>, uuid: UUID?): ServerPlayerEntity? {
    return fromServer(ctx.source.server, uuid)
}

/**
 * Gets a [PlayerEntity] that is connected to a [MinecraftServer]
 * using their [UUID].
 *
 * @param server the server to get the player from
 * @param uuid the UUID of the player
 *
 * @return the [ServerPlayerEntity] object
 */
@Contract(pure = true)
fun fromServer(server: MinecraftServer, uuid: UUID?): ServerPlayerEntity? {
    return if (uuid == null) null else server.playerManager.getPlayer(uuid)
}

/**
 * Receives an [ItemStack] and a [ServerPlayerEntity], assuming
 * the [ItemStack] is a compass updates it to point to the [ServerPlayerEntity].
 *
 * @param compass the compass to update
 * @param target the target that the compass shall point to
 *
 * @return the updated [ItemStack], without mutating the first param
 */
@Contract(pure = true)
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
    val itemTag = newCompass.orCreateNbt.copy()
    itemTag.putBoolean("LodestoneTracked", false)
    itemTag.putString("LodestoneDimension", target.serverWorld.registryKey.value.toString())
    val lodestonePos = NbtCompound()
    lodestonePos.putInt("X", target.x.toInt())
    lodestonePos.putInt("Y", target.y.toInt())
    lodestonePos.putInt("Z", target.z.toInt())
    itemTag.put("LodestonePos", lodestonePos)
    newCompass.nbt = itemTag
    newCompass = CompassUpdateCallback.EVENT.invoker().onCompassUpdate(oldCompass, newCompass)
    return newCompass
}

/**
 * Applies the specified status effect to a player for 2 ticks.
 *
 * @param player the player to apply the effect to
 * @param effect the effect to apply
 *
 * @return whether the effect applied
 *
 * @see LivingEntity.addStatusEffect
 */
@Contract(mutates = "param1")
fun applyStatusEffectToPlayer(player: PlayerEntity, effect: StatusEffect?): Boolean {
    return player.addStatusEffect(StatusEffectInstance(effect, 2, 0, false, false))
}
