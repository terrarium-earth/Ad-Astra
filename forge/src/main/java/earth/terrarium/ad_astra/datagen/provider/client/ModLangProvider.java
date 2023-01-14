package earth.terrarium.ad_astra.datagen.provider.client;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.util.LangUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;

import java.util.Objects;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, AdAstra.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCKS.stream().filter(e -> !(e.get() instanceof WallSignBlock)).forEach(entry -> addBlock(entry, StringUtils.capitaliseAllWords(entry.getId().getPath().replace("_", " "))));
        ModItems.ITEMS.stream().filter(e -> !(e.get() instanceof BlockItem)).forEach(entry -> addItem(entry, StringUtils.capitaliseAllWords(Objects.requireNonNull(entry.getId()).getPath().replace("_", " "))));
        ModEntityTypes.ENTITY_TYPES.stream().forEach(entry -> addEntityType(entry, StringUtils.capitaliseAllWords(Objects.requireNonNull(entry.getId()).getPath().replace("_", " "))));

        LangUtils.LANG_MAP.forEach(this::add);

        add("subtitles.ad_astra.alien_watcher", "Alien Watching");
        add("subtitles.ad_astra.drone_fly_by", "Drone Flying By");
        add("subtitles.ad_astra.flying_saucer", "Flying Saucer");
        add("subtitles.ad_astra.imminent_doom", "Imminent Doom");
        add("subtitles.ad_astra.large_door_close", "Space Door Closing");
        add("subtitles.ad_astra.large_door_open", "Space Door Opening");
        add("subtitles.ad_astra.light_speed_travel", "Light Speed Spaceship");
        add("subtitles.ad_astra.passing_spaceship", "Passing Spaceship");
        add("subtitles.ad_astra.rocket_fly", "Rocket Flying");
        add("subtitles.ad_astra.small_door_open", "Glass Door Opening");
        add("subtitles.ad_astra.space_laser", "Space Laser");
        add("subtitles.ad_astra.wormhole", "Wormhole");
        add("subtitles.ad_astra.wrench", "Wrench Twisting");
    }

}
