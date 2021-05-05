package io.github.ytg1234.manhunt.util

import net.minecraft.entity.player.PlayerEntity
import java.util.*

interface ManhuntServer {
    /**
     * SpeedRunner in the server.
     * @see io.github.ytg1234.manhunt.base.speedRunner
     */
    var speedRunner : UUID?

    /**
     * Hunters in the server.
     * @see io.github.ytg1234.manhunt.base.hunters
     */
    val hunters: MutableList<UUID>

    /**
     * Contains every [player][PlayerEntity] that has the mod
     * on their client.
     */
    val haveMod : MutableList<PlayerEntity>
}