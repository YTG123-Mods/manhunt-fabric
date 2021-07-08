package io.github.ytg1234.manhunt.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
import java.util.ArrayList

/**
 * A class that holds and represents the configuration of the Manhunt mod.
 *
 * @author YTG1234
 */
@Config(name = "manhunt")
class ManhuntConfig : ConfigData {
    /**
     * Sets the behaviour of the compass mechanic, can be either [UPDATE][Compass.UPDATE] or [USE][Compass.USE].
     *
     * [UPDATE][Compass.UPDATE] = Automatically update the compass every tick.
     * [USE][Compass.USE] = Use the compass to update it (more like Dream's manhunt).
     */
    @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
    @Comment("Sets the behaviour of the compass mechanic, can be either UPDATE or USE. UPDATE = Automatically update the compass every tick. USE = Use the compass to update it (more like Dream's manhunt).")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @JvmField
    var compassBehaviour = Compass.USE

    /**
     * Sets the behaviour of the damage mechanic. Can be either [KILL][Damage.KILL] or [DAMAGE][Damage.DAMAGE].
     *
     * [KILL][Damage.KILL] = The speedrunner loses when they are killed (like Dream's manhunt).
     * [DAMAGE][Damage.DAMAGE] = The speedrunner loses when they take damage (like dream's assassin).
     */
    @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
    @Comment("Sets the behaviour of the damage mechanic. Can be either KILL or DAMAGE. KILL = The speedrunner loses when they are killed (like Dream's manhunt). DAMAGE = The speedrunner loses when they take damage (like dream's assassin).")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @JvmField
    var damageBehaviour = Damage.KILL

    /**
     * If true, gives players a compass when added to the hunters list.
     */
    @Comment("If true, gives players a compass when added to the hunters list.")
    @ConfigEntry.Gui.Tooltip
    @JvmField
    var giveCompassWhenSettingHunters = true

    // This is a new list because auto config will try to append the values in the Json file.
    /**
     * Dimensions that the compass won't work in.
     */
    @Comment("Dimensions that the compass won't work in.")
    @ConfigEntry.Gui.Tooltip
    @JvmField
    var disabledDimensions: List<String> = ArrayList()

    /**
     * Whether to apply the glowing effect to the speedrunner, similar to Dream's Survivalist
     */
    @Comment("Whether to apply the glowing effect to the speedrunner, similar to Dream's Survivalist.")
    @ConfigEntry.Gui.Tooltip
    @JvmField
    var highlightSpeedrunner = false
}

