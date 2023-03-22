package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.registry.ModWorldCarvers;
import earth.terrarium.ad_astra.datagen.AdAstraDataGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

import java.util.concurrent.CompletableFuture;

public class ModConfiguredCarverProvider implements DataProvider {
    private final PackOutput packOutput;

    public ModConfiguredCarverProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        @SuppressWarnings("deprecation")
        var carver = new ConfiguredWorldCarver<>(
                ModWorldCarvers.CRATER.get(),
                new CarverConfiguration(
                        1,
                        UniformHeight.of(VerticalAnchor.belowTop(32), VerticalAnchor.belowTop(0)),
                        ConstantFloat.of(1),
                        VerticalAnchor.aboveBottom(-2032),
                        CarverDebugSettings.DEFAULT,
                        BuiltInRegistries.BLOCK.getOrCreateTag(ModTags.Blocks.MOON_CARVER_REPLACEABLES)
                )
        );

        return AdAstraDataGenerator.saveCodecValue(
                output,
                new ResourceLocation(AdAstra.MOD_ID, "moon_crater"),
                carver,
                ConfiguredWorldCarver.DIRECT_CODEC,
                Registries.CONFIGURED_CARVER,
                packOutput
        );
    }

    @Override
    public String getName() {
        return "Configured Carvers";
    }
}
