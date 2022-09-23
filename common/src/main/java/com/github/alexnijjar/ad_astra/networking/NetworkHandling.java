package com.github.alexnijjar.ad_astra.networking;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;

public class NetworkHandling {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 0, "main");

    public static void register() {
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, LockBeePacket.ID, LockBeePacket.HANDLER, LockBeePacket.class);
    }
}
