package io.github.ytg1234.manhunt.base;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.ytg1234.manhunt.api.event.callback.CompassUpdateCallback;
import io.github.ytg1234.manhunt.base.config.ManhuntConfig;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Various utilities for the manhunt mod.
 */
public final class ManhuntUtils {
    public static final String MOD_ID = "manhunt";
    public static final String MOD_NAME = "Manhunt Fabric";
    public static final List<UUID> hunters;
    public static final Identifier SERVER_QUESTION_PACKET_ID;
    public static final Identifier CLIENT_ANSWER_PACKET_ID;
    public static final List<PlayerEntity> haveMod;
    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static UUID speedrunner;
    public static ManhuntConfig CONFIG;
    public static boolean runActive;

    static {
        hunters = new ArrayList<>();
        speedrunner = null;
        haveMod = new ArrayList<>();
        runActive = false;
        SERVER_QUESTION_PACKET_ID = new Identifier(MOD_ID, "question");
        CLIENT_ANSWER_PACKET_ID = new Identifier(MOD_ID, "answer");
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
            LOGGER.warn("Compass target is null, cannot update compass! Please fix!");
            return compass.copy();
        }
        // Is dimension disabled?
        if (CONFIG.disabledDimensions.contains(target.getServerWorld().getRegistryKey().getValue().toString())) return compass.copy();

        // Continue Updating
        ItemStack oldCompass = compass.copy();
        ItemStack newCompass = compass.copy();
        CompoundTag itemTag = newCompass.getOrCreateTag().copy();
        itemTag.putBoolean("LodestoneTracked", false);
        itemTag.putString("LodestoneDimension", target.getServerWorld().getRegistryKey().getValue().toString());
        CompoundTag lodestonePos = new CompoundTag();
        lodestonePos.putInt("X", (int) target.getX());
        lodestonePos.putInt("Y", (int) target.getY());
        lodestonePos.putInt("Z", (int) target.getZ());
        itemTag.put("LodestonePos", lodestonePos);
        newCompass.setTag(itemTag);
        newCompass = CompassUpdateCallback.EVENT.invoker().onCompassUpdate(oldCompass, newCompass);
        return newCompass;
    }

    public static boolean applyStatusEffectToPlayer(PlayerEntity player, StatusEffect effect) {
        return player.addStatusEffect(new StatusEffectInstance(effect, 2, 0, false, false));
    }
}
