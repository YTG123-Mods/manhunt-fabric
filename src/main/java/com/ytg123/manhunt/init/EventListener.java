package com.ytg123.manhunt.init;

import com.mojang.brigadier.CommandDispatcher;
import com.ytg123.manhunt.Manhunt;
import com.ytg123.manhunt.ManhuntUtils;
import com.ytg123.manhunt.command.ClearCacheCommand;
import com.ytg123.manhunt.command.HuntersCommand;
import com.ytg123.manhunt.command.SpeedrunnerCommand;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import static com.ytg123.manhunt.ManhuntUtils.fromServer;
import static com.ytg123.manhunt.config.Behaviours.Compass;

public final class EventListener {
    private EventListener() {
    }

    public static void onEndTick(MinecraftServer server) {
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
        ManhuntUtils.hunters.forEach(hunterUuid -> {
            // Check if player is null
            if (fromServer(server, hunterUuid) == null) {
                return;
            }

            // Continue with logic
            ItemStack stack = fromServer(server, hunterUuid).inventory.getStack(8);

            // If the stack is empty, null or not a compass put a compass there
            if (stack == null || stack.isEmpty() || !stack.getItem().equals(Items.COMPASS)) {
                if (Manhunt.CONFIG.giveCompassWhenSettingHunters) {
                    fromServer(server, hunterUuid).equip(8, new ItemStack(Items.COMPASS));
                    stack = fromServer(server, hunterUuid).inventory.getStack(8);
                } else if (stack == null) return;
            }
            if (Manhunt.CONFIG.compassBehaviour.equals(Compass.UPDATE)) {
                // Set compass NBT
                if (stack.getItem().equals(Items.COMPASS)) {
                    ManhuntUtils.updateCompass(stack, fromServer(server, ManhuntUtils.speedrunner));
                    //                    CompoundTag itemTag = stack.getTag() == null ? new CompoundTag() : stack.getTag().copy();
                    //                    itemTag.putBoolean("LodestoneTracked", false);
                    //                    itemTag.putString(
                    //                            "LodestoneDimension",
                    //                            fromServer(server, ManhuntUtils.speedrunner).getServerWorld().getRegistryKey().getValue().toString()
                    //                                     );
                    //                    CompoundTag lodestonePos = new CompoundTag();
                    //                    lodestonePos.putInt("X", (int) fromServer(server, ManhuntUtils.speedrunner).getX());
                    //                    lodestonePos.putInt("Y", (int) fromServer(server, ManhuntUtils.speedrunner).getY());
                    //                    lodestonePos.putInt("Z", (int) fromServer(server, ManhuntUtils.speedrunner).getZ());
                    //                    itemTag.put("LodestonePos", lodestonePos);
                    //                    stack.setTag(itemTag);
                }

                // Old code
                //                for (int i = 0; i < fromServer(server, hunterUuid).inventory.size(); i++) {
                //                    if (fromServer(server, hunterUuid).inventory.getStack(i) == null || ManhuntUtils.speedrunner == null) continue;
                //                    ItemStack stack = fromServer(server, hunterUuid).inventory.getStack(i);
                //                    if (stack.getItem().equals(Items.COMPASS)) {
                //                        CompoundTag itemTag = stack.getTag() == null ? new CompoundTag() : stack.getTag().copy();
                //                        itemTag.putBoolean("LodestoneTracked", false);
                //                        itemTag.putString(
                //                                "LodestoneDimension",
                //                                fromServer(server, ManhuntUtils.speedrunner).getServerWorld().getRegistryKey().getValue().toString()
                //                                         );
                //                        CompoundTag lodestonePos = new CompoundTag();
                //                        lodestonePos.putInt("X", (int) fromServer(server, ManhuntUtils.speedrunner).getX());
                //                        lodestonePos.putInt("Y", (int) fromServer(server, ManhuntUtils.speedrunner).getY());
                //                        lodestonePos.putInt("Z", (int) fromServer(server, ManhuntUtils.speedrunner).getZ());
                //                        itemTag.put("LodestonePos", lodestonePos);
                //                        stack.setTag(itemTag);
                //                    }
                //                }
            }
        });
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean b) {
        SpeedrunnerCommand.register(commandDispatcher);
        HuntersCommand.register(commandDispatcher);
        ClearCacheCommand.register(commandDispatcher);
    }
}
