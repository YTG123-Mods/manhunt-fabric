package com.ytg123.manhunt.mixin;

import com.ytg123.manhunt.Manhunt;
import com.ytg123.manhunt.ManhuntUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.ytg123.manhunt.config.Behaviours.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource damageSource);

    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "TAIL"), cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient()) {
            if (source.getAttacker() != null) {
                if (source.getAttacker() instanceof PlayerEntity && ManhuntUtils.hunters.contains(source.getAttacker().getUuid()) && Manhunt.CONFIG.damageBehaviour.equals(Damage.DAMAGE) && !isInvulnerableTo(source)) {
                    cir.setReturnValue(super.damage(source, Float.MAX_VALUE));
                }
            }
        }
    }
}
