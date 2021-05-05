package io.github.ytg1234.manhunt.mixin;

import com.mojang.authlib.GameProfile;
import io.github.ytg1234.manhunt.base.ManhuntUtilsKt;
import io.github.ytg1234.manhunt.config.Damage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow
    @Final
    public MinecraftServer server;

    ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource damageSource);

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        @Nullable Entity attacker = source.getAttacker();
        if (ManhuntUtilsKt.CONFIG.damageBehaviour.equals(Damage.DAMAGE) && !isInvulnerableTo(source) &&
            attacker instanceof PlayerEntity && ManhuntUtilsKt.isHunter(attacker)) {
            cir.setReturnValue(super.damage(source, Float.MAX_VALUE));
        }
    }
}
