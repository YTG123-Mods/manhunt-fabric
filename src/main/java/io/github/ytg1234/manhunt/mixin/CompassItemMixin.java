package io.github.ytg1234.manhunt.mixin;

import io.github.ytg1234.manhunt.Manhunt;
import io.github.ytg1234.manhunt.ManhuntUtils;
import io.github.ytg1234.manhunt.config.Behaviours;
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

import static io.github.ytg1234.manhunt.ManhuntUtils.fromServer;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin extends Item {
    private CompassItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking() && Manhunt.CONFIG.compassBehaviour.equals(Behaviours.Compass.USE) && ManhuntUtils.hunters.contains(user.getUuid())) {
            if (!world.isClient()) {
                for (int i = 0; i < user.inventory.size(); i++) {
                    if (user.inventory.getStack(i) == null || ManhuntUtils.speedrunner == null) continue;
                    ItemStack stack = user.inventory.getStack(i);
                    if (stack.getItem().equals(Items.COMPASS)) {
                        user.equip(i, ManhuntUtils.updateCompass(stack, fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner)));
                        //                        CompoundTag itemTag = stack.getTag() == null ? new CompoundTag() : stack.getTag().copy();
                        //                        itemTag.putBoolean("LodestoneTracked", false);
                        //                        itemTag.putString("LodestoneDimension", fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner).getServerWorld().getRegistryKey().getValue().toString());
                        //                        CompoundTag lodestonePos = new CompoundTag();
                        //                        lodestonePos.putInt("X", (int) fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner).getX());
                        //                        lodestonePos.putInt("Y", (int) fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner).getY());
                        //                        lodestonePos.putInt("Z", (int) fromServer(Objects.requireNonNull(user.getServer()), ManhuntUtils.speedrunner).getZ());
                        //                        itemTag.put("LodestonePos", lodestonePos);
                        //                        stack.setTag(itemTag);
                    }
                }
            }
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        }
        return super.use(world, user, hand);
    }
}
