package earth.terrarium.adastra.common.registry;


import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import com.teamresourceful.resourcefullib.common.fluid.data.FluidProperties;
import com.teamresourceful.resourcefullib.common.fluid.registry.ResourcefulFluidRegistry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistryType;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("UnstableApiUsage")
public final class ModFluidProperties {
    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, AdAstra.MOD_ID);
    public static final ResourcefulFluidRegistry FLUID_TYPES = ResourcefulRegistries.create(ResourcefulRegistryType.FLUID, AdAstra.MOD_ID);

    public static final ResourceLocation STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation FLOWING = ResourceLocation.withDefaultNamespace("block/water_flow");
    public static final ResourceLocation OVERLAY = ResourceLocation.withDefaultNamespace("block/water_overlay");
    public static final ResourceLocation SCREEN_OVERLAY = ResourceLocation.withDefaultNamespace("textures/misc/underwater.png");

    public static final RegistryEntry<FluidData> OXYGEN = FLUID_TYPES.register("oxygen", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(0)
        .density(-1)
        .disablePlacing()
        .tintColor(0xffdae6f0)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> HYDROGEN = FLUID_TYPES.register("hydrogen", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(0)
        .density(-1)
        .disablePlacing()
        .tintColor(0xff89CFF0)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> OIL = FLUID_TYPES.register("oil", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(2000)
        .density(2000)
        .tintColor(0xff373A36)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> FUEL = FLUID_TYPES.register("fuel", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(1500)
        .density(1500)
        .tintColor(0xffE5292B)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> CRYO_FUEL = FLUID_TYPES.register("cryo_fuel", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(71)
        .density(71)
        .temperature(-196)
        .tintColor(0xff6cfffa)
        .canConvertToSource(false));

    public static final RegistryEntry<Block> OXYGEN_BLOCK = ModBlocks.FLUIDS.register("oxygen", () -> new LiquidBlock(ModFluids.OXYGEN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final RegistryEntry<Block> HYDROGEN_BLOCK = ModBlocks.FLUIDS.register("hydrogen", () -> new LiquidBlock(ModFluids.HYDROGEN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final RegistryEntry<Block> OIL_BLOCK = ModBlocks.FLUIDS.register("oil", () -> new LiquidBlock(ModFluids.OIL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryEntry<Block> FUEL_BLOCK = ModBlocks.FLUIDS.register("fuel", () -> new LiquidBlock(ModFluids.FUEL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.COLOR_RED)));
    public static final RegistryEntry<Block> CRYO_FUEL_BLOCK = ModBlocks.FLUIDS.register("cryo_fuel", () -> new LiquidBlock(ModFluids.CRYO_FUEL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.COLOR_CYAN)));

    public static final RegistryEntry<Item> OXYGEN_BUCKET = ModItems.BASIC_ITEMS.register("oxygen_bucket", () -> new BucketItem(
        ModFluids.OXYGEN.get(),
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> HYDROGEN_BUCKET = ModItems.BASIC_ITEMS.register("hydrogen_bucket", () -> new BucketItem(
        ModFluids.HYDROGEN.get(),
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> OIL_BUCKET = ModItems.BASIC_ITEMS.register("oil_bucket", () -> new BucketItem(
        ModFluids.OIL.get(),
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> FUEL_BUCKET = ModItems.BASIC_ITEMS.register("fuel_bucket", () -> new BucketItem(
        ModFluids.FUEL.get(),
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final RegistryEntry<Item> CRYO_FUEL_BUCKET = ModItems.BASIC_ITEMS.register("cryo_fuel_bucket", () -> new BucketItem(
        ModFluids.CRYO_FUEL.get(),
        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );
}
