package io.github.ytg1234.manhunt.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Redirect(method = "updateHeldItems",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/item/ItemStack;areEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"))
    private boolean disableCompassAnimation(ItemStack original, ItemStack updated) {
        if (updated.getItem().equals(original.getItem())) {
            if (original.getItem().equals(Items.COMPASS)) return true;
        }

        return ItemStack.areEqual(original, updated);
    }
}
