package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.NasaWorkbenchBlockEntity;
import earth.terrarium.adastra.common.blockentities.flag.FlagBlockEntity;
import earth.terrarium.adastra.common.blockentities.GlobeBlockEntity;
import earth.terrarium.adastra.common.blockentities.PipeBlockEntity;
import earth.terrarium.adastra.common.blockentities.SlidingDoorBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineExtensionBlockEntity;
import earth.terrarium.adastra.common.blockentities.machines.*;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("unused")
public class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<ContainerMachineExtensionBlockEntity>> MACHINE_EXTENSION = BLOCK_ENTITY_TYPES.register(
        "machine_extension",
        () -> RegistryHelpers.createBlockEntityType(
            ContainerMachineExtensionBlockEntity::new,
            ModBlocks.MACHINES.stream().map(RegistryEntry::get).toArray(Block[]::new)));

    public static final RegistryEntry<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITY_TYPES.register(
        "compressor",
        () -> RegistryHelpers.createBlockEntityType(
            CompressorBlockEntity::new,
            ModBlocks.COMPRESSOR.get()));

    public static final RegistryEntry<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = BLOCK_ENTITY_TYPES.register(
        "coal_generator",
        () -> RegistryHelpers.createBlockEntityType(
            CoalGeneratorBlockEntity::new,
            ModBlocks.COAL_GENERATOR.get()));

    public static final RegistryEntry<BlockEntityType<OxygenLoaderBlockEntity>> OXYGEN_LOADER = BLOCK_ENTITY_TYPES.register(
        "oxygen_loader",
        () -> RegistryHelpers.createBlockEntityType(
            OxygenLoaderBlockEntity::new,
            ModBlocks.OXYGEN_LOADER.get()));

    public static final RegistryEntry<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register(
        "fuel_refinery",
        () -> RegistryHelpers.createBlockEntityType(
            FuelRefineryBlockEntity::new,
            ModBlocks.FUEL_REFINERY.get()));

    public static final RegistryEntry<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP = BLOCK_ENTITY_TYPES.register(
        "water_pump",
        () -> RegistryHelpers.createBlockEntityType(
            WaterPumpBlockEntity::new,
            ModBlocks.WATER_PUMP.get()));

    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register(
        "solar_panel",
        () -> RegistryHelpers.createBlockEntityType(
            SolarPanelBlockEntity::new,
            ModBlocks.SOLAR_PANEL.get()));

    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register(
        "oxygen_distributor",
        () -> RegistryHelpers.createBlockEntityType(
            OxygenDistributorBlockEntity::new,
            ModBlocks.OXYGEN_DISTRIBUTOR.get()));

    public static final RegistryEntry<BlockEntityType<EnergizerBlockEntity>> ENERGIZER = BLOCK_ENTITY_TYPES.register(
        "energizer",
        () -> RegistryHelpers.createBlockEntityType(
            EnergizerBlockEntity::new,
            ModBlocks.ENERGIZER.get()));

    public static final RegistryEntry<BlockEntityType<CryoFreezerBlockEntity>> CRYO_FREEZER = BLOCK_ENTITY_TYPES.register(
        "cryo_freezer",
        () -> RegistryHelpers.createBlockEntityType(
            CryoFreezerBlockEntity::new,
            ModBlocks.CRYO_FREEZER.get()));

    public static final RegistryEntry<BlockEntityType<OxygenSensorBlockEntity>> OXYGEN_SENSOR = BLOCK_ENTITY_TYPES.register(
        "oxygen_sensor",
        () -> RegistryHelpers.createBlockEntityType(
            OxygenSensorBlockEntity::new,
            ModBlocks.OXYGEN_SENSOR.get()));

    public static final RegistryEntry<BlockEntityType<NasaWorkbenchBlockEntity>> NASA_WORKBENCH = BLOCK_ENTITY_TYPES.register(
        "nasa_workbench",
        () -> RegistryHelpers.createBlockEntityType(
            NasaWorkbenchBlockEntity::new,
            ModBlocks.NASA_WORKBENCH.get()));

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

    public static final RegistryEntry<BlockEntityType<PipeBlockEntity>> PIPE = BLOCK_ENTITY_TYPES.register(
        "pipe",
        () -> createBlockEntityType(
            PipeBlockEntity::new,
            ModBlocks.PIPES));


    @SuppressWarnings("DataFlowIssue")
    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, ResourcefulRegistry<Block> registry) {
        return BlockEntityType.Builder.of(factory,
                registry.stream()
                    .map(RegistryEntry::get)
                    .toArray(Block[]::new))
            .build(null);
    }
}