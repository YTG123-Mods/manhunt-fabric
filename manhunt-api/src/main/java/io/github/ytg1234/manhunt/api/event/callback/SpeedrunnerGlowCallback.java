package io.github.ytg1234.manhunt.api.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Called when the glowing effect is applied to the speedrunner.
 *
 * @author YTG1234
 * @since 1.0.0
 */
public interface SpeedrunnerGlowCallback {
    Event<SpeedrunnerGlowCallback> EVENT = EventFactory.createArrayBacked(SpeedrunnerGlowCallback.class, listeners -> speedrunner -> {
        for (SpeedrunnerGlowCallback listener : listeners) {
            boolean result = listener.onSpeedrunnerGlow(speedrunner);
            if (result) return true;
        }
        return false;
    });

    /**
     * Receives the {@link PlayerEntity speedrunner} that is glowing and
     * determines whether to cancel the glow.
     *
     * @param speedrunner the speedrunner that is glowing
     *
     * @return Whether to cancel the glowing.
     */
    boolean onSpeedrunnerGlow(PlayerEntity speedrunner);
}
