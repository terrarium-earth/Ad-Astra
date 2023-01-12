package earth.terrarium.ad_astra.common.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import earth.terrarium.ad_astra.AdAstra;

public class NetworkHandling {
    public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstra.MOD_ID, 0, "main");

    public static void init() {
    }
}
