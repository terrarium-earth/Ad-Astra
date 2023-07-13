package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.*;
import earth.terrarium.adastra.common.items.Ti69Item;
import earth.terrarium.adastra.common.items.armor.AerolyteSpaceSuitItem;
import earth.terrarium.adastra.common.items.armor.materials.AerolyteSpaceSuitMaterial;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, AdAstra.MOD_ID);
    public static final Supplier<CreativeModeTab> TAB = new ResourcefulCreativeTab(new ResourceLocation(AdAstra.MOD_ID, "main"))
        .setItemIcon(() -> Items.DIRT)
        .addRegistry(ITEMS)
        .build();

    public static final RegistryEntry<Item> TI_69 = ITEMS.register("ti_69", () -> new Ti69Item(new Item.Properties().stacksTo(1)));

    public static final RegistryEntry<Item> OXYGEN_DISTRIBUTOR = ITEMS.register("oxygen_distributor", () -> new CustomGeoBlockItem(
        ModBlocks.OXYGEN_DISTRIBUTOR.get(),
        new Item.Properties(),
        OxygenDistributorBlockEntity.SPIN));

    public static final RegistryEntry<Item> GRAVITY_NORMALIZER = ITEMS.register("gravity_normalizer", () -> new CustomGeoBlockItem(
        ModBlocks.GRAVITY_NORMALIZER.get(),
        new Item.Properties(),
        GravityNormalizerBlockEntity.IDLE_ON));

    public static final RegistryEntry<Item> ETRIONIC_SOLAR_PANEL = ITEMS.register("etreonic_solar_panel", () -> new CustomGeoBlockItem(
        ModBlocks.ETRIONIC_SOLAR_PANEL.get(),
        new Item.Properties(),
        SolarPanelBlockEntity.CLOSE));

    public static final RegistryEntry<Item> VESNIUM_SOLAR_PANEL = ITEMS.register("vesnium_solar_panel", () -> new CustomGeoBlockItem(
        ModBlocks.VESNIUM_SOLAR_PANEL.get(),
        new Item.Properties(),
        SolarPanelBlockEntity.CLOSE));

    public static final RegistryEntry<Item> HYDRAULIC_PRESS = ITEMS.register("hydraulic_press", () -> new CustomGeoBlockItem(
        ModBlocks.HYDRAULIC_PRESS.get(),
        new Item.Properties(),
        HydraulicPressBlockEntity.BONK));

    public static final RegistryEntry<Item> OIL_REFINERY = ITEMS.register("oil_refinery", () -> new CustomGeoBlockItem(
        ModBlocks.OIL_REFINERY.get(),
        new Item.Properties(),
        OilRefineryBlockEntity.IDLE_OFF));

    public static final RegistryEntry<Item> STEAM_GENERATOR = ITEMS.register("steam_generator", () -> new CustomGeoBlockItem(
        ModBlocks.STEAM_GENERATOR.get(),
        new Item.Properties(),
        SteamGeneratorBlockEntity.IDLE));

    public static final RegistryEntry<Item> EMITTER = ITEMS.register("emitter", () -> new CustomGeoBlockItem(
        ModBlocks.EMITTER.get(),
        new Item.Properties(),
        EmitterBlockEntity.IDLE));

    public static final RegistryEntry<Item> RECEIVER = ITEMS.register("receiver", () -> new CustomGeoBlockItem(
        ModBlocks.RECEIVER.get(),
        new Item.Properties(),
        ReceiverBlockEntity.IDLE));

    public static final RegistryEntry<Item> VESNIUM_COIL = ITEMS.register("vesnium_coil", () -> new CustomGeoBlockItem(
        ModBlocks.VESNIUM_COIL.get(),
        new Item.Properties(),
        VesniumCoilBlockEntity.IDLE));

    public static final RegistryEntry<Item> TINKERERS_WORKBENCH = ITEMS.register("tinkerers_workbench", () -> new CustomGeoBlockItem(
        ModBlocks.TINKERERS_WORKBENCH.get(),
        new Item.Properties(),
        TinkerersWorkbenchBlockEntity.FABRICATING));

    public static final RegistryEntry<Item> RECYCLER = ITEMS.register("recycler", () -> new CustomGeoBlockItem(
        ModBlocks.RECYCLER.get(),
        new Item.Properties(),
        RecyclerBlockEntity.IDLE_ON));

    public static final RegistryEntry<Item> BATTERY = ITEMS.register("battery", () -> new BlockItem(
        ModBlocks.BATTERY.get(), new Item.Properties())
    );

    public static final RegistryEntry<Item> ETRIONIC_BLAST_FURNACE = ITEMS.register("etreonic_blast_furnace", () -> new BlockItem(
        ModBlocks.ETRIONIC_BLAST_FURNACE.get(),
        new Item.Properties())
    );

    public static final RegistryEntry<Item> AEROLYTE_SPACE_HELMET = ITEMS.register("aerolyte_space_helmet", () -> new AerolyteSpaceSuitItem(
        AerolyteSpaceSuitMaterial.MATERIAL,
        ArmorItem.Type.HELMET,
        new Item.Properties())
    );

    public static final RegistryEntry<Item> AEROLYTE_SPACE_SUIT = ITEMS.register("aerolyte_space_suit", () -> new AerolyteSpaceSuitItem(
        AerolyteSpaceSuitMaterial.MATERIAL,
        ArmorItem.Type.CHESTPLATE,
        new Item.Properties())
    );

    public static final RegistryEntry<Item> AEROLYTE_SPACE_PANTS = ITEMS.register("aerolyte_space_pants", () -> new AerolyteSpaceSuitItem(
        AerolyteSpaceSuitMaterial.MATERIAL,
        ArmorItem.Type.LEGGINGS,
        new Item.Properties())
    );

    public static final RegistryEntry<Item> AEROLYTE_SPACE_BOOTS = ITEMS.register("aerolyte_space_boots", () -> new AerolyteSpaceSuitItem(
        AerolyteSpaceSuitMaterial.MATERIAL,
        ArmorItem.Type.BOOTS,
        new Item.Properties())
    );

    public static final RegistryEntry<Item> OXYGEN_BUCKET = ITEMS.register("oxygen_bucket", () -> new FluidBucketItem(
        ModFluidProperties.OXYGEN,
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> HYDROGEN_BUCKET = ITEMS.register("hydrogen_bucket", () -> new FluidBucketItem(
        ModFluidProperties.HYDROGEN,
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new FluidBucketItem(
        ModFluidProperties.OIL,
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> MOON_SAND = ITEMS.register("moon_sand", () -> new BlockItem(ModBlocks.MOON_SAND.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE = ITEMS.register("moon_stone", () -> new BlockItem(ModBlocks.MOON_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_DEEPSLATE = ITEMS.register("moon_deepslate", () -> new BlockItem(ModBlocks.MOON_DEEPSLATE.get(), new Item.Properties()));
}
