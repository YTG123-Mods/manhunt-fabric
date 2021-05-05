package io.github.ytg1234.manhunt.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mixin(MinecraftServer.class)
class MinecraftServerMixin implements io.github.ytg1234.manhunt.util.ManhuntServer {
    @Unique @NotNull  private List<UUID> manhuntBase_hunters;
    @Unique @Nullable private UUID manhuntBase_speedRunner;
    @Unique @NotNull  private List<PlayerEntity> manhuntBase_haveMod;

    @Deprecated MinecraftServerMixin() {
        manhuntBase_haveMod = new ArrayList<>();
        manhuntBase_hunters = new ArrayList<>();    // Stupid IDEA
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void OnConstructor(
            Thread thread,
            DynamicRegistryManager.Impl impl,
            LevelStorage.Session session,
            SaveProperties saveProperties,
            ResourcePackManager resourcePackManager,
            Proxy proxy,
            DataFixer dataFixer,
            ServerResourceManager serverResourceManager,
            MinecraftSessionService minecraftSessionService,
            GameProfileRepository gameProfileRepository,
            UserCache userCache,
            WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory,
            CallbackInfo ci
    ) {
        //hunters = CollectionsKt.mutableListOf();
        manhuntBase_hunters = new ArrayList<>();
        manhuntBase_haveMod = new ArrayList<>();
    }

    @Nullable @Override
    public UUID getSpeedRunner() {
        return manhuntBase_speedRunner;
    }

    @Override
    public void setSpeedRunner(@Nullable UUID speedRunner) {
        this.manhuntBase_speedRunner = speedRunner;
    }

    @NotNull @Override
    public List<UUID> getHunters() {
        return manhuntBase_hunters;
    }

    @NotNull @Override
    public List<PlayerEntity> getHaveMod() {
        return manhuntBase_haveMod;
    }
}
