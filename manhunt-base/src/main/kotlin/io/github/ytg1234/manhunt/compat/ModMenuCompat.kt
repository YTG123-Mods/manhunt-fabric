package io.github.ytg1234.manhunt.compat

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import io.github.ytg1234.manhunt.config.ManhuntConfig
import me.shedaniel.autoconfig.AutoConfig
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

/**
 * Compatibility with ModMenu.
 *
 * @author YTG1234
 */
@Environment(EnvType.CLIENT)
object ModMenuCompat : ModMenuApi {
    /**
     * Used to construct a new config screen instance when your mod's
     * configuration button is selected on the mod menu screen. The
     * screen instance parameter is the active mod menu screen.
     *
     * @return A factory for constructing config screen instances.
     */
    override fun getModConfigScreenFactory() = ConfigScreenFactory {
        AutoConfig.getConfigScreen(ManhuntConfig::class.java, it).get()
    }
}
