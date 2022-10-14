package com.github.alexnijjar.ad_astra.registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPadBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.CryoFreezerBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.EnergizerBlockEntity;
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
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

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

		FLAG_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("flag_entity", () -> RegistryHelpers.createBlockEntityType(FlagBlockEntity::new, ModBlocks.WHITE_FLAG.get(), ModBlocks.BLACK_FLAG.get(), ModBlocks.BLUE_FLAG.get(), ModBlocks.BROWN_FLAG.get(), ModBlocks.CYAN_FLAG.get(), ModBlocks.GRAY_FLAG.get(), ModBlocks.GREEN_FLAG.get(), ModBlocks.LIGHT_BLUE_FLAG.get(), ModBlocks.LIGHT_GRAY_FLAG.get(), ModBlocks.LIME_FLAG.get(), ModBlocks.MAGENTA_FLAG.get(), ModBlocks.ORANGE_FLAG.get(), ModBlocks.PINK_FLAG.get(), ModBlocks.PURPLE_FLAG.get(), ModBlocks.RED_FLAG.get(), ModBlocks.YELLOW_FLAG.get()));
		GLOBE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("globe_entity", () -> RegistryHelpers.createBlockEntityType(GlobeBlockEntity::new, ModBlocks.EARTH_GLOBE.get(), ModBlocks.MOON_GLOBE.get(), ModBlocks.MARS_GLOBE.get(), ModBlocks.MERCURY_GLOBE.get(), ModBlocks.VENUS_GLOBE.get(), ModBlocks.GLACIO_GLOBE.get()));

		// Machine block entities
		FUEL_REFINERY = BLOCK_ENTITY_TYPES.register("fuel_refinery_entity", () -> RegistryHelpers.createBlockEntityType(FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY.get()));
		COMPRESSOR = BLOCK_ENTITY_TYPES.register("compressor_entity", () -> RegistryHelpers.createBlockEntityType(CompressorBlockEntity::new, ModBlocks.COMPRESSOR.get()));
		COAL_GENERATOR = BLOCK_ENTITY_TYPES.register("coal_generator_entity", () -> RegistryHelpers.createBlockEntityType(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get()));
		OXYGEN_LOADER = BLOCK_ENTITY_TYPES.register("oxygen_loader_entity", () -> RegistryHelpers.createBlockEntityType(OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER.get()));
		SOLAR_PANEL = BLOCK_ENTITY_TYPES.register("solar_panel_entity", () -> RegistryHelpers.createBlockEntityType(SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL.get()));
		NASA_WORKBENCH = BLOCK_ENTITY_TYPES.register("nasa_workbench_entity", () -> RegistryHelpers.createBlockEntityType(NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH.get()));
		OXYGEN_DISTRIBUTOR = BLOCK_ENTITY_TYPES.register("oxygen_distributor_entity", () -> RegistryHelpers.createBlockEntityType(OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR.get()));
		WATER_PUMP = BLOCK_ENTITY_TYPES.register("water_pump_entity", () -> RegistryHelpers.createBlockEntityType(WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP.get()));
		ENERGIZER = BLOCK_ENTITY_TYPES.register("energizer_entity", () -> RegistryHelpers.createBlockEntityType(EnergizerBlockEntity::new, ModBlocks.ENERGIZER.get()));
		CRYO_FREEZER = BLOCK_ENTITY_TYPES.register("cryo_freezer_entity", () -> RegistryHelpers.createBlockEntityType(CryoFreezerBlockEntity::new, ModBlocks.CRYO_FREEZER.get()));
		OXYGEN_SENSOR = BLOCK_ENTITY_TYPES.register("oxygen_sensor", () -> RegistryHelpers.createBlockEntityType(OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR.get()));
		SLIDING_DOOR = BLOCK_ENTITY_TYPES.register("sliding_door", () -> RegistryHelpers.createBlockEntityType(SlidingDoorBlockEntity::new, ModBlocks.STEEL_SLIDING_DOOR.get(), ModBlocks.DESH_SLIDING_DOOR.get(), ModBlocks.OSTRUM_SLIDING_DOOR.get(), ModBlocks.CALORITE_SLIDING_DOOR.get(), ModBlocks.AIRLOCK.get(), ModBlocks.REINFORCED_DOOR.get()));
		LAUNCH_PAD = BLOCK_ENTITY_TYPES.register("launch_pad", () -> RegistryHelpers.createBlockEntityType(LaunchPadBlockEntity::new, ModBlocks.LAUNCH_PAD.get()));

		CABLE = BLOCK_ENTITY_TYPES.register("cable", () -> RegistryHelpers.createBlockEntityType(CableBlockEntity::new, ModBlocks.STEEL_CABLE.get(), ModBlocks.DESH_CABLE.get()));
		FLUID_PIPE = BLOCK_ENTITY_TYPES.register("fluid_pipe", () -> RegistryHelpers.createBlockEntityType(FluidPipeBlockEntity::new, ModBlocks.DESH_FLUID_PIPE.get(), ModBlocks.OSTRUM_FLUID_PIPE.get()));

		BLOCK_ENTITY_TYPES.register();
	}
}