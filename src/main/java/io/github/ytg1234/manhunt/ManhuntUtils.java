package io.github.ytg1234.manhunt;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Various utilities for the manhunt mod.
 */
public final class ManhuntUtils {
    public static UUID speedrunner;
    public static List<UUID> hunters;

    public static List<PlayerEntity> haveMod;

    static {
        hunters = new ArrayList<>();
        speedrunner = null;
        haveMod = new ArrayList<>();
    }

    private ManhuntUtils() {
    }

    public static boolean playerHasMod(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        return context.getSource().getEntity() != null &&
               context.getSource().getEntity() instanceof PlayerEntity &&
               ManhuntUtils.haveMod.contains(context.getSource().getPlayer());
    }

    public static ServerPlayerEntity fromCmdContext(CommandContext<ServerCommandSource> ctx, UUID uuid) {
        return ctx.getSource().getMinecraftServer().getPlayerManager().getPlayer(uuid);
    }

    public static ServerPlayerEntity fromServer(MinecraftServer server, UUID uuid) {
        return server.getPlayerManager().getPlayer(uuid);
    }

    public static ItemStack updateCompass(ItemStack compass, ServerPlayerEntity target) {
        // Is target null?
        if (target == null) {
            Manhunt.log(Level.WARN, "Compass target is null, cannot update compass! Please fix!");
            return compass;
        }
        // Is dimension disabled?
        if (Manhunt.CONFIG.disabledDimensions.contains(target.getServerWorld().getRegistryKey().getValue().toString())) return compass;

        // Continue Updating
        CompoundTag itemTag = compass.getOrCreateTag().copy();
        itemTag.putBoolean("LodestoneTracked", false);
        itemTag.putString("LodestoneDimension", target.getServerWorld().getRegistryKey().getValue().toString());
        CompoundTag lodestonePos = new CompoundTag();
        lodestonePos.putInt("X", (int) target.getX());
        lodestonePos.putInt("Y", (int) target.getY());
        lodestonePos.putInt("Z", (int) target.getZ());
        itemTag.put("LodestonePos", lodestonePos);
        compass.setTag(itemTag);
        return compass;
    }
}
