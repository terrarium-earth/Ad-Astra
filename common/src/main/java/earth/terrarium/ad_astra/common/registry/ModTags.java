package earth.terrarium.ad_astra.common.registry;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {
    public static final TagKey<EntityType<?>> FIRE_IMMUNE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "entities/fire_immune"));
    public static final TagKey<EntityType<?>> LIVES_WITHOUT_OXYGEN = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "entities/lives_without_oxygen"));
    public static final TagKey<Item> FREEZE_RESISTANT = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "freeze_resistant"));
    public static final TagKey<Item> HEAT_RESISTANT = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "heat_resistant"));
    public static final TagKey<Item> OXYGENATED_ARMOR = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "oxygenated_armor"));
    public static final TagKey<Item> GLACIAN_LOGS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "glacian_logs"));
    public static final TagKey<Item> AERONOS_CAPS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "aeronos_caps"));
    public static final TagKey<Item> STROPHAR_CAPS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "strophar_caps"));
    public static final TagKey<Block> BURNS_OUT = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "burns_out"));

    public static final TagKey<Fluid> FUELS = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(getCommonNamespace() + ":fuel"));
    public static final TagKey<Fluid> EFFICIENT_FUELS = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "efficient_fuel"));
    public static final TagKey<Fluid> OXYGEN = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(getCommonNamespace() + ":oxygen"));
    public static final TagKey<Fluid> OIL = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(getCommonNamespace() + ":oil"));

    public static final TagKey<Item> CALORITE_INGOTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":calorite_ingots"));
    public static final TagKey<Item> CALORITE_NUGGETS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":calorite_nuggets"));
    public static final TagKey<Item> CHEESES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":cheeses"));
    public static final TagKey<Item> CALORITE_PLATES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":calorite_plates"));
    public static final TagKey<Item> DESH_PLATES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":desh_plates"));
    public static final TagKey<Item> OSTRUM_PLATES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":ostrum_plates"));
    public static final TagKey<Item> STEEL_PLATES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":steel_plates"));
    public static final TagKey<Item> DESH_INGOTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":desh_ingots"));
    public static final TagKey<Item> DESH_NUGGETS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":desh_nuggets"));
    public static final TagKey<Item> IRON_PLATES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":iron_plates"));
    public static final TagKey<Item> IRON_RODS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":iron_rods"));
    public static final TagKey<Item> OSTRUM_INGOTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":ostrum_ingots"));
    public static final TagKey<Item> OSTRUM_NUGGETS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":ostrum_nuggets"));
    public static final TagKey<Item> RAW_CALORITE_ORES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":raw_calorite_ores"));
    public static final TagKey<Item> RAW_DESH_ORES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":raw_desh_ores"));
    public static final TagKey<Item> RAW_OSTRUM_ORES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":raw_ostrum_ores"));
    public static final TagKey<Item> STEEL_INGOTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":steel_ingots"));
    public static final TagKey<Item> STEEL_NUGGETS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":steel_nuggets"));

    public static final TagKey<Item> STEEL_BLOCKS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":steel_blocks"));
    public static final TagKey<Item> DESH_BLOCKS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":desh_blocks"));
    public static final TagKey<Item> OSTRUM_BLOCKS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":ostrum_blocks"));
    public static final TagKey<Item> CALORITE_BLOCKS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":calorite_blocks"));

    public static final TagKey<Item> WRENCHES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(getCommonNamespace() + ":wrenches"));

    private static String getCommonNamespace() {
        return ArchitecturyTarget.getCurrentTarget().equals("fabric") ? "c" : "forge";
    }
}
