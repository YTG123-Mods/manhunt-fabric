package io.github.ytg1234.manhunt.base.init;

import io.github.ytg1234.manhunt.api.event.callback.SpeedrunnerGlowCallback;
import io.github.ytg1234.manhunt.base.ManhuntUtils;
import io.github.ytg1234.manhunt.base.config.ManhuntConfigEnums;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;

import static io.github.ytg1234.manhunt.base.ManhuntUtils.fromServer;

public final class ManhuntTicks {
    public static void centralTick(MinecraftServer server) {
        updateCompasses(server);
        highlightSpeedrunner(server);
    }

    private static void updateCompasses(MinecraftServer server) {
        ManhuntUtils.hunters.forEach(hunterUuid -> {
            // Check if player is null
            if (fromServer(server, hunterUuid) == null) return;

            // Continue with logic
            ItemStack stack = fromServer(server, hunterUuid).inventory.getStack(8);

            // If the stack is empty, null or not a compass put a compass there
            if (stack == null || stack.isEmpty() || !stack.getItem().equals(Items.COMPASS)) {
                if (ManhuntUtils.CONFIG.giveCompassWhenSettingHunters) {
                    fromServer(server, hunterUuid).equip(8, new ItemStack(Items.COMPASS));
                    stack = fromServer(server, hunterUuid).inventory.getStack(8);
                } else if (stack == null) return;
            }
            if (ManhuntUtils.CONFIG.compassBehaviour.equals(ManhuntConfigEnums.Compass.UPDATE)) {
                // Set compass NBT
                if (stack.getItem().equals(Items.COMPASS)) {
                    fromServer(server, hunterUuid).equip(8, ManhuntUtils.updateCompass(stack, fromServer(server, ManhuntUtils.speedrunner)));
                }
            }
        });
    }

    private static void highlightSpeedrunner(MinecraftServer server) {
        // If speedrunner is null, bad.
        if (fromServer(server, ManhuntUtils.speedrunner) == null) return;
        if (ManhuntUtils.CONFIG.highlightSpeedrunner) {
            boolean toCancel = SpeedrunnerGlowCallback.EVENT.invoker().onSpeedrunnerGlow(fromServer(server, ManhuntUtils.speedrunner));
            if (!toCancel) ManhuntUtils.applyStatusEffectToPlayer(fromServer(server, ManhuntUtils.speedrunner), StatusEffects.GLOWING);
        }
    }
}
