package com.github.alexnijjar.ad_astra.registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPadBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.CryoFreezerBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.EnergizerBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.FuelRefineryBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.NasaWorkbenchBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenLoaderBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenSensorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.SolarPanelBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.WaterPumpBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.pipes.CableBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.pipes.FluidPipeBlockEntity;
import com.github.alexnijjar.ad_astra.mixin.BlockEntityTypeAccessor;

import dev.architectury.registry.registries.DeferredRegister;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {

	// Flag Block Entity
	public static Supplier<BlockEntityType<FlagBlockEntity>> FLAG_BLOCK_ENTITY;

	// Globe Block Entity
	public static Supplier<BlockEntityType<GlobeBlockEntity>> GLOBE_BLOCK_ENTITY;

	// Machine Block Entity
	public static Supplier<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY;
	public static Supplier<BlockEntityType<CompressorBlockEntity>> COMPRESSOR;
	public static Supplier<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR;
	public static Supplier<BlockEntityType<OxygenLoaderBlockEntity>> OXYGEN_LOADER;
	public static Supplier<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL;
	public static Supplier<BlockEntityType<NasaWorkbenchBlockEntity>> NASA_WORKBENCH;
	public static Supplier<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR;
	public static Supplier<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP;
	public static Supplier<BlockEntityType<EnergizerBlockEntity>> ENERGIZER;
	public static Supplier<BlockEntityType<CryoFreezerBlockEntity>> CRYO_FREEZER;
	public static Supplier<BlockEntityType<OxygenSensorBlockEntity>> OXYGEN_SENSOR;
	public static Supplier<BlockEntityType<SlidingDoorBlockEntity>> SLIDING_DOOR;
	public static Supplier<BlockEntityType<LaunchPadBlockEntity>> LAUNCH_PAD;

	public static Supplier<BlockEntityType<CableBlockEntity>> CABLE;
	public static Supplier<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE;

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);

	public static void register() {

		FLAG_BLOCK_ENTITY = register("flag_entity", FlagBlockEntity::new, ModBlocks.WHITE_FLAG.get(), ModBlocks.BLACK_FLAG.get(), ModBlocks.BLUE_FLAG.get(), ModBlocks.BROWN_FLAG.get(), ModBlocks.CYAN_FLAG.get(), ModBlocks.GRAY_FLAG.get(), ModBlocks.GREEN_FLAG.get(), ModBlocks.LIGHT_BLUE_FLAG.get(), ModBlocks.LIGHT_GRAY_FLAG, ModBlocks.LIME_FLAG.get(), ModBlocks.MAGENTA_FLAG.get(), ModBlocks.ORANGE_FLAG.get(), ModBlocks.PINK_FLAG.get(), ModBlocks.PURPLE_FLAG.get(), ModBlocks.RED_FLAG.get(), ModBlocks.YELLOW_FLAG.get());

		GLOBE_BLOCK_ENTITY = register("globe_entity", GlobeBlockEntity::new, ModBlocks.EARTH_GLOBE.get(), ModBlocks.MOON_GLOBE.get(), ModBlocks.MARS_GLOBE.get(), ModBlocks.MERCURY_GLOBE.get(), ModBlocks.VENUS_GLOBE.get(), ModBlocks.GLACIO_GLOBE.get());

		// Machine block entities
		FUEL_REFINERY = register("fuel_refinery_entity", FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY.get());
		COMPRESSOR = register("compressor_entity", CompressorBlockEntity::new, ModBlocks.COMPRESSOR.get());
		COAL_GENERATOR = register("coal_generator_entity", CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get());
		OXYGEN_LOADER = register("oxygen_loader_entity", OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER.get());
		SOLAR_PANEL = register("solar_panel_entity", SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL.get());
		NASA_WORKBENCH = register("nasa_workbench_entity", NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH.get());
		OXYGEN_DISTRIBUTOR = register("oxygen_distributor_entity", OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR.get());
		WATER_PUMP = register("water_pump_entity", WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP.get());
		ENERGIZER = register("energizer_entity", EnergizerBlockEntity::new, ModBlocks.ENERGIZER.get());
		CRYO_FREEZER = register("cryo_freezer_entity", CryoFreezerBlockEntity::new, ModBlocks.CRYO_FREEZER.get());
		OXYGEN_SENSOR = register("oxygen_sensor", OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR.get());
		SLIDING_DOOR = register("sliding_door", SlidingDoorBlockEntity::new, ModBlocks.IRON_SLIDING_DOOR.get(), ModBlocks.STEEL_SLIDING_DOOR.get(), ModBlocks.DESH_SLIDING_DOOR.get(), ModBlocks.OSTRUM_SLIDING_DOOR.get(), ModBlocks.CALORITE_SLIDING_DOOR.get(), ModBlocks.AIRLOCK.get(), ModBlocks.REINFORCED_DOOR.get());
		LAUNCH_PAD = register("launch_pad", LaunchPadBlockEntity::new, ModBlocks.LAUNCH_PAD.get());

		CABLE = register("cable", CableBlockEntity::new, ModBlocks.STEEL_CABLE.get(), ModBlocks.DESH_CABLE.get());
		FLUID_PIPE = register("fluid_pipe", FluidPipeBlockEntity::new, ModBlocks.DESH_FLUID_PIPE.get(), ModBlocks.OSTRUM_FLUID_PIPE).get();

		// Add custom signs to the sign block entity registry
		BlockEntityTypeAccessor signRegistry = ((BlockEntityTypeAccessor) BlockEntityType.SIGN);
		Set<Block> signBlocks = new HashSet<>(signRegistry.getBlocks());
		signBlocks.add(ModBlocks.GLACIAN_SIGN.get());
		signBlocks.add(ModBlocks.GLACIAN_WALL_SIGN.get());
		signRegistry.setBlocks(signBlocks);

		// Add custom chests to the chest block entity registry
		BlockEntityTypeAccessor chestRegistry = ((BlockEntityTypeAccessor) BlockEntityType.CHEST);
		Set<Block> chestBlocks = new HashSet<>(chestRegistry.getBlocks());
		chestBlocks.add(ModBlocks.AERONOS_CHEST.get());
		chestBlocks.add(ModBlocks.STROPHAR_CHEST.get());
		chestRegistry.setBlocks(chestBlocks);

		EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((AbstractMachineBlockEntity) blockEntity).getSideEnergyStorage(direction), SOLAR_PANEL, COAL_GENERATOR, COMPRESSOR, FUEL_REFINERY, OXYGEN_LOADER, OXYGEN_DISTRIBUTOR, WATER_PUMP, ENERGIZER, CRYO_FREEZER);

		FluidStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((FluidMachineBlockEntity) blockEntity).inputTank, FUEL_REFINERY, OXYGEN_LOADER, OXYGEN_DISTRIBUTOR, WATER_PUMP, CRYO_FREEZER);

		BLOCK_ENTITY_TYPES.register();
	}

	public static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntityFactory<T> factory, Block... blocks) {
		return BLOCK_ENTITY_TYPES.register(id, () -> BlockEntityType.Builder.create(factory, blocks).build(null));
	}
}