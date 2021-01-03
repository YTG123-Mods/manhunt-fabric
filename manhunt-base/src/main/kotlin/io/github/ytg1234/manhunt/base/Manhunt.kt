package io.github.ytg1234.manhunt.base

import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration
import io.github.ytg1234.manhunt.config.ManhuntConfig
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer
import net.fabricmc.api.ModInitializer

object Manhunt : ModInitializer {
    /**
     * Initializes the mod.
     */
    override fun onInitialize() {
        LOGGER.info("Initializing")
        AutoConfig.register(ManhuntConfig::class.java, ::JanksonConfigSerializer)
        CONFIG = AutoConfig.getConfigHolder(ManhuntConfig::class.java).config
        ManhuntEventRegistration.registerCommonEvents()
    }
}
