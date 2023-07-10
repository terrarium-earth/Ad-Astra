package earth.terrarium.adastra.client.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

@Config("adastra-client")
public final class ClientConfig {

    @InlineCategory
    public static RadioConfig radio;
}
