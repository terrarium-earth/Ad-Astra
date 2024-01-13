package earth.terrarium.adastra.client.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@Category("Radio")
public final class RadioConfig {

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "volume",
        translation = "Volume"
    )
    @ConfigOption.Range(min = 0, max = 100)
    public static int volume = 50;

    @ConfigEntry(
        type = EntryType.STRING,
        id = "favorites",
        translation = "Favorites"
    )
    public static String[] favorites = new String[0];
}
