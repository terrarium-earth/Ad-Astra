package earth.terrarium.adastra.datagen.provider.client;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.codehaus.plexus.util.StringUtils;

import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, AdAstra.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCKS.stream().forEach(entry -> addBlock(entry,
            StringUtils.capitaliseAllWords(entry
                .getId()
                .getPath()
                .replace("_", " "))));

        ModItems.ITEMS.stream()
            .filter(i -> !(i.get() instanceof BlockItem))
            .forEach(entry -> addItem(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModEntityTypes.ENTITY_TYPES.stream()
            .forEach(entry -> addEntityType(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModFluids.FLUIDS.stream()
            .forEach(entry -> addFluid(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        add(ConstantComponents.ITEM_GROUP.getString(), "Ad Astra");
        add(ConstantComponents.DEATH_OXYGEN.getString(), "%1$s couldn't breathe anymore");
        add(ConstantComponents.DEATH_OXYGEN_PLAYER.getString(), "%1$s lost their breath whilst trying to escape %2$s");

        add(ConstantComponents.TOGGLE_SUIT_FLIGHT_KEY.getString(), "Toggle Suit Flight");
        add(ConstantComponents.AD_ASTRA_CATEGORY.getString(), "Ad Astra");

        add(ConstantComponents.SUIT_FLIGHT_ENABLED.getString(), "Suit Flight Enabled");
        add(ConstantComponents.SUIT_FLIGHT_DISABLED.getString(), "Suit Flight Disabled");

        add(ConstantComponents.OXYGEN_TRUE.getString(), "✔");
        add(ConstantComponents.OXYGEN_FALSE.getString(), "✘");
        add("text.adastra.temperature", "%s°C");
        add("text.adastra.gravity", "%sm/s²");
        add("text.adastra.radio.none", "No station");

        add("text.adastra.weather.temperature", "Now: %s°C");
        add("text.adastra.weather.temperature.in", "In: %s");

        add(ConstantComponents.TI_69_TOOLTIP_1.getString(), "Displays important information");
        add(ConstantComponents.TI_69_TOOLTIP_2.getString(), "Right-click to change the current app");

        add("tooltip.adastra.energy", "%s ⚡ / %s ⚡");
        add("tooltip.adastra.energy_in", "In: %s ⚡");
        add("tooltip.adastra.energy_out", "Out: %s ⚡");

        add("tooltip.adastra.max_energy_in", "Max In: %s ⚡");
        add("tooltip.adastra.max_energy_out", "Max Out: %s ⚡");

        add(ConstantComponents.SIDE_CONFIG.getString(), "Side Config");
        add(ConstantComponents.REDSTONE_CONTROL.getString(), "Redstone Control");

        add(ConstantComponents.REDSTONE_ALWAYS_ON.getString(), "Always On");
        add(ConstantComponents.REDSTONE_NEVER_ON.getString(), "Never On");
        add(ConstantComponents.REDSTONE_ON_WHEN_POWERED.getString(), "On When Powered");
        add(ConstantComponents.REDSTONE_ON_WHEN_NOT_POWERED.getString(), "On When Not Powered");
    }

    public void addFluid(Supplier<? extends Fluid> key, String name) {
        if (key.get() instanceof BotariumFlowingFluid) return;
        add("fluid_type.%s.%s".formatted(AdAstra.MOD_ID, ForgeRegistries.FLUIDS.getKey(key.get()).getPath()), name);
    }
}
