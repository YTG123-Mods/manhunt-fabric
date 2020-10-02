package com.ytg123.manhunt.mixin;

import com.ytg123.manhunt.Manhunt;
import com.ytg123.manhunt.ManhuntUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static com.ytg123.manhunt.config.Behaviours.*;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin extends Item {
    private CompassItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking() && Manhunt.CONFIG.compassBehaviour.equals(Compass.USE) && ManhuntUtils.hunters.contains(user)) {
            if (!world.isClient()) {
                for (int i = 0; i < user.inventory.size(); i++) {
                    if (user.inventory.getStack(i) == null || ManhuntUtils.speedrunner == null) continue;
                    ItemStack stack = user.inventory.getStack(i);
                    if (stack.getItem().equals(Items.COMPASS)) {
                        CompoundTag itemTag = stack.getTag() == null ? new CompoundTag() : stack.getTag().copy();
                        itemTag.putBoolean("LodestoneTracked", false);
                        itemTag.putString("LodestoneDimension", ManhuntUtils.speedrunner.getServerWorld().getRegistryKey().getValue().toString());
                        CompoundTag lodestonePos = new CompoundTag();
                        lodestonePos.putInt("X", (int) ManhuntUtils.speedrunner.getX());
                        lodestonePos.putInt("Y", (int) ManhuntUtils.speedrunner.getY());
                        lodestonePos.putInt("Z", (int) ManhuntUtils.speedrunner.getZ());
                        itemTag.put("LodestonePos", lodestonePos);
                        stack.setTag(itemTag);
                    }
                }
            }
            return TypedActionResult.method_29237(user.getStackInHand(hand), world.isClient());
        }
        return super.use(world, user, hand);
    }
}
