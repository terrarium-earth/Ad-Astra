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
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(ModEntityTypes.SPIDER_BOT.get())));

        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ARMOR_STAND)));
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.IRON_GOLEM)));
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WARDEN)));
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ENDER_DRAGON)));
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WITHER)));
        tag(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ALLAY)));

        tag(ModEntityTypeTags.CAN_SURVIVE_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ZOMBIE)));
        tag(ModEntityTypeTags.CAN_SURVIVE_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.HUSK)));
        tag(ModEntityTypeTags.CAN_SURVIVE_WITHOUT_OXYGEN).add(TagEntry.tag(EntityTypeTags.SKELETONS.location()));

        surviveCold(EntityType.SNOW_GOLEM);
        surviveCold(EntityType.STRAY);

        surviveHeat(EntityType.BLAZE);
        surviveHeat(EntityType.MAGMA_CUBE);
        surviveHeat(EntityType.ZOMBIFIED_PIGLIN);
        surviveHeat(EntityType.ZOGLIN);
    }

    private void surviveHeat(EntityType<?> type) {
        tag(ModEntityTypeTags.CAN_SURVIVE_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
        tag(ModEntityTypeTags.CAN_SURVIVE_EXTREME_HEAT).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
    }

    private void surviveCold(EntityType<?> type) {
        tag(ModEntityTypeTags.CAN_SURVIVE_WITHOUT_OXYGEN).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
        tag(ModEntityTypeTags.CAN_SURVIVE_EXTREME_COLD).add(TagEntry.element(ForgeRegistries.ENTITY_TYPES.getKey(type)));
    }
}
