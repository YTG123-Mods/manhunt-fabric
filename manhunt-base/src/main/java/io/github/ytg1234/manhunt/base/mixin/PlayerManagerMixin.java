package io.github.ytg1234.manhunt.base.mixin;

import io.github.ytg1234.manhunt.base.Manhunt;
import io.github.ytg1234.manhunt.base.ManhuntUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            at = @At("RETURN"))
    public void onPlayerLogin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        ServerPlayNetworking.send(player, ManhuntUtils.SERVER_QUESTION_PACKET_ID, PacketByteBufs.empty());
        Manhunt.log(Level.DEBUG, "Asked Server Question");
    }
}
