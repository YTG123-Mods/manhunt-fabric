package io.github.ytg1234.manhunt.config

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.EnumHandler
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment
import java.util.ArrayList


@Config(name = "manhunt")
class ManhuntConfig : ConfigData {
    @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
    @Comment("Sets the behaviour of the compass mechanic, can be either UPDATE or USE. UPDATE = Automatically update the compass every tick. USE = Use the compass to update it (more like Dream's manhunt).")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @JvmField
    var compassBehaviour = Compass.USE

    @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
    @Comment("Sets the behaviour of the damage mechanic. Can be either KILL or DAMAGE. KILL = The speedrunner loses when they are killed (like Dream's manhunt). DAMAGE = The speedrunner loses when they take damage (like dream's assassin).")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @JvmField
    var damageBehaviour = Damage.KILL

    @Comment("If true, gives players a compass when added to the hunters list.")
    @ConfigEntry.Gui.Tooltip
    @JvmField
    var giveCompassWhenSettingHunters = true

    // This is a new list because auto config will try to append the values in the Json file.
    @Comment("Dimensions that the compass won't work in.")
    @ConfigEntry.Gui.Tooltip
    @JvmField
    var disabledDimensions: List<String> = ArrayList()

    @Comment("Whether to apply the glowing effect to the speedrunner, similar to Dream's Survivalist")
    @ConfigEntry.Gui.Tooltip
    @JvmField
    var highlightSpeedrunner = false
}

