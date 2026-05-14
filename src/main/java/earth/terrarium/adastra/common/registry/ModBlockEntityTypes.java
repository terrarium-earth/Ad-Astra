package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.GlobeBlockEntity;
import earth.terrarium.adastra.common.blockentities.RadioBlockEntity;
import earth.terrarium.adastra.common.blockentities.SlidingDoorBlockEntity;
import earth.terrarium.adastra.common.blockentities.flag.FlagBlockEntity;
import earth.terrarium.adastra.common.blockentities.machines.*;
import earth.terrarium.adastra.common.blockentities.pipes.CableBlockEntity;
import earth.terrarium.adastra.common.blockentities.pipes.FluidPipeBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("unused")
public class ModBlockEntityTypes {

    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = BLOCK_ENTITY_TYPES.register(
        "coal_generator",
        () -> BlockEntityType.Builder.of(
            CoalGeneratorBlockEntity::new,
            ModBlocks.COAL_GENERATOR.get()).build(null));

    public static final RegistryEntry<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITY_TYPES.register(
        "compressor",
        () -> BlockEntityType.Builder.of(
            CompressorBlockEntity::new,
            ModBlocks.COMPRESSOR.get()).build(null));

    public static final RegistryEntry<BlockEntityType<EtrionicBlastFurnaceBlockEntity>> ETRIONIC_BLAST_FURNACE = BLOCK_ENTITY_TYPES.register(
        "etreonic_blast_furnace",
        () -> BlockEntityType.Builder.of(
            EtrionicBlastFurnaceBlockEntity::new,
            ModBlocks.ETRIONIC_BLAST_FURNACE.get()).build(null));

    public static final RegistryEntry<BlockEntityType<OxygenLoaderBlockEntity>> OXYGEN_LOADER = BLOCK_ENTITY_TYPES.register(
        "oxygen_loader",
        () -> BlockEntityType.Builder.of(
            OxygenLoaderBlockEntity::new,
            ModBlocks.OXYGEN_LOADER.get()).build(null));

    public static final RegistryEntry<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register(
        "fuel_refinery",
        () -> BlockEntityType.Builder.of(
            FuelRefineryBlockEntity::new,
            ModBlocks.FUEL_REFINERY.get()).build(null));

    public static final RegistryEntry<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP = BLOCK_ENTITY_TYPES.register(
        "water_pump",
        () -> BlockEntityType.Builder.of(
            WaterPumpBlockEntity::new,
            ModBlocks.WATER_PUMP.get()).build(null));

    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register(
        "solar_panel",
        () -> BlockEntityType.Builder.of(
            SolarPanelBlockEntity::new,
            ModBlocks.SOLAR_PANEL.get()).build(null));

    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register(
        "oxygen_distributor",
        () -> BlockEntityType.Builder.of(
            OxygenDistributorBlockEntity::new,
            ModBlocks.OXYGEN_DISTRIBUTOR.get()).build(null));

    public static final RegistryEntry<BlockEntityType<GravityNormalizerBlockEntity>> GRAVITY_NORMALIZER = BLOCK_ENTITY_TYPES.register(
        "gravity_normalizer",
        () -> BlockEntityType.Builder.of(
            GravityNormalizerBlockEntity::new,
            ModBlocks.GRAVITY_NORMALIZER.get()).build(null));

    public static final RegistryEntry<BlockEntityType<EnergizerBlockEntity>> ENERGIZER = BLOCK_ENTITY_TYPES.register(
        "energizer",
        () -> BlockEntityType.Builder.of(
            EnergizerBlockEntity::new,
            ModBlocks.ENERGIZER.get()).build(null));

    public static final RegistryEntry<BlockEntityType<CryoFreezerBlockEntity>> CRYO_FREEZER = BLOCK_ENTITY_TYPES.register(
        "cryo_freezer",
        () -> BlockEntityType.Builder.of(
            CryoFreezerBlockEntity::new,
            ModBlocks.CRYO_FREEZER.get()).build(null));

    public static final RegistryEntry<BlockEntityType<DetectorBlockEntity>> Detector = BLOCK_ENTITY_TYPES.register(
        "detector",
        () -> BlockEntityType.Builder.of(
            DetectorBlockEntity::new,
            ModBlocks.OXYGEN_SENSOR.get()).build(null));

    public static final RegistryEntry<BlockEntityType<NasaWorkbenchBlockEntity>> NASA_WORKBENCH = BLOCK_ENTITY_TYPES.register(
        "nasa_workbench",
        () -> BlockEntityType.Builder.of(
            NasaWorkbenchBlockEntity::new,
            ModBlocks.NASA_WORKBENCH.get()).build(null));

    public static final RegistryEntry<BlockEntityType<GlobeBlockEntity>> GLOBE = BLOCK_ENTITY_TYPES.register(
        "globe",
        () -> createBlockEntityType(
            GlobeBlockEntity::new,
            ModBlocks.GLOBES));

    public static final RegistryEntry<BlockEntityType<FlagBlockEntity>> FLAG = BLOCK_ENTITY_TYPES.register(
        "flag",
        () -> createBlockEntityType(
            FlagBlockEntity::new,
            ModBlocks.FLAGS));

    public static final RegistryEntry<BlockEntityType<SlidingDoorBlockEntity>> SLIDING_DOOR = BLOCK_ENTITY_TYPES.register(
        "sliding_door",
        () -> createBlockEntityType(
            SlidingDoorBlockEntity::new,
            ModBlocks.SLIDING_DOORS));

    public static final RegistryEntry<BlockEntityType<CableBlockEntity>> CABLE = BLOCK_ENTITY_TYPES.register(
        "cable",
        () -> createBlockEntityType(
            CableBlockEntity::new,
            ModBlocks.CABLES));

    public static final RegistryEntry<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE = BLOCK_ENTITY_TYPES.register(
        "fluid_pipe",
        () -> createBlockEntityType(
            FluidPipeBlockEntity::new,
            ModBlocks.FLUID_PIPES));

    public static final RegistryEntry<BlockEntityType<RadioBlockEntity>> RADIO = BLOCK_ENTITY_TYPES.register(
        "radio",
        () -> BlockEntityType.Builder.of(
            RadioBlockEntity::new,
            ModBlocks.RADIO.get()).build(null));

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, ResourcefulRegistry<Block> registry) {
        return BlockEntityType.Builder.of(factory,
                registry.stream()
                    .map(RegistryEntry::get)
                    .toArray(Block[]::new))
            .build(null);
    }
}