package com.github.alexnijjar.ad_astra.registry;

// import com.github.alexnijjar.ad_astra.blocks.cables.CableBlockEntity;

import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.*;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {

	// Flag Block Entity.
	public static BlockEntityType<FlagBlockEntity> FLAG_BLOCK_ENTITY;

	// Globe Block Entity
	public static BlockEntityType<GlobeBlockEntity> GLOBE_BLOCK_ENTITY;

	// Machine Block Entity.
	public static BlockEntityType<FuelRefineryBlockEntity> FUEL_REFINERY;
	public static BlockEntityType<CompressorBlockEntity> COMPRESSOR;
	public static BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR;
	public static BlockEntityType<OxygenLoaderBlockEntity> OXYGEN_LOADER;
	public static BlockEntityType<SolarPanelBlockEntity> SOLAR_PANEL;
	public static BlockEntityType<NasaWorkbenchBlockEntity> NASA_WORKBENCH;
	public static BlockEntityType<OxygenDistributorBlockEntity> OXYGEN_DISTRIBUTOR;
	public static BlockEntityType<WaterPumpBlockEntity> WATER_PUMP;
	public static BlockEntityType<EnergizerBlockEntity> ENERGIZER;
	public static BlockEntityType<CryoFreezerBlockEntity> CRYO_FREEZER;
	public static BlockEntityType<OxygenSensorBlockEntity> OXYGEN_SENSOR;

	// public static BlockEntityType<CableBlockEntity> CABLE;

	public static void register() {
		FLAG_BLOCK_ENTITY = register("flag_entity", FlagBlockEntity::new, ModBlocks.FLAG, ModBlocks.FLAG_BLUE, ModBlocks.FLAG_BROWN, ModBlocks.FLAG_CYAN, ModBlocks.FLAG_GRAY, ModBlocks.FLAG_GREEN, ModBlocks.FLAG_LIGHT_BLUE, ModBlocks.FLAG_LIME,
				ModBlocks.FLAG_MAGENTA, ModBlocks.FLAG_ORANGE, ModBlocks.FLAG_PINK, ModBlocks.FLAG_PURPLE, ModBlocks.FLAG_RED, ModBlocks.FLAG_YELLOW);

		GLOBE_BLOCK_ENTITY = register("globe_entity", GlobeBlockEntity::new, ModBlocks.EARTH_GLOBE, ModBlocks.MOON_GLOBE, ModBlocks.MARS_GLOBE, ModBlocks.MERCURY_GLOBE, ModBlocks.VENUS_GLOBE, ModBlocks.GLACIO_GLOBE);

		// Machine block entities
		FUEL_REFINERY = register("fuel_refinery_entity", FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY);
		COMPRESSOR = register("compressor_entity", CompressorBlockEntity::new, ModBlocks.COMPRESSOR);
		COAL_GENERATOR = register("coal_generator_entity", CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR);
		OXYGEN_LOADER = register("oxygen_loader_entity", OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER);
		SOLAR_PANEL = register("solar_panel_entity", SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL);
		NASA_WORKBENCH = register("nasa_workbench_entity", NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH);
		OXYGEN_DISTRIBUTOR = register("oxygen_distributor_entity", OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR);
		WATER_PUMP = register("water_pump_entity", WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP);
		ENERGIZER = register("energizer_entity", EnergizerBlockEntity::new, ModBlocks.ENERGIZER);
		CRYO_FREEZER = register("cryo_freezer_entity", CryoFreezerBlockEntity::new, ModBlocks.CRYO_FREEZER);
		OXYGEN_SENSOR = register("oxygen_sensor", OxygenSensorBlockEntity::new, ModBlocks.OXYGEN_SENSOR);

		// CABLE = register("steel_cable", CableBlockEntity::new, ModBlocks.STEEL_CABLE, ModBlocks.DESH_CABLE);

		EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((AbstractMachineBlockEntity) blockEntity).getSideEnergyStorage(direction), SOLAR_PANEL, COAL_GENERATOR, COMPRESSOR, FUEL_REFINERY, OXYGEN_LOADER, OXYGEN_DISTRIBUTOR,
				WATER_PUMP, ENERGIZER, CRYO_FREEZER);

		// EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((CableBlockEntity) blockEntity).getSideEnergyStorage(direction), CABLE);
		FluidStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((FluidMachineBlockEntity) blockEntity).inputTank, FUEL_REFINERY, OXYGEN_LOADER, OXYGEN_DISTRIBUTOR, WATER_PUMP, CRYO_FREEZER);
	}

	public static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ModIdentifier(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
	}
}