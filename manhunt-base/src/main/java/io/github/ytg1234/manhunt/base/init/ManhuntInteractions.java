package io.github.ytg1234.manhunt.base.init;

import java.util.Objects;

import io.github.ytg1234.manhunt.base.ManhuntUtils;
import io.github.ytg1234.manhunt.base.config.ManhuntConfigEnums;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static io.github.ytg1234.manhunt.base.ManhuntUtils.fromServer;

public final class ManhuntInteractions {
    private ManhuntInteractions() {
    }

    public static TypedActionResult<ItemStack> updateCompass(PlayerEntity user, World world, Hand hand) {
        if (!(user.getStackInHand(hand).getItem() instanceof CompassItem)) return TypedActionResult.pass(user.getStackInHand(hand));
        // If user is not sneaking we don't need
        if (user.isSneaking() &&
            ManhuntUtils.CONFIG.compassBehaviour.equals(ManhuntConfigEnums.Compass.USE) &&
            ManhuntUtils.hunters.contains(user.getUuid())) {
            // On the client we'll just return
            if (!world.isClient()) {
                ItemStack stack = user.getStackInHand(hand);
                if (stack == null) { // This should never execute as it happens when a player uses that stack
                    return TypedActionResult.pass(user.getStackInHand(hand));
                }
                if (stack.getItem().equals(Items.COMPASS)) {
                    user.equip(8, ManhuntUtils.updateCompass(stack, fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner)));
                }
            }
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
