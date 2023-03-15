package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.chest.CustomChestBlockEntity;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlockEntity;
import earth.terrarium.ad_astra.common.block.flag.FlagBlockEntity;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.entity.*;
import earth.terrarium.ad_astra.common.block.pipe.CableBlockEntity;
import earth.terrarium.ad_astra.common.block.pipe.FluidPipeBlockEntity;
import earth.terrarium.ad_astra.common.block.sign.CustomSignBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<FlagBlockEntity>> FLAG = BLOCK_ENTITY_TYPES.register("flag", () -> createBlockEntityType(FlagBlockEntity::new, ModBlocks.FLAGS));
    public static final RegistryEntry<BlockEntityType<GlobeBlockEntity>> GLOBE = BLOCK_ENTITY_TYPES.register("globe", () -> createBlockEntityType(GlobeBlockEntity::new, ModBlocks.GLOBES));
    public static final RegistryEntry<BlockEntityType<SlidingDoorBlockEntity>> SLIDING_DOOR = BLOCK_ENTITY_TYPES.register("sliding_door", () -> createBlockEntityType(SlidingDoorBlockEntity::new, ModBlocks.SLIDING_DOORS));
    public static final RegistryEntry<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register("fuel_refinery", () -> createBlockEntityType(FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY.get()));
    public static final RegistryEntry<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITY_TYPES.register("compressor", () -> createBlockEntityType(CompressorBlockEntity::new, ModBlocks.COMPRESSOR.get()));
    public static final RegistryEntry<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = BLOCK_ENTITY_TYPES.register("coal_generator", () -> createBlockEntityType(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get()));
    public static final RegistryEntry<BlockEntityType<OxygenLoaderBlockEntity>> OXYGEN_LOADER = BLOCK_ENTITY_TYPES.register("oxygen_loader", () -> createBlockEntityType(OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER.get()));
    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("solar_panel", () -> createBlockEntityType(SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL.get()));
    public static final RegistryEntry<BlockEntityType<NasaWorkbenchBlockEntity>> NASA_WORKBENCH = BLOCK_ENTITY_TYPES.register("nasa_workbench", () -> createBlockEntityType(NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH.get()));
    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register("oxygen_distributor", () -> createBlockEntityType(OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR.get()));
    public static final RegistryEntry<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP = BLOCK_ENTITY_TYPES.register("water_pump", () -> createBlockEntityType(WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP.get()));
    public static final RegistryEntry<BlockEntityType<EnergizerBlockEntity>> ENERGIZER = BLOCK_ENTITY_TYPES.register("energizer", () -> createBlockEntityType(EnergizerBlockEntity::new, ModBlocks.ENERGIZER.get()));
    public static final RegistryEntry<BlockEntityType<CryoFreezerBlockEntity>> CRYO_FREEZER = BLOCK_ENTITY_TYPES.register("cryo_freezer", () -> createBlockEntityType(CryoFreezerBlockEntity::new, ModBlocks.CRYO_FREEZER.get()));
    public static final RegistryEntry<BlockEntityType<OxygenSensorBlockEntity>> OXYGEN_SENSOR = BLOCK_ENTITY_TYPES.register("oxygen_sensor", () -> createBlockEntityType(OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR.get()));
    public static final RegistryEntry<BlockEntityType<SignBlockEntity>> SIGN = BLOCK_ENTITY_TYPES.register("sign", () -> createBlockEntityType(CustomSignBlockEntity::new, ModBlocks.SIGNS));
    public static final RegistryEntry<BlockEntityType<CustomChestBlockEntity>> CHEST = BLOCK_ENTITY_TYPES.register("chest", () -> createBlockEntityType(CustomChestBlockEntity::new, ModBlocks.CHESTS));
    public static final RegistryEntry<BlockEntityType<CableBlockEntity>> CABLE = BLOCK_ENTITY_TYPES.register("cable", () -> createBlockEntityType(CableBlockEntity::new, ModBlocks.CABLE_DUCT.get(), ModBlocks.STEEL_CABLE.get(), ModBlocks.DESH_CABLE.get()));
    public static final RegistryEntry<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE = BLOCK_ENTITY_TYPES.register("fluid_pipe", () -> createBlockEntityType(FluidPipeBlockEntity::new, ModBlocks.FLUID_PIPE_DUCT.get(), ModBlocks.DESH_FLUID_PIPE.get(), ModBlocks.OSTRUM_FLUID_PIPE.get()));

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, ResourcefulRegistry<Block> registry) {
        return BlockEntityType.Builder.of(factory, registry.stream().map(RegistryEntry::get).toArray(Block[]::new)).build(null);
    }
}