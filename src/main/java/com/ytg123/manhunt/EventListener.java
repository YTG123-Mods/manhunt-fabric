package com.ytg123.manhunt;

import com.mojang.brigadier.CommandDispatcher;
import com.ytg123.manhunt.command.HuntersCommand;
import com.ytg123.manhunt.command.SpeedrunnerCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.EndTick;

public final class EventListener implements EndTick, CommandRegistrationCallback {
    public static final EventListener INSTANCE;

    static {
        INSTANCE = new EventListener();
    }

    private EventListener() {
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
//        Utils.compassTracking.forEach((key, value) -> {
//            for(int i = 0; i < key.inventory.size(); i++) {
//                ItemStack stack = key.inventory.getStack(i);
//                if (stack.getItem().equals(Items.COMPASS)) {
//                    CompoundTag itemTag = stack.getTag().copy();
//                    itemTag.putBoolean("LodestoneTracked", false);
//                    itemTag.putString("LodestoneDimension", value.getServerWorld().getRegistryKey().getValue().toString());
//                    CompoundTag lodestonePos = new CompoundTag();
//                    lodestonePos.putInt("X", (int) value.getX());
//                    lodestonePos.putInt("Y", (int) value.getY());
//                    lodestonePos.putInt("Z", (int) value.getZ());
//                    itemTag.put("LodestonePos", lodestonePos);
//                }
//            }
//        });
        Utils.hunters.forEach(hunter -> {
            for (int i = 0; i < hunter.inventory.size(); i++) {
                if (hunter.inventory.getStack(i) == null || Utils.speedrunner == null) continue;
                ItemStack stack = hunter.inventory.getStack(i);
                if (stack.getItem().equals(Items.COMPASS)) {
                    CompoundTag itemTag = stack.getTag() == null ? new CompoundTag() : stack.getTag().copy();
                    itemTag.putBoolean("LodestoneTracked", false);
                    itemTag.putString("LodestoneDimension", Utils.speedrunner.getServerWorld().getRegistryKey().getValue().toString());
                    CompoundTag lodestonePos = new CompoundTag();
                    lodestonePos.putInt("X", (int) Utils.speedrunner.getX());
                    lodestonePos.putInt("Y", (int) Utils.speedrunner.getY());
                    lodestonePos.putInt("Z", (int) Utils.speedrunner.getZ());
                    itemTag.put("LodestonePos", lodestonePos);
                    stack.setTag(itemTag);
                }
            }
        });
    }

    @Override
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean b) {
        SpeedrunnerCommand.register(commandDispatcher);
        HuntersCommand.register(commandDispatcher);
    }
}
