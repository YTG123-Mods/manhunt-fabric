package io.github.ytg1234.manhunt.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.ytg1234.manhunt.Manhunt;
import io.github.ytg1234.manhunt.ManhuntUtils;
import io.github.ytg1234.manhunt.api.event.callback.SpeedrunnerGlowCallback;
import io.github.ytg1234.manhunt.command.ClearCacheCommand;
import io.github.ytg1234.manhunt.command.HuntersCommand;
import io.github.ytg1234.manhunt.command.SpeedrunnerCommand;
import io.github.ytg1234.manhunt.config.Behaviours;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import static io.github.ytg1234.manhunt.ManhuntUtils.fromServer;

public final class ManhuntEventRegistration {
    private ManhuntEventRegistration() {
    }

    public static void registerCommonEvents() {
        ServerTickEvents.END_SERVER_TICK.register(ManhuntEventRegistration::updateCompasses);
        ServerTickEvents.END_SERVER_TICK.register(ManhuntEventRegistration::highlightSpeedrunner);
        CommandRegistrationCallback.EVENT.register(ManhuntEventRegistration::registerCommands);

        ServerPlayNetworking.registerGlobalReceiver(ManhuntUtils.CLIENT_ANSWER_PACKET_ID, (server, player, handler, data, sender) -> {
            server.execute(() -> {
                if (!ManhuntUtils.haveMod.contains(player)) ManhuntUtils.haveMod.add(player);
            });
        });
    }

    private static void updateCompasses(MinecraftServer server) {
        ManhuntUtils.hunters.forEach(hunterUuid -> {
            // Check if player is null
            if (fromServer(server, hunterUuid) == null) return;

            // Continue with logic
            ItemStack stack = fromServer(server, hunterUuid).inventory.getStack(8);

            // If the stack is empty, null or not a compass put a compass there
            if (stack == null || stack.isEmpty() || !stack.getItem().equals(Items.COMPASS)) {
                if (Manhunt.CONFIG.giveCompassWhenSettingHunters) {
                    fromServer(server, hunterUuid).equip(8, new ItemStack(Items.COMPASS));
                    stack = fromServer(server, hunterUuid).inventory.getStack(8);
                } else if (stack == null) return;
            }
            if (Manhunt.CONFIG.compassBehaviour.equals(Behaviours.Compass.UPDATE)) {
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
        if (Manhunt.CONFIG.highlightSpeedrunner) {
            boolean toCancel = SpeedrunnerGlowCallback.EVENT.invoker().onSpeedrunnerGlow(fromServer(server, ManhuntUtils.speedrunner));
            if (!toCancel) ManhuntUtils.applyStatusEffectToPlayer(fromServer(server, ManhuntUtils.speedrunner), StatusEffects.GLOWING);
        }
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean b) {
        SpeedrunnerCommand.register(commandDispatcher);
        HuntersCommand.register(commandDispatcher);
        ClearCacheCommand.register(commandDispatcher);
    }

    public static void registerClientSideEvents() {
        ClientPlayNetworking.registerGlobalReceiver(ManhuntUtils.SERVER_QUESTION_PACKET_ID, (client, handler, data, sender) -> {
            client.execute(() -> {
                ClientPlayNetworking.send(ManhuntUtils.CLIENT_ANSWER_PACKET_ID, PacketByteBufs.empty());
            });
        });
    }
}
