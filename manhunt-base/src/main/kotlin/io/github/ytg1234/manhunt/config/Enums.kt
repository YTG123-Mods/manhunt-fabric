package io.github.ytg1234.manhunt.config

/**
 * Decides the behaviour of the hunters' compass.
 *
 * @property UPDATE Compass Updates automatically every tick.
 * @property USE Hunter has to use compass to update it.
 *
 * @author YTG1234
 */
enum class Compass {
    UPDATE, USE
}

/**
 * Decides the "damage mode", when the speedrunner dies.
 *
 * @property DAMAGE The speedrunner dies when they take damage.
 * @property KILL The speedrunner dies when they die, like normal Minecraft.
 *
 * @author YTG1234
 */
enum class Damage {
    DAMAGE, KILL
}
