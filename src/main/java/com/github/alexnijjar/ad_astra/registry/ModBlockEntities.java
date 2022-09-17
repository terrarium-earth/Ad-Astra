package com.github.alexnijjar.ad_astra.registry;

import java.util.HashSet;
import java.util.Set;

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
	public static BlockEntityType<SlidingDoorBlockEntity> SLIDING_DOOR;
	public static BlockEntityType<LaunchPadBlockEntity> LAUNCH_PAD;

	public static BlockEntityType<CableBlockEntity> CABLE;
	public static BlockEntityType<FluidPipeBlockEntity> FLUID_PIPE;

	public static void register() {
		FLAG_BLOCK_ENTITY = register("flag_entity", FlagBlockEntity::new, ModBlocks.WHITE_FLAG, ModBlocks.BLUE_FLAG, ModBlocks.BLACK_FLAG, ModBlocks.BROWN_FLAG, ModBlocks.CYAN_FLAG, ModBlocks.GRAY_FLAG, ModBlocks.GREEN_FLAG, ModBlocks.LIGHT_BLUE_FLAG,
				ModBlocks.LIGHT_GRAY_FLAG, ModBlocks.LIME_FLAG, ModBlocks.MAGENTA_FLAG, ModBlocks.ORANGE_FLAG, ModBlocks.PINK_FLAG, ModBlocks.PURPLE_FLAG, ModBlocks.RED_FLAG, ModBlocks.YELLOW_FLAG);

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
		SLIDING_DOOR = register("sliding_door", SlidingDoorBlockEntity::new, ModBlocks.IRON_SLIDING_DOOR, ModBlocks.STEEL_SLIDING_DOOR, ModBlocks.DESH_SLIDING_DOOR, ModBlocks.OSTRUM_SLIDING_DOOR, ModBlocks.CALORITE_SLIDING_DOOR, ModBlocks.AIRLOCK,
				ModBlocks.REINFORCED_DOOR);
		LAUNCH_PAD = register("launch_pad", LaunchPadBlockEntity::new, ModBlocks.LAUNCH_PAD);

		CABLE = register("cable", CableBlockEntity::new, ModBlocks.STEEL_CABLE, ModBlocks.DESH_CABLE);
		FLUID_PIPE = register("fluid_pipe", FluidPipeBlockEntity::new, ModBlocks.DESH_FLUID_PIPE, ModBlocks.OSTRUM_FLUID_PIPE);

		// Add custom signs to the sign block entity registry
		BlockEntityTypeAccessor signRegistry = ((BlockEntityTypeAccessor) BlockEntityType.SIGN);
		Set<Block> signBlocks = new HashSet<>(signRegistry.getBlocks());
		signBlocks.add(ModBlocks.GLACIAN_SIGN);
		signBlocks.add(ModBlocks.GLACIAN_WALL_SIGN);
		signRegistry.setBlocks(signBlocks);

		// Add custom chests to the chest block entity registry
		BlockEntityTypeAccessor chestRegistry = ((BlockEntityTypeAccessor) BlockEntityType.CHEST);
		Set<Block> chestBlocks = new HashSet<>(chestRegistry.getBlocks());
		chestBlocks.add(ModBlocks.AERONOS_CHEST);
		chestBlocks.add(ModBlocks.STROPHAR_CHEST);
		chestRegistry.setBlocks(chestBlocks);

		EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((AbstractMachineBlockEntity) blockEntity).getSideEnergyStorage(direction), SOLAR_PANEL, COAL_GENERATOR, COMPRESSOR, FUEL_REFINERY, OXYGEN_LOADER, OXYGEN_DISTRIBUTOR,
				WATER_PUMP, ENERGIZER, CRYO_FREEZER);

		FluidStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((FluidMachineBlockEntity) blockEntity).inputTank, FUEL_REFINERY, OXYGEN_LOADER, OXYGEN_DISTRIBUTOR, WATER_PUMP, CRYO_FREEZER);
	}

	public static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ModIdentifier(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
	}
}