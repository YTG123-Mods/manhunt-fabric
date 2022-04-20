package io.github.ytg1234.manhunt.base.init

import io.github.ytg1234.manhunt.api.event.callback.SpeedrunnerGlowCallback
import io.github.ytg1234.manhunt.base.*
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer

/**
 * Tick tock.
 *
 * @author YTG1234
 */
object ManhuntTicks {
    /**
     * Activates all tick events.
     *
     * @see net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.EndTick.onEndTick
     */
    fun centralTick(server: MinecraftServer) {
        updateCompasses(server)
        highlightSpeedrunner(server)
    }

    /**
     * Updates the compasses of each hunter.
     *
     * @see net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.EndTick.onEndTick
     */
    private fun updateCompasses(server: MinecraftServer) {
        hunters.forEach { hunterUuid ->
            // Continue with logic
            if (fromServer(server, hunterUuid) == null) return@forEach
            var stack: ItemStack = fromServer(server, hunterUuid)!!.inventory.getStack(8)

            // If the stack is empty, null or not a compass put a compass there
            if (stack.isEmpty || stack.item != Items.COMPASS) {
//                if (CONFIG!!.giveCompassWhenSettingHunters) {
                    fromServer(server, hunterUuid)!!.inventory.setStack(8, ItemStack(Items.COMPASS))
                    stack = fromServer(server, hunterUuid)!!.inventory.getStack(8)
//                }
            }
//            if (CONFIG!!.compassBehaviour == Compass.UPDATE) {
//                // Set compass NBT
//                if (stack.item == Items.COMPASS) {
//                    fromServer(server, hunterUuid)!!.inventory.setStack(
//                        8,
//                        updateCompass(stack, fromServer(server, speedrunner))
//                    )
//                }
//            }
        }
    }

    /**
     * Applies the glowing effect to the speedrunner
     * if the configuration allows that.
     *
     * @see net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.EndTick.onEndTick
     */
    private fun highlightSpeedrunner(server: MinecraftServer) {
        // If speedrunner is null, bad.
//        if (CONFIG!!.highlightSpeedrunner) {
//            val toCancel =
//                SpeedrunnerGlowCallback.EVENT.invoker().onSpeedrunnerGlow(fromServer(server, speedrunner))
//            if (!toCancel && speedrunner != null) applyStatusEffectToPlayer(
//                fromServer(server, speedrunner)!!,
//                StatusEffects.GLOWING
//            )
//        }
    }
}
