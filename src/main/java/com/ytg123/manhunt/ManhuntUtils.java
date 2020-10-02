package com.ytg123.manhunt;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Various utilities for the manhunt mod.
 */
public final class ManhuntUtils {
//    public static final Map<ServerPlayerEntity, ServerPlayerEntity> compassTracking = new HashMap<>();

//    /**
//     * Checks if a player is tracking another player.
//     * @param tracking The player who might be tracking
//     * @param tracked The player who might be tracked
//     * @return The tracking player is tracking the tracked player.
//     */
//    public static boolean isPlayerTrackingPlayer(@NotNull ServerPlayerEntity tracking, @NotNull ServerPlayerEntity tracked) {
//        return compassTracking.containsKey(tracking) && compassTracking.get(tracking).equals(tracked);
//    }
//
//    /**
//     * Returns the player tracked by another player.
//     * @param tracking Player who's tracking
//     * @return The tracking player's tracked player, null if not present.
//     */
//    @Nullable
//    public static ServerPlayerEntity getTracked(@NotNull ServerPlayerEntity tracking) {
//        return compassTracking.getOrDefault(tracking, null);
//    }

    public static ServerPlayerEntity speedrunner;
    public static List<ServerPlayerEntity> hunters;

    public static List<PlayerEntity> haveMod = new ArrayList<>();

    static {
        hunters = new ArrayList<>();
        speedrunner = null;
    }

    private ManhuntUtils() {}

    public static boolean playerHasMod(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        return context.getSource().getEntity() != null && context.getSource().getEntity() instanceof PlayerEntity && ManhuntUtils.haveMod.contains(context.getSource().getPlayer());
    }
}
