package net.fabricmc.example.networking;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.example.NTHudMod;
import net.fabricmc.example.models.renderers.ModelData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

public class Networking {

    /*
    * cd;<long cd><resourcepack/icon> -> DisplayCooldownPacket
    */
    public static final String PROTOCOL_VERSION = "nth|1";

    public static final Identifier CHANNEL_NTRGUI = new Identifier("nth:gui");

    public static void initialize()  {

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            System.out.println("Joined game, testing handshake");
            sendHello();
        });

        ClientPlayNetworking.registerGlobalReceiver(CHANNEL_NTRGUI, (client, handler, buf, responseSender) -> {
            try {
                int readableBytes = buf.readableBytes();
                if (readableBytes > 0) {
                    String stringPayload = buf.toString(0, buf.readableBytes(), StandardCharsets.UTF_8);
                    client.execute(() -> processChannelMessage(stringPayload));
                } else {
                    NTHudMod.log("Zero length payload");
                }
            } catch (Exception ex) {
                NTHudMod.err("Unable to read payload", ex);
            }
        });

    }

    private static void sendHello() {
        ByteBuf buffer = Unpooled.copiedBuffer(PROTOCOL_VERSION, StandardCharsets.UTF_8);
        ClientPlayNetworking.send(CHANNEL_NTRGUI, new PacketByteBuf(buffer));
    }

    public static void processChannelMessage(String message) {
        try {
            String[] split = message.split(";");
            String type = split[0];
            HudPacketType hudPacketType = HudPacketType.valueOf(type.toUpperCase());
            switch (hudPacketType) {
                case CD:
                    NTHudMod.getInstance().getCooldowns().put(new Identifier(split[2]), Long.parseLong(split[1]));
                    NTHudMod.log("Received HUD packet " + message);
                    break;
                case SET_MODEL:
                    NTHudMod.getInstance().getCustomModels().put(split[1], new ModelData(split[2], split[3], split[4]));
                    NTHudMod.log("Received set model packet " + message);
                    break;
                case ADD_MODEL:
                case PLAY_ANIMATION:
                default:
                    NTHudMod.err("Unknown message " + message);
            }
        } catch (Exception e) {
            NTHudMod.err("Unable to parse payload " + message, e);
        }
    }
}
