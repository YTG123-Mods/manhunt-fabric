package io.github.ytg1234.manhunt.base

import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

/**
 * Initializes the mod.
 *
 * @author YTG1234
 */
object Manhunt : ModInitializer {
    override fun onInitialize(mod: ModContainer?) {
        LOGGER.info("Initializing")
        ManhuntEventRegistration.registerCommonEvents()
    }
}
