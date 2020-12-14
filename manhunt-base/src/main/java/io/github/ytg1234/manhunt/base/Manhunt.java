package io.github.ytg1234.manhunt.base;

import io.github.ytg1234.manhunt.base.config.ManhuntConfig;
import io.github.ytg1234.manhunt.base.init.ManhuntEventRegistration;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Manhunt implements ModInitializer {
    public static final String MOD_ID = "manhunt";
    public static final String MOD_NAME = "Manhunt Fabric";
    public static ManhuntConfig CONFIG;

    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

    /**
     * Initializes the mod.
     */
    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        AutoConfig.register(ManhuntConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ManhuntConfig.class).getConfig();

        ManhuntEventRegistration.registerCommonEvents();
    }

    /**
     * Logs a message to the console.
     *
     * @param level   The log level
     * @param message The message being logged
     */
    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }
}
