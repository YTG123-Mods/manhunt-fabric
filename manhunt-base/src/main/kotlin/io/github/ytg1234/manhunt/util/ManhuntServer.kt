package io.github.ytg1234.manhunt.util

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
}