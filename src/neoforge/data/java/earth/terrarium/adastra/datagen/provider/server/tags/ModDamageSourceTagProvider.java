package earth.terrarium.adastra.datagen.provider.server.tags;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModDamageSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModDamageSourceTagProvider extends TagsProvider<DamageType> {

    public ModDamageSourceTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).addOptional(ModDamageSources.OXYGEN.location());
        tag(DamageTypeTags.NO_IMPACT).addOptional(ModDamageSources.OXYGEN.location());
    }
}
