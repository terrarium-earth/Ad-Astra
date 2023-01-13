package earth.terrarium.ad_astra.datagen.provider.client;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModItems;
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
        add("itemGroup.ad_astra.main", "Ad Astra");

        ModBlocks.BLOCKS.stream().filter(e -> !(e.get() instanceof WallSignBlock)).forEach(entry -> addBlock(entry, StringUtils.capitaliseAllWords(entry.getId().getPath().replace("_", " "))));
        ModItems.ITEMS.stream().filter(e -> !(e.get() instanceof BlockItem)).forEach(entry -> addItem(entry, StringUtils.capitaliseAllWords(Objects.requireNonNull(entry.getId()).getPath().replace("_", " "))));
        ModEntityTypes.ENTITY_TYPES.stream().forEach(entry -> addEntityType(entry, StringUtils.capitaliseAllWords(Objects.requireNonNull(entry.getId()).getPath().replace("_", " "))));

        add("tooltip.ad_astra.empty_tank", "Empty Tank");
        add("tooltip.ad_astra.energy_bar", "%s ⚡ /§a %s ⚡");
        add("tooltip.ad_astra.fluid_tank", "%s 🪣 / %s 🪣 %s");
    }
}
