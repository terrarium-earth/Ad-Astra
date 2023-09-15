package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagProvider extends TagsProvider<Fluid> {

    public ModFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.FLUID, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModFluidTags.OXYGEN).add(TagEntry.element(ForgeRegistries.FLUIDS.getKey(ModFluids.OXYGEN.get())));
        tag(ModFluidTags.HYDROGEN).add(TagEntry.element(ForgeRegistries.FLUIDS.getKey(ModFluids.HYDROGEN.get())));
        tag(ModFluidTags.OIL).add(TagEntry.element(ForgeRegistries.FLUIDS.getKey(ModFluids.OIL.get())));
        tag(ModFluidTags.FUEL).add(TagEntry.element(ForgeRegistries.FLUIDS.getKey(ModFluids.FUEL.get())));

        tag(ModFluidTags.ZIP_GUN_PROPELLANTS).addTag(ModFluidTags.OXYGEN).addTag(ModFluidTags.HYDROGEN);
    }
}
