package io.github.ytg1234.manhunt.base.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Redirect(method = "dropAll()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private boolean preventCompass(ItemStack self) {
        if (self.getItem().equals(Items.COMPASS)) {
            return true;
        } else {
            return self.isEmpty();
        }
    }

    //    @Inject(method = "dropAll()V",
    //            at = @At(value = "INVOKE",
    //                     target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;"),
    //            locals = LocalCapture.CAPTURE_FAILHARD)
    //    private void notDropCompass(CallbackInfo ci, Iterator<DefaultedList<ItemStack>> thing, List<ItemStack> itemStacks, int i, ItemStack currentStack) {
    //        if (currentStack.getItem().equals(Items.COMPASS)) {
    //
    //        }
    //    }
}
