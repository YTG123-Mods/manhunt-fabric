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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (target == null) {
            Manhunt.log(Level.WARN, "Compass target is null, cannot update compass! Please fix!");
            return compass;
        }
        CompoundTag itemTag = compass.getTag() == null ? new CompoundTag() : compass.getTag().copy();
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

    //    public static boolean isClientLookingAtPlayer(MinecraftClient client, PlayerEntity lookedAt) {
    //        float tickDelta = 0f;
    //        int width = client.getWindow().getScaledWidth();
    //        int height = client.getWindow().getScaledHeight();
    //        Vec3d cameraDirection = client.cameraEntity.getRotationVec(tickDelta);
    //        double fov = client.options.fov;
    //        double angleSize = fov / height;
    //        Vector3f verticalRotationAxis = new Vector3f(cameraDirection);
    //        verticalRotationAxis.cross(Vector3f.POSITIVE_Y);
    //
    //        // Check if the camera is pointing directly up or down.
    //        if (!verticalRotationAxis.normalize()) {
    //            return false;
    //        }
    //
    //        Vector3f horizontalRotationAxis = new Vector3f(cameraDirection);
    //        horizontalRotationAxis.cross(verticalRotationAxis);
    //        horizontalRotationAxis.normalize();
    //
    //        verticalRotationAxis = new Vector3f(cameraDirection);
    //        verticalRotationAxis.cross(horizontalRotationAxis);
    //
    //        Vec3d direction = map((float) angleSize, cameraDirection, horizontalRotationAxis, verticalRotationAxis, width / 2, width / 2, width, height);
    //        HitResult hit = raycastInDirection(client, tickDelta, direction);
    //
    //        switch (Objects.requireNonNull(hit).getType()) {
    //            case MISS:
    //                // nothing near enough
    //                break;
    //            case BLOCK:
    //                break;
    //            case ENTITY:
    //                EntityHitResult entityHit = (EntityHitResult) hit;
    //                Entity entity = entityHit.getEntity();
    //                if (lookedAt.equals(entity)) {
    //                    return true;
    //                }
    //                break;
    //        }
    //
    //        return false;
    //    }
    //
    //    private static Vec3d map(
    //            float anglePerPixel, Vec3d center, Vector3f horizontalRotationAxis, Vector3f verticalRotationAxis, int x, int y, int width, int height
    //                            ) {
    //        float horizontalRotation = (x - width / 2f) * anglePerPixel;
    //        float verticalRotation = (y - height / 2f) * anglePerPixel;
    //
    //        final Vector3f temp2 = new Vector3f(center);
    //        temp2.rotate(verticalRotationAxis.getDegreesQuaternion(verticalRotation));
    //        temp2.rotate(horizontalRotationAxis.getDegreesQuaternion(horizontalRotation));
    //        return new Vec3d(temp2);
    //    }
    //
    //    private static HitResult raycastInDirection(MinecraftClient client, float tickDelta, Vec3d direction) {
    //        Entity entity = client.getCameraEntity();
    //        if (entity == null || client.world == null) {
    //            return null;
    //        }
    //
    //        double reachDistance = 30; // Change this to extend the reach
    //        HitResult target = raycast(entity, reachDistance, tickDelta, false, direction);
    //        double extendedReach = reachDistance;
    //
    //        Vec3d cameraPos = entity.getCameraPosVec(tickDelta);
    //
    //        extendedReach = extendedReach * extendedReach;
    //        if (target != null) {
    //            extendedReach = target.getPos().squaredDistanceTo(cameraPos);
    //        }
    //
    //        Vec3d vec3d3 = cameraPos.add(direction.multiply(reachDistance));
    //        Box box = entity.getBoundingBox().stretch(entity.getRotationVec(1.0F).multiply(reachDistance)).expand(1.0D, 1.0D, 1.0D);
    //        EntityHitResult
    //                entityHitResult =
    //                ProjectileUtil.raycast(entity, cameraPos, vec3d3, box, (entityx) -> !entityx.isSpectator() && entityx.collides(), extendedReach);
    //
    //        if (entityHitResult == null) {
    //            return target;
    //        }
    //
    //        Entity entity2 = entityHitResult.getEntity();
    //        Vec3d vec3d4 = entityHitResult.getPos();
    //        double g = cameraPos.squaredDistanceTo(vec3d4);
    //        if (g < extendedReach || target == null) {
    //            target = entityHitResult;
    //            if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
    //                client.targetedEntity = entity2;
    //            }
    //        }
    //
    //        return target;
    //    }
    //
    //    private static HitResult raycast(
    //            Entity entity, double maxDistance, float tickDelta, boolean includeFluids, Vec3d direction
    //                                    ) {
    //        Vec3d end = entity.getCameraPosVec(tickDelta).add(direction.multiply(maxDistance));
    //        return entity.world.raycast(new RaycastContext(
    //                entity.getCameraPosVec(tickDelta),
    //                end,
    //                RaycastContext.ShapeType.OUTLINE,
    //                includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
    //                entity
    //        ));
    //    }
}
