package earth.terrarium.adastra.client.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.IntRange;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "radio", translation = "Radio")
public final class RadioConfig {

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "volume",
        translation = "Volume"
    )
    @IntRange(min = 0, max = 100)
    public static int volume = 50;

    @ConfigEntry(
        type = EntryType.STRING,
        id = "favorites"
    )
    @ConfigOption.Hidden
    public static String[] favorites = new String[0];
}
