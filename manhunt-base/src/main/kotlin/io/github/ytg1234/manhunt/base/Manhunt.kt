package io.github.ytg1234.manhunt.base

import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration
import net.fabricmc.api.ModInitializer

/**
 * Initializes the mod.
 *
 * @author YTG1234
 */
object Manhunt : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("Initializing")
        ManhuntEventRegistration.registerCommonEvents()
    }
}
