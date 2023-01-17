package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.*;
import earth.terrarium.ad_astra.common.block.pipe.CableBlockEntity;
import earth.terrarium.ad_astra.common.block.pipe.FluidPipeBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<EtrionicGeneratorBlockEntity>> ETRIONIC_GENERATOR = BLOCK_ENTITY_TYPES.register("etrionic_generator", () -> createBlockEntityType(EtrionicGeneratorBlockEntity::new, ModBlocks.ETRIONIC_GENERATOR.get()));
    public static final RegistryEntry<BlockEntityType<CombustionGeneratorBlockEntity>> COMBUSTION_GENERATOR = BLOCK_ENTITY_TYPES.register("combustion_generator", () -> createBlockEntityType(CombustionGeneratorBlockEntity::new, ModBlocks.COMBUSTION_GENERATOR.get()));
    public static final RegistryEntry<BlockEntityType<GeothermalGeneratorBlockEntity>> GEOTHERMAL_GENERATOR = BLOCK_ENTITY_TYPES.register("geothermal_generator", () -> createBlockEntityType(GeothermalGeneratorBlockEntity::new, ModBlocks.GEOTHERMAL_GENERATOR.get()));
    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("solar_panel", () -> createBlockEntityType(SolarPanelBlockEntity::new, ModBlocks.ETRIONIC_SOLAR_PANEL.get(), ModBlocks.VESNIUM_SOLAR_PANEL.get()));
    public static final RegistryEntry<BlockEntityType<EtrionicBlastFurnaceBlockEntity>> ETRIONIC_BLAST_FURNACE = BLOCK_ENTITY_TYPES.register("etrionic_blast_furnace", () -> createBlockEntityType(EtrionicBlastFurnaceBlockEntity::new, ModBlocks.ETRIONIC_BLAST_FURNACE.get()));
    public static final RegistryEntry<BlockEntityType<HydraulicPressBlockEntity>> HYDRAULIC_PRESS = BLOCK_ENTITY_TYPES.register("hydraulic_press", () -> createBlockEntityType(HydraulicPressBlockEntity::new, ModBlocks.HYDRAULIC_PRESS.get()));
    public static final RegistryEntry<BlockEntityType<ElectrolyzerBlockEntity>> ELECTROLYZER = BLOCK_ENTITY_TYPES.register("electrolyzer", () -> createBlockEntityType(ElectrolyzerBlockEntity::new, ModBlocks.ELECTROLYZER.get()));
    public static final RegistryEntry<BlockEntityType<OxygenSensorBlockEntity>> OXYGEN_SENSOR = BLOCK_ENTITY_TYPES.register("oxygen_sensor", () -> createBlockEntityType(OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR.get()));
    public static final RegistryEntry<BlockEntityType<OilRefineryBlockEntity>> OIL_REFINERY = BLOCK_ENTITY_TYPES.register("oil_refinery", () -> createBlockEntityType(OilRefineryBlockEntity::new, ModBlocks.OIL_REFINERY.get()));
    public static final RegistryEntry<BlockEntityType<CryogenicFreezerBlockEntity>> CRYOGENIC_FREEZER = BLOCK_ENTITY_TYPES.register("cryogenic_freezer", () -> createBlockEntityType(CryogenicFreezerBlockEntity::new, ModBlocks.CRYOGENIC_FREEZER.get()));
    public static final RegistryEntry<BlockEntityType<RecyclerBlockEntity>> RECYCLER = BLOCK_ENTITY_TYPES.register("recycler", () -> createBlockEntityType(RecyclerBlockEntity::new, ModBlocks.RECYCLER.get()));
    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register("oxygen_distributor", () -> createBlockEntityType(OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR.get()));
    public static final RegistryEntry<BlockEntityType<TemperatureRegulatorBlockEntity>> TEMPERATURE_REGULATOR = BLOCK_ENTITY_TYPES.register("temperature_regulator", () -> createBlockEntityType(TemperatureRegulatorBlockEntity::new, ModBlocks.TEMPERATURE_REGULATOR.get()));
    public static final RegistryEntry<BlockEntityType<GravityNormalizerBlockEntity>> GRAVITY_NORMALIZER = BLOCK_ENTITY_TYPES.register("gravity_normalizer", () -> createBlockEntityType(GravityNormalizerBlockEntity::new, ModBlocks.GRAVITY_NORMALIZER.get()));
    public static final RegistryEntry<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP = BLOCK_ENTITY_TYPES.register("water_pump", () -> createBlockEntityType(WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP.get()));

    public static final RegistryEntry<BlockEntityType<CableBlockEntity>> CABLE = BLOCK_ENTITY_TYPES.register("cable", () -> createBlockEntityType(CableBlockEntity::new, ModBlocks.ETRIUM_CABLE.get()));
    public static final RegistryEntry<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE = BLOCK_ENTITY_TYPES.register("fluid_pipe", () -> createBlockEntityType(FluidPipeBlockEntity::new, ModBlocks.DESMIUM_FLUID_PIPE.get()));

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, ResourcefulRegistry<Block> registry) {
        return BlockEntityType.Builder.of(factory, registry.stream().map(RegistryEntry::get).toArray(Block[]::new)).build(null);
    }
}
