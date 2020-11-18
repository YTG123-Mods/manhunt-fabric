package io.github.ytg1234.manhunt.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

/**
 * <p>
 * Callback for updating the hunters' compass.
 * Called before the compass is updated.
 * </p>
 *
 * <p>
 * Upon return, returning the {@code oldStack} parameter cancels the event,
 * and returning anything else continues to further processing by other listeners.
 * </p>
 *
 * <p>
 * The final return value is the updated hunter compass.
 * </p>
 */
@FunctionalInterface
public interface CompassUpdateCallback {
    /**
     * Called when a hunter's compass updates.
     */
    Event<CompassUpdateCallback> EVENT = EventFactory.createArrayBacked(CompassUpdateCallback.class, listeners -> (oldStack, newStack) -> {
        ItemStack currentStack = newStack.copy();
        for (CompassUpdateCallback listener : listeners) {
            currentStack = listener.onCompassUpdate(oldStack, currentStack.copy());
            if (currentStack == oldStack) return oldStack;
        }
        return currentStack;
    });

    /**
     * Receives the old, non-updated or non-compass item stack, the hunter and the updated stack.
     *
     * <p>
     * Can apply some changes to the old or the updated {@link ItemStack}, thus modifying the compass update process.
     * Returning the old stack effectively "cancelling" the update.
     * Returning a stack different than the {@code newStack} parameter changes the event behaviour.
     * </p>
     *
     * @param hunter   The hunter whose compass is being updated.
     * @param oldStack The old, non-updated compass or another {@link ItemStack}.
     * @param newStack The new, updated compass {@link ItemStack}. This stack is updated by the Manhunt mod or by another listener.
     *
     * @return The new, updated compass {@link ItemStack}.
     */
    ItemStack onCompassUpdate(ItemStack oldStack, ItemStack newStack);
}
