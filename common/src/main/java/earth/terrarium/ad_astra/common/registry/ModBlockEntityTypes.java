package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlockEntity;
import earth.terrarium.ad_astra.common.block.flag.FlagBlockEntity;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.entity.*;
import earth.terrarium.ad_astra.common.block.pipe.CableBlockEntity;
import earth.terrarium.ad_astra.common.block.pipe.FluidPipeBlockEntity;
import earth.terrarium.ad_astra.mixin.BlockEntityTypeAccessor;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;

public class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(Registry.BLOCK_ENTITY_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<BlockEntityType<FlagBlockEntity>> FLAG = BLOCK_ENTITY_TYPES.register("flag", () -> createBlockEntityType(FlagBlockEntity::new, ModBlocks.FLAGS));
    public static final RegistryEntry<BlockEntityType<GlobeBlockEntity>> GLOBE = BLOCK_ENTITY_TYPES.register("globe", () -> createBlockEntityType(GlobeBlockEntity::new, ModBlocks.GLOBES));
    public static final RegistryEntry<BlockEntityType<SlidingDoorBlockEntity>> SLIDING_DOOR = BLOCK_ENTITY_TYPES.register("sliding_door", () -> createBlockEntityType(SlidingDoorBlockEntity::new, ModBlocks.SLIDING_DOORS));
    public static final RegistryEntry<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register("fuel_refinery", () -> RegistryHelpers.createBlockEntityType(FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY.get()));
    public static final RegistryEntry<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITY_TYPES.register("compressor", () -> RegistryHelpers.createBlockEntityType(CompressorBlockEntity::new, ModBlocks.COMPRESSOR.get()));
    public static final RegistryEntry<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = BLOCK_ENTITY_TYPES.register("coal_generator", () -> RegistryHelpers.createBlockEntityType(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get()));
    public static final RegistryEntry<BlockEntityType<OxygenLoaderBlockEntity>> OXYGEN_LOADER = BLOCK_ENTITY_TYPES.register("oxygen_loader", () -> RegistryHelpers.createBlockEntityType(OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER.get()));
    public static final RegistryEntry<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("solar_panel", () -> RegistryHelpers.createBlockEntityType(SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL.get()));
    public static final RegistryEntry<BlockEntityType<NasaWorkbenchBlockEntity>> NASA_WORKBENCH = BLOCK_ENTITY_TYPES.register("nasa_workbench", () -> RegistryHelpers.createBlockEntityType(NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH.get()));
    public static final RegistryEntry<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register("oxygen_distributor", () -> RegistryHelpers.createBlockEntityType(OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR.get()));
    public static final RegistryEntry<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP = BLOCK_ENTITY_TYPES.register("water_pump", () -> RegistryHelpers.createBlockEntityType(WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP.get()));
    public static final RegistryEntry<BlockEntityType<EnergizerBlockEntity>> ENERGIZER = BLOCK_ENTITY_TYPES.register("energizer", () -> RegistryHelpers.createBlockEntityType(EnergizerBlockEntity::new, ModBlocks.ENERGIZER.get()));
    public static final RegistryEntry<BlockEntityType<CryoFreezerBlockEntity>> CRYO_FREEZER = BLOCK_ENTITY_TYPES.register("cryo_freezer", () -> RegistryHelpers.createBlockEntityType(CryoFreezerBlockEntity::new, ModBlocks.CRYO_FREEZER.get()));
    public static final RegistryEntry<BlockEntityType<OxygenSensorBlockEntity>> OXYGEN_SENSOR = BLOCK_ENTITY_TYPES.register("oxygen_sensor", () -> RegistryHelpers.createBlockEntityType(OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR.get()));
    public static final RegistryEntry<BlockEntityType<CableBlockEntity>> CABLE = BLOCK_ENTITY_TYPES.register("cable", () -> RegistryHelpers.createBlockEntityType(CableBlockEntity::new, ModBlocks.STEEL_CABLE.get(), ModBlocks.DESH_CABLE.get()));
    public static final RegistryEntry<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE = BLOCK_ENTITY_TYPES.register("fluid_pipe", () -> RegistryHelpers.createBlockEntityType(FluidPipeBlockEntity::new, ModBlocks.DESH_FLUID_PIPE.get(), ModBlocks.OSTRUM_FLUID_PIPE.get()));

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(RegistryHelpers.BlockEntityFactory<E> factory, ResourcefulRegistry<Block> registry) {
        return RegistryHelpers.createBlockEntityType(factory, registry.stream().map(RegistryEntry::get).toArray(Block[]::new));
    }

    public static void postInit() {
        // Add custom signs to the sign block entity registry
        BlockEntityTypeAccessor signRegistry = ((BlockEntityTypeAccessor) BlockEntityType.SIGN);
        Set<Block> signBlocks = new HashSet<>(signRegistry.getValidBlocks());
        signBlocks.add(ModBlocks.GLACIAN_SIGN.get());
        signBlocks.add(ModBlocks.GLACIAN_WALL_SIGN.get());
        signRegistry.setValidBlocks(signBlocks);

        // Add custom chests to the chest block entity registry
        BlockEntityTypeAccessor chestRegistry = ((BlockEntityTypeAccessor) BlockEntityType.CHEST);
        Set<Block> chestBlocks = new HashSet<>(chestRegistry.getValidBlocks());
        chestBlocks.add(ModBlocks.AERONOS_CHEST.get());
        chestBlocks.add(ModBlocks.STROPHAR_CHEST.get());
        chestRegistry.setValidBlocks(chestBlocks);
        ModEntityTypes.registerSpawnPlacements();
    }
}