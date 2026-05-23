package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagProvider extends TagsProvider<Fluid> {

    public ModFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.FLUID, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        add(ModFluidTags.OXYGEN, ModFluids.OXYGEN.get(), "oxygen");
        add(ModFluidTags.HYDROGEN, ModFluids.HYDROGEN.get(), "hydrogen");
        add(ModFluidTags.OIL, ModFluids.OIL.get(), "oil");
        add(ModFluidTags.FUEL, ModFluids.FUEL.get(), "fuel");
        add(ModFluidTags.FUEL, ModFluids.CRYO_FUEL.get());
        add(ModFluidTags.EFFICIENT_FUEL, ModFluids.CRYO_FUEL.get());

        add(ModFluidTags.TIER_1_ROCKET_FUEL, ModFluidTags.FUEL);
        add(ModFluidTags.TIER_2_ROCKET_FUEL, ModFluidTags.FUEL);
        add(ModFluidTags.TIER_3_ROCKET_FUEL, ModFluidTags.FUEL);
        add(ModFluidTags.TIER_4_ROCKET_FUEL, ModFluidTags.FUEL);
        add(ModFluidTags.TIER_1_ROVER_FUEL, ModFluidTags.FUEL);

        add(ModFluidTags.ZIP_GUN_PROPELLANTS, ModFluidTags.OXYGEN);
        add(ModFluidTags.ZIP_GUN_PROPELLANTS, ModFluidTags.HYDROGEN);

        add(ModFluidTags.FREEZES_IN_SPACE, Fluids.WATER);
        add(ModFluidTags.EVAPORATES_IN_SPACE, Fluids.WATER);

        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(ResourceLocation.fromNamespaceAndPath("c", "diesel")));
        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(ResourceLocation.fromNamespaceAndPath("c", "biodiesel")));

        tag(ModFluidTags.OIL).add(TagEntry.optionalElement(ResourceLocation.fromNamespaceAndPath("techreborn", "oil")));
        tag(ModFluidTags.OIL).add(TagEntry.optionalTag(ResourceLocation.fromNamespaceAndPath("c", "crude_oil")));
    }

    private void add(TagKey<Fluid> tag, Fluid fluid) {
        tag(tag).add(element(fluid));
    }

    private void add(TagKey<Fluid> tag, TagKey<Fluid> fluid) {
        tag(tag).addTag(fluid);
    }

    private void add(TagKey<Fluid> tag, Fluid fluid, String commonTag) {
        add(tag, fluid);
        addCommonTag(fluid, tag, commonTag);
    }

    private void addCommonTag(Fluid fluid, TagKey<Fluid> tag, String fabricCommonTag) {
        tag(tag).add(TagEntry.optionalTag(ResourceLocation.fromNamespaceAndPath("c", fabricCommonTag)));

        var commonTag = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", fabricCommonTag));
        tag(commonTag).add(element(fluid));
    }

    private static TagEntry element(Fluid fluid) {
        return TagEntry.element(loc(fluid));
    }

    private static ResourceLocation loc(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }
}
