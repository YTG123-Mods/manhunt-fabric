package io.github.ytg1234.manhunt.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.ytg1234.manhunt.Manhunt;
import io.github.ytg1234.manhunt.ManhuntUtils;
import io.github.ytg1234.manhunt.command.ClearCacheCommand;
import io.github.ytg1234.manhunt.command.HuntersCommand;
import io.github.ytg1234.manhunt.command.SpeedrunnerCommand;
import io.github.ytg1234.manhunt.config.Behaviours;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.apache.logging.log4j.Level;

import static io.github.ytg1234.manhunt.ManhuntUtils.freezePlayer;
import static io.github.ytg1234.manhunt.ManhuntUtils.fromServer;

public final class EventListener {
    private EventListener() {
    }

    public static void freezeHunters(MinecraftServer server) {
        ServerPlayerEntity speedrunner = fromServer(server, ManhuntUtils.speedrunner);
        if (speedrunner == null) return;
        HitResult raycastResult = speedrunner.raycast(20, 0, false);
        Manhunt.log(Level.INFO, "successfully raycasted");
        if (raycastResult == null) return;
        Manhunt.log(Level.INFO, "raycast is not null");
        if (raycastResult.getType().equals(HitResult.Type.MISS)) Manhunt.log(Level.INFO, "raycast missed");
        if (raycastResult.getType().equals(HitResult.Type.BLOCK)) Manhunt.log(Level.INFO, "raycast block");
        if (raycastResult.getType().equals(HitResult.Type.ENTITY)) {
            Manhunt.log(Level.INFO, "raycast found entity");
            EntityHitResult raycastEntityResult = (EntityHitResult) raycastResult;
            if (raycastEntityResult.getEntity() instanceof PlayerEntity) {
                Manhunt.log(Level.INFO, "entity is a pleyer");
                PlayerEntity pe = (PlayerEntity) raycastEntityResult.getEntity();
                if (ManhuntUtils.hunters.contains(pe.getUuid())) {
                    Manhunt.log(Level.INFO, "Player is a hunter");
                    freezePlayer(pe);
                    Manhunt.log(Level.INFO, "frozen player");
                }
            }
        }
    }

    public static void updateCompass(MinecraftServer server) {
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
            if (Manhunt.CONFIG.compassBehaviour.equals(Behaviours.Compass.UPDATE)) {
                // Set compass NBT
                if (stack.getItem().equals(Items.COMPASS)) {
                    ManhuntUtils.updateCompass(stack, fromServer(server, ManhuntUtils.speedrunner));
                }
            }
        });
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean dedicated) {
        SpeedrunnerCommand.register(commandDispatcher);
        HuntersCommand.register(commandDispatcher);
        ClearCacheCommand.register(commandDispatcher);
    }
}
