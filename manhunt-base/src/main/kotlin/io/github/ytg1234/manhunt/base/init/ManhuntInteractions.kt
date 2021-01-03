package io.github.ytg1234.manhunt.base.init

import io.github.ytg1234.manhunt.base.*
import io.github.ytg1234.manhunt.config.Compass
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.CompassItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

object ManhuntInteractions {
    fun pointCompass(user: PlayerEntity, world: World, hand: Hand): TypedActionResult<ItemStack?>? {
        if (user.getStackInHand(hand).item !is CompassItem) return TypedActionResult.pass(user.getStackInHand(hand))

        // If user is not sneaking we don't need
        if (user.isSneaking &&
            CONFIG!!.compassBehaviour == Compass.USE &&
            hunters.contains(user.uuid)
        ) {
            // On the client we'll just return
            if (!world.isClient()) {
                val stack = user.getStackInHand(hand)
                if (stack.item == Items.COMPASS) {
                    user.equip(
                        8,
                        updateCompass(stack, fromServer(user.server!!, speedrunner))
                    )
                }
            }
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient())
        }
        return TypedActionResult.pass(user.getStackInHand(hand))
    }
}
