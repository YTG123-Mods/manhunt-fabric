package com.ytg123.manhunt;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    public static UUID speedrunner;
    public static List<UUID> hunters;

    public static List<PlayerEntity> haveMod;

    static {
        hunters = new ArrayList<>();
        speedrunner = null;
        haveMod = new ArrayList<>();
    }

    private ManhuntUtils() {}

    public static boolean playerHasMod(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        return context.getSource().getEntity() != null && context.getSource().getEntity() instanceof PlayerEntity && ManhuntUtils.haveMod.contains(context.getSource().getPlayer());
    }

    public static ServerPlayerEntity fromCmdContext(CommandContext<ServerCommandSource> ctx, UUID uuid) {
        return ctx.getSource().getMinecraftServer().getPlayerManager().getPlayer(uuid);
    }

    public static ServerPlayerEntity fromServer(MinecraftServer server, UUID uuid) {
        return server.getPlayerManager().getPlayer(uuid);
    }

    public static ItemStack updateCompass(ItemStack compass, ServerPlayerEntity target) {
        if(target == null) {
            Manhunt.log(Level.WARN, "Compass target is null, cannot update compass! Please fix!");
            return compass;
        }
        CompoundTag itemTag = compass.getTag() == null ? new CompoundTag() : compass.getTag().copy();
        itemTag.putBoolean("LodestoneTracked", false);
        itemTag.putString(
                "LodestoneDimension",
                target.getServerWorld().getRegistryKey().getValue().toString()
                         );
        CompoundTag lodestonePos = new CompoundTag();
        lodestonePos.putInt("X", (int) target.getX());
        lodestonePos.putInt("Y", (int) target.getY());
        lodestonePos.putInt("Z", (int) target.getZ());
        itemTag.put("LodestonePos", lodestonePos);
        compass.setTag(itemTag);
        return compass;
    }
}
