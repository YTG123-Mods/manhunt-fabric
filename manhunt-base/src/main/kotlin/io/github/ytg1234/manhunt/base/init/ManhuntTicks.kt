package io.github.ytg1234.manhunt.base.init

import io.github.ytg1234.manhunt.api.event.callback.SpeedrunnerGlowCallback
import io.github.ytg1234.manhunt.base.CONFIG
import io.github.ytg1234.manhunt.base.applyStatusEffectToPlayer
import io.github.ytg1234.manhunt.base.fromServer
import io.github.ytg1234.manhunt.base.hunters
import io.github.ytg1234.manhunt.base.speedrunner
import io.github.ytg1234.manhunt.base.updateCompass
import io.github.ytg1234.manhunt.config.Compass
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer

object ManhuntTicks {
    fun centralTick(server: MinecraftServer) {
        updateCompasses(server)
        highlightSpeedrunner(server)
    }

    private fun updateCompasses(server: MinecraftServer) {
        hunters.forEach { hunterUuid ->
            // Continue with logic
            var stack: ItemStack = fromServer(server, hunterUuid).inventory.getStack(8)

            // If the stack is empty, null or not a compass put a compass there
            if (stack.isEmpty || stack.item != Items.COMPASS) {
                if (CONFIG!!.giveCompassWhenSettingHunters) {
                    fromServer(server, hunterUuid).equip(8, ItemStack(Items.COMPASS))
                    stack = fromServer(server, hunterUuid).inventory.getStack(8)
                }
            }
            if (CONFIG!!.compassBehaviour == Compass.UPDATE) {
                // Set compass NBT
                if (stack.item == Items.COMPASS) {
                    fromServer(server, hunterUuid).equip(
                        8,
                        updateCompass(stack, fromServer(server, speedrunner))
                    )
                }
            }
        }
    }

    private fun highlightSpeedrunner(server: MinecraftServer) {
        // If speedrunner is null, bad.
        if (CONFIG!!.highlightSpeedrunner) {
            val toCancel =
                SpeedrunnerGlowCallback.EVENT.invoker().onSpeedrunnerGlow(fromServer(server, speedrunner))
            if (!toCancel) applyStatusEffectToPlayer(
                fromServer(server, speedrunner),
                StatusEffects.GLOWING
            )
        }
    }
}
