package io.github.ytg1234.manhunt.compat

import io.github.prospector.modmenu.api.ConfigScreenFactory
import io.github.prospector.modmenu.api.ModMenuApi
import io.github.ytg1234.manhunt.config.ManhuntConfig
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object ModMenuCompat : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory {
        AutoConfig.getConfigScreen(ManhuntConfig::class.java, it).get()
    }
}
