package earth.terrarium.adastra.datagen.provider.server.tags;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.tags.ModEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("DataFlowIssue")
public class ModEntityTypeTagProvider extends TagsProvider<EntityType<?>> {

    public ModEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.ENTITY_TYPE, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        surviveSpace(EntityType.ARMOR_STAND);
        surviveSpace(EntityType.IRON_GOLEM);
        surviveSpace(EntityType.WARDEN);
        surviveSpace(EntityType.ENDER_DRAGON);
        surviveSpace(EntityType.WITHER);
        surviveSpace(EntityType.ALLAY);

        tag(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ZOMBIE)));
        tag(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.HUSK)));
        tag(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN).add(TagEntry.tag(EntityTypeTags.SKELETONS.location()));

        tag(ModEntityTypeTags.CAN_SURVIVE_EXTREME_COLD).add(TagEntry.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES.location()));

        tag(ModEntityTypeTags.CAN_SURVIVE_ACID_RAIN).add(TagEntry.tag(ModEntityTypeTags.CAN_SURVIVE_EXTREME_HEAT.location()));

        tag(ModEntityTypeTags.IGNORES_AIR_VORTEX).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.LEASH_KNOT)));

        acidRainImmune(ModEntityTypes.TIER_1_ROCKET.get());
        acidRainImmune(ModEntityTypes.TIER_2_ROCKET.get());
        acidRainImmune(ModEntityTypes.TIER_3_ROCKET.get());
        acidRainImmune(ModEntityTypes.TIER_4_ROCKET.get());
        acidRainImmune(ModEntityTypes.LANDER.get());

        surviveCold(EntityType.SNOW_GOLEM);
        surviveCold(EntityType.STRAY);
        surviveCold(ModEntityTypes.CORRUPTED_LUNARIAN.get());
        surviveCold(ModEntityTypes.STAR_CRAWLER.get());
        surviveCold(ModEntityTypes.MARTIAN_RAPTOR.get());
        surviveCold(ModEntityTypes.LUNARIAN.get());

        surviveSpace(ModEntityTypes.LUNARIAN_WANDERING_TRADER.get());
        surviveSpace(ModEntityTypes.PYGRO.get());
        surviveSpace(ModEntityTypes.PYGRO_BRUTE.get());
        surviveSpace(ModEntityTypes.ZOMBIFIED_PYGRO.get());
        surviveSpace(ModEntityTypes.MOGLER.get());
        surviveSpace(ModEntityTypes.ZOMBIFIED_MOGLER.get());
        surviveSpace(ModEntityTypes.SULFUR_CREEPER.get());

        surviveHeat(EntityType.BLAZE);
        surviveHeat(EntityType.MAGMA_CUBE);
        surviveHeat(EntityType.ZOMBIFIED_PIGLIN);
        surviveHeat(EntityType.ZOGLIN);
    }

    private void surviveHeat(EntityType<?> type) {
        tag(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
        tag(ModEntityTypeTags.CAN_SURVIVE_EXTREME_HEAT).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
    }

    private void surviveCold(EntityType<?> type) {
        tag(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
        tag(ModEntityTypeTags.CAN_SURVIVE_EXTREME_COLD).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
    }

    private void surviveSpace(EntityType<?> type) {
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
    }

    private void acidRainImmune(EntityType<?> type) {
        tag(ModEntityTypeTags.CAN_SURVIVE_ACID_RAIN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
    }
}
