package io.github.ytg1234.manhunt.config

/**
 * Decides the behaviour of the hunters' compass.
 *
 * @author YTG1234
 */
enum class Compass {
    /**
     * Compass Updates automatically every tick.
     */
    UPDATE,

    /**
     * Hunter has to use compass to update it.
     */
    USE
}

/**
 * Decides the "damage mode", aka when the speedrunner dies.
 *
 * @author YTG1234
 */
enum class Damage {
    /**
     * The speedrunner dies when they take damage.
     */
    DAMAGE,

    /**
     * The speedrunner dies when they die, like normal Minecraft.
     */
    KILL
}
