package earth.terrarium.ad_astra.registry;

import earth.terrarium.ad_astra.blocks.door.SlidingDoorBlockEntity;
import earth.terrarium.ad_astra.blocks.flags.FlagBlockEntity;
import earth.terrarium.ad_astra.blocks.globes.GlobeBlockEntity;
import earth.terrarium.ad_astra.blocks.launchpad.LaunchPadBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.*;
import earth.terrarium.ad_astra.blocks.pipes.CableBlockEntity;
import earth.terrarium.ad_astra.blocks.pipes.FluidPipeBlockEntity;
import earth.terrarium.ad_astra.mixin.BlockEntityTypeAccessor;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModBlockEntities {

    public static final Supplier<BlockEntityType<FlagBlockEntity>> FLAG_BLOCK_ENTITY =register("flag", () -> RegistryHelpers.createBlockEntityType(FlagBlockEntity::new, ModBlocks.WHITE_FLAG.get(), ModBlocks.BLACK_FLAG.get(), ModBlocks.BLUE_FLAG.get(), ModBlocks.BROWN_FLAG.get(), ModBlocks.CYAN_FLAG.get(), ModBlocks.GRAY_FLAG.get(), ModBlocks.GREEN_FLAG.get(), ModBlocks.LIGHT_BLUE_FLAG.get(), ModBlocks.LIGHT_GRAY_FLAG.get(), ModBlocks.LIME_FLAG.get(), ModBlocks.MAGENTA_FLAG.get(), ModBlocks.ORANGE_FLAG.get(), ModBlocks.PINK_FLAG.get(), ModBlocks.PURPLE_FLAG.get(), ModBlocks.RED_FLAG.get(), ModBlocks.YELLOW_FLAG.get()));
    public static final Supplier<BlockEntityType<GlobeBlockEntity>> GLOBE_BLOCK_ENTITY = register("globe", () -> RegistryHelpers.createBlockEntityType(GlobeBlockEntity::new, ModBlocks.EARTH_GLOBE.get(), ModBlocks.MOON_GLOBE.get(), ModBlocks.MARS_GLOBE.get(), ModBlocks.MERCURY_GLOBE.get(), ModBlocks.VENUS_GLOBE.get(), ModBlocks.GLACIO_GLOBE.get()));
    public static final Supplier<BlockEntityType<FuelRefineryBlockEntity>>FUEL_REFINERY = register("fuel_refinery", () -> RegistryHelpers.createBlockEntityType(FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY.get()));
    public static final Supplier<BlockEntityType<CompressorBlockEntity>>COMPRESSOR = register("compressor", () -> RegistryHelpers.createBlockEntityType(CompressorBlockEntity::new, ModBlocks.COMPRESSOR.get()));
    public static final Supplier<BlockEntityType<CoalGeneratorBlockEntity>>COAL_GENERATOR = register("coal_generator", () -> RegistryHelpers.createBlockEntityType(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get()));
    public static final Supplier<BlockEntityType<OxygenLoaderBlockEntity>>OXYGEN_LOADER = register("oxygen_loader", () -> RegistryHelpers.createBlockEntityType(OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER.get()));
    public static final Supplier<BlockEntityType<SolarPanelBlockEntity>>SOLAR_PANEL = register("solar_panel", () -> RegistryHelpers.createBlockEntityType(SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL.get()));
    public static final Supplier<BlockEntityType<NasaWorkbenchBlockEntity>>NASA_WORKBENCH = register("nasa_workbench", () -> RegistryHelpers.createBlockEntityType(NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH.get()));
    public static final Supplier<BlockEntityType<OxygenDistributorBlockEntity>>OXYGEN_DISTRIBUTOR = register("oxygen_distributor", () -> RegistryHelpers.createBlockEntityType(OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR.get()));
    public static final Supplier<BlockEntityType<WaterPumpBlockEntity>>WATER_PUMP = register("water_pump", () -> RegistryHelpers.createBlockEntityType(WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP.get()));
    public static final Supplier<BlockEntityType<EnergizerBlockEntity>>ENERGIZER = register("energizer", () -> RegistryHelpers.createBlockEntityType(EnergizerBlockEntity::new, ModBlocks.ENERGIZER.get()));
    public static final Supplier<BlockEntityType<CryoFreezerBlockEntity>>CRYO_FREEZER = register("cryo_freezer", () -> RegistryHelpers.createBlockEntityType(CryoFreezerBlockEntity::new, ModBlocks.CRYO_FREEZER.get()));
    public static final Supplier<BlockEntityType<OxygenSensorBlockEntity>>OXYGEN_SENSOR = register("oxygen_sensor", () -> RegistryHelpers.createBlockEntityType(OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR.get()));
    public static final Supplier<BlockEntityType<SlidingDoorBlockEntity>>SLIDING_DOOR = register("sliding_door", () -> RegistryHelpers.createBlockEntityType(SlidingDoorBlockEntity::new, ModBlocks.STEEL_SLIDING_DOOR.get(), ModBlocks.DESH_SLIDING_DOOR.get(), ModBlocks.OSTRUM_SLIDING_DOOR.get(), ModBlocks.CALORITE_SLIDING_DOOR.get(), ModBlocks.AIRLOCK.get(), ModBlocks.REINFORCED_DOOR.get()));
    public static final Supplier<BlockEntityType<LaunchPadBlockEntity>>LAUNCH_PAD = register("launch_pad", () -> RegistryHelpers.createBlockEntityType(LaunchPadBlockEntity::new, ModBlocks.LAUNCH_PAD.get()));
    public static final Supplier<BlockEntityType<CableBlockEntity>>CABLE = register("cable", () -> RegistryHelpers.createBlockEntityType(CableBlockEntity::new, ModBlocks.STEEL_CABLE.get(), ModBlocks.DESH_CABLE.get()));
    public static final Supplier<BlockEntityType<FluidPipeBlockEntity>>FLUID_PIPE = register("fluid_pipe", () -> RegistryHelpers.createBlockEntityType(FluidPipeBlockEntity::new, ModBlocks.DESH_FLUID_PIPE.get(), ModBlocks.OSTRUM_FLUID_PIPE.get()));

    private static <T extends BlockEntityType<B>, B extends BlockEntity> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.BLOCK_ENTITY_TYPE, id, object);
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

    public static void init() {
    }
}