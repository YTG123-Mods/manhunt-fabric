package io.github.ytg1234.manhunt.util

import mc.aegis.AegisCommandBuilder
import me.lucko.fabric.api.permissions.v0.Permissions
import net.fabricmc.loader.api.FabricLoader

/**
 * A simple utility class to manage commands
 * with permissions.
 *
 * @param literal the command name
 * @param permNode the permission node required for the command
 * @param permLevel the operator permission level required if node is not present
 *
 * @constructor Configures the class to use the correct settings.
 *
 * @author YTG1234
 */
abstract class PermedCommand(val literal: String, val permNode: String, val permLevel: Int = 0) {
    /**
     * Takes a [AegisCommandBuilder], applies some modifications and
     * returns a builder. The return value *should* probably be `this`,
     * however it isn't required.
     */
    abstract val cmd: AegisCommandBuilder.() -> AegisCommandBuilder

    /**
     * Registers the command without permissions.
     *
     * @param dispatcher the dispatcher to register the command on
     */
    private fun register(dispatcher: Dispatcher) {
        dispatcher.register(
            AegisCommandBuilder(literal) {
                requires { it.hasPermissionLevel(permLevel) }
            }.cmd().build()
        )
    }

    /**
     * Registers the command with permission
     * checks, must **only** be called if Lucko's permission
     * API is present.
     *
     * @param dispatcher the dispatcher to register the command on
     *
     * @see register
     */
    private fun registerWithPerms(dispatcher: Dispatcher) {
        dispatcher.register(
            AegisCommandBuilder(literal) {
                requires { Permissions.require(permNode, permLevel).test(it) } // this is because Kotlin compiler is irrit
            }.cmd().build()
        )
    }

    /**
     * Conditionally registers the command either
     * with permission checks or without.
     *
     * @param dispatcher the dispatcher to register the command on
     *
     * @see register
     * @see registerWithPerms
     */
    fun registerCmd(dispatcher: Dispatcher) {
        if (FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0")) registerWithPerms(dispatcher)
        else register(dispatcher)
    }
}
