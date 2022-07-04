package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.blocks.flags.FlagBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.globes.GlobeBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.AbstractMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CompressorBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.EnergizerBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FuelRefineryBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.NasaWorkbenchBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenLoaderBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.WaterPumpBlockEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

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
        public static BlockEntityType<FuelRefineryBlockEntity> FUEL_REFINERY_ENTITY;
        public static BlockEntityType<CompressorBlockEntity> COMPRESSOR_ENTITY;
        public static BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_ENTITY;
        public static BlockEntityType<OxygenLoaderBlockEntity> OXYGEN_LOADER_ENTITY;
        public static BlockEntityType<SolarPanelBlockEntity> SOLAR_PANEL_ENTITY;
        public static BlockEntityType<NasaWorkbenchBlockEntity> NASA_WORKBENCH_ENTITY;
        public static BlockEntityType<OxygenDistributorBlockEntity> OXYGEN_DISTRIBUTOR_ENTITY;
        public static BlockEntityType<WaterPumpBlockEntity> WATER_PUMP_ENTITY;
        public static BlockEntityType<EnergizerBlockEntity> ENERGIZER;

        public static void register() {
                // Flag Block Entity.
                FLAG_BLOCK_ENTITY = register("flag_entity", FlagBlockEntity::new, ModBlocks.FLAG, ModBlocks.FLAG_BLUE, ModBlocks.FLAG_BROWN, ModBlocks.FLAG_CYAN, ModBlocks.FLAG_GRAY, ModBlocks.FLAG_GREEN, ModBlocks.FLAG_LIGHT_BLUE, ModBlocks.FLAG_LIME,
                                ModBlocks.FLAG_MAGENTA, ModBlocks.FLAG_ORANGE, ModBlocks.FLAG_PINK, ModBlocks.FLAG_PURPLE, ModBlocks.FLAG_RED, ModBlocks.FLAG_YELLOW);

                GLOBE_BLOCK_ENTITY = register("globe_entity", GlobeBlockEntity::new, ModBlocks.EARTH_GLOBE, ModBlocks.MOON_GLOBE, ModBlocks.MARS_GLOBE, ModBlocks.MERCURY_GLOBE, ModBlocks.VENUS_GLOBE, ModBlocks.GLACIO_GLOBE);

                // Machine Block Entity.
                FUEL_REFINERY_ENTITY = register("fuel_refinery_entity", FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY);
                COMPRESSOR_ENTITY = register("compressor_entity", CompressorBlockEntity::new, ModBlocks.COMPRESSOR);
                COAL_GENERATOR_ENTITY = register("coal_generator_entity", CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR);
                OXYGEN_LOADER_ENTITY = register("oxygen_loader_entity", OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER);
                SOLAR_PANEL_ENTITY = register("solar_panel_entity", SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL);
                NASA_WORKBENCH_ENTITY = register("nasa_workbench_entity", NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH);
                OXYGEN_DISTRIBUTOR_ENTITY = register("oxygen_distributor_entity", OxygenDistributorBlockEntity::new, ModBlocks.OXYGEN_DISTRIBUTOR);
                WATER_PUMP_ENTITY = register("water_pump_entity", WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP);
                ENERGIZER = register("energizer_entity", EnergizerBlockEntity::new, ModBlocks.ENERGIZER);

                EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((AbstractMachineBlockEntity) blockEntity).getSideEnergyStorage(direction), SOLAR_PANEL_ENTITY, COAL_GENERATOR_ENTITY, COMPRESSOR_ENTITY, FUEL_REFINERY_ENTITY,
                                OXYGEN_LOADER_ENTITY, OXYGEN_DISTRIBUTOR_ENTITY, WATER_PUMP_ENTITY, ENERGIZER);
                FluidStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((FluidMachineBlockEntity) blockEntity).inputTank, FUEL_REFINERY_ENTITY, OXYGEN_LOADER_ENTITY, OXYGEN_DISTRIBUTOR_ENTITY, WATER_PUMP_ENTITY);
        }

        public static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
                return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ModIdentifier(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
        }
}