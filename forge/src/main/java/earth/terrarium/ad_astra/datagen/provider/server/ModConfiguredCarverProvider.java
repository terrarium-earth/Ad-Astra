package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.registry.ModWorldCarvers;
import earth.terrarium.ad_astra.datagen.AdAstraDataGenerator;
import earth.terrarium.ad_astra.datagen.provider.base.ModCodecProvider;
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
import java.util.function.BiConsumer;

public class ModConfiguredCarverProvider extends ModCodecProvider<ConfiguredWorldCarver<?>> {
    public ModConfiguredCarverProvider(PackOutput packOutput) {
        super(packOutput, ConfiguredWorldCarver.DIRECT_CODEC, Registries.CONFIGURED_CARVER);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, ConfiguredWorldCarver<?>> consumer) {
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

        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "moon_crater"), carver);
    }

    @Override
    public String getName() {
        return "Configured Carvers";
    }
}
