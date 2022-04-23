package net.mrscauthd.beyond_earth.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.SoundUtil;

public class ModS2CPackets {

    public static final Identifier PORTAL_SOUND_PACKET_ID = new ModIdentifier("portal_sound_packet");

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(PORTAL_SOUND_PACKET_ID,
                (client, handler, buf, responseSender) -> {
                    SoundUtil.setSound(buf.readBoolean());
                });
    }
}