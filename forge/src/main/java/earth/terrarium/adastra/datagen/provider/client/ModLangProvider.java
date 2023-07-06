package earth.terrarium.adastra.datagen.provider.client;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, AdAstra.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCKS.stream().forEach(entry ->
            addBlock(
                entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModItems.ITEMS.stream()
            .filter(i -> !(i.get() instanceof BlockItem))
            .forEach(entry ->
                addItem(
                    entry,
                    StringUtils.capitaliseAllWords(entry
                        .getId()
                        .getPath()
                        .replace("_", " "))));

        add(ConstantComponents.ITEM_GROUP.getString(), "Ad Astra");
        add(ConstantComponents.DEATH_OXYGEN.getString(), "%1$s couldn't breathe anymore");
        add(ConstantComponents.DEATH_OXYGEN_PLAYER.getString(), "%1$s lost their breath whilst trying to escape %2$s");

        add("text.adastra.oxygen", "%s");
        add("text.adastra.temperature", "%s °C");
        add("text.adastra.gravity", "%s m/s²");
    }
}
