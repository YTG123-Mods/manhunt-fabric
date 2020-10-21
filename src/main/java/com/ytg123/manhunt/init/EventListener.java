package com.ytg123.manhunt.init;

import com.mojang.brigadier.CommandDispatcher;
import com.ytg123.manhunt.Manhunt;
import com.ytg123.manhunt.ManhuntUtils;
import com.ytg123.manhunt.command.ClearCacheCommand;
import com.ytg123.manhunt.command.HuntersCommand;
import com.ytg123.manhunt.command.SpeedrunnerCommand;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import static com.ytg123.manhunt.ManhuntUtils.fromServer;
import static com.ytg123.manhunt.config.Behaviours.Compass;

public final class EventListener {
    private EventListener() {
    }

    public static void onEndTick(MinecraftServer server) {
        ManhuntUtils.hunters.forEach(hunterUuid -> {
            // Check if player is null
            if (fromServer(server, hunterUuid) == null) {
                return;
            }

            // Continue with logic
            ItemStack stack = fromServer(server, hunterUuid).inventory.getStack(8);

            // If the stack is empty, null or not a compass put a compass there
            if (stack == null || stack.isEmpty() || !stack.getItem().equals(Items.COMPASS)) {
                if (Manhunt.CONFIG.giveCompassWhenSettingHunters) {
                    fromServer(server, hunterUuid).equip(8, new ItemStack(Items.COMPASS));
                    stack = fromServer(server, hunterUuid).inventory.getStack(8);
                } else if (stack == null) return;
            }
            if (Manhunt.CONFIG.compassBehaviour.equals(Compass.UPDATE)) {
                // Set compass NBT
                if (stack.getItem().equals(Items.COMPASS)) {
                    ManhuntUtils.updateCompass(stack, fromServer(server, ManhuntUtils.speedrunner));
                }
            }
        });
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean b) {
        SpeedrunnerCommand.register(commandDispatcher);
        HuntersCommand.register(commandDispatcher);
        ClearCacheCommand.register(commandDispatcher);
    }
}
