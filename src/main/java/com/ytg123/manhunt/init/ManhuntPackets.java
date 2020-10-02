package com.ytg123.manhunt.init;

import com.ytg123.manhunt.Manhunt;
import com.ytg123.manhunt.ManhuntUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class ManhuntPackets {
    private ManhuntPackets() {
    }

    public static final Identifier SERVER_QUESTION_PACKET_ID = new Identifier(Manhunt.MOD_ID, "question");
    public static final Identifier CLIENT_ANSWER_PACKET_ID = new Identifier(Manhunt.MOD_ID, "answer");

    public static void registerPacketsClient() {
        ClientSidePacketRegistry.INSTANCE.register(ManhuntPackets.SERVER_QUESTION_PACKET_ID,  (context, data) -> {
            context.getTaskQueue().execute(() -> {
                ClientSidePacketRegistry.INSTANCE.sendToServer(CLIENT_ANSWER_PACKET_ID, new PacketByteBuf(Unpooled.buffer()));
            });
        });
    }

    public static void registerPacketsServer() {
        ServerSidePacketRegistry.INSTANCE.register(CLIENT_ANSWER_PACKET_ID, (context, data) -> {
            ManhuntUtils.haveMod.add(context.getPlayer());
        });
    }
}
