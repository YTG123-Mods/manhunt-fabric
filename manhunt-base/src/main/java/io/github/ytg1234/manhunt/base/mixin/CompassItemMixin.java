package io.github.ytg1234.manhunt.base.mixin;

import io.github.ytg1234.manhunt.base.Manhunt;
import io.github.ytg1234.manhunt.base.ManhuntUtils;
import io.github.ytg1234.manhunt.base.config.Behaviours;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

import static io.github.ytg1234.manhunt.base.ManhuntUtils.fromServer;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin extends Item {
    private CompassItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking() && Manhunt.CONFIG.compassBehaviour.equals(Behaviours.Compass.USE) && ManhuntUtils.hunters.contains(user.getUuid())) {
            if (!world.isClient()) {
                ItemStack stack = user.getStackInHand(hand);
                if (stack == null) { // This should never execute as it happens when a player uses that stack
                    return super.use(world, user, hand);
                }
                if (stack.getItem().equals(Items.COMPASS)) {
                    user.equip(8, ManhuntUtils.updateCompass(stack, fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner)));
                }
            }
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        }
        return super.use(world, user, hand);
    }
}
