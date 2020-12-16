package io.github.ytg1234.manhunt.base;

import io.github.ytg1234.manhunt.base.config.ManhuntConfig;
import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import static io.github.ytg1234.manhunt.base.ManhuntUtils.LOGGER;

public class Manhunt implements ModInitializer {

    /**
     * Initializes the mod.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing");
        AutoConfig.register(ManhuntConfig.class, JanksonConfigSerializer::new);
        ManhuntUtils.CONFIG = AutoConfig.getConfigHolder(ManhuntConfig.class).getConfig();

        ManhuntEventRegistration.registerCommonEvents();
    }
}
