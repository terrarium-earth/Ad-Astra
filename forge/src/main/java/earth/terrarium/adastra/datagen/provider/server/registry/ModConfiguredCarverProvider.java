package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModWorldCarvers;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class ModConfiguredCarverProvider {
    public static final ResourceKey<ConfiguredWorldCarver<?>> MOON_CRATER = register("moon_crater");

    private static ResourceKey<ConfiguredWorldCarver<?>> register(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
        context.register(MOON_CRATER, new ConfiguredWorldCarver<>(
            ModWorldCarvers.CRATER.get(),
            new CarverConfiguration(
                1,
                UniformHeight.of(VerticalAnchor.belowTop(32), VerticalAnchor.belowTop(0)),
                ConstantFloat.of(1),
                VerticalAnchor.aboveBottom(-2032),
                CarverDebugSettings.DEFAULT,
                BuiltInRegistries.BLOCK.getOrCreateTag(ModBlockTags.MOON_CARVER_REPLACEABLES)
            )));
    }
}
