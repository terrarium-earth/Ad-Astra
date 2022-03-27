package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.blocks.launch_pad.RocketLaunchPadEntity;
import net.mrscauthd.beyond_earth.blocks.flags.FlagBlockEntity;
import net.mrscauthd.beyond_earth.blocks.machines.entity.*;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {

    // Flag Block Entity.
    public static BlockEntityType<FlagBlockEntity> FLAG_BLOCK_ENTITY;

    public static BlockEntityType<RocketLaunchPadEntity> ROCKET_LAUNCH_PAD_ENTITY;

    // Machine Block Entity.
    public static BlockEntityType<FuelRefineryBlockEntity> FUEL_REFINERY_ENTITY;
    public static BlockEntityType<CompressorBlockEntity> COMPRESSOR_ENTITY;
    public static BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_ENTITY;
    public static BlockEntityType<OxygenLoaderBlockEntity> OXYGEN_LOADER_ENTITY;
    public static BlockEntityType<SolarPanelBlockEntity> SOLAR_PANEL_ENTITY;
    public static BlockEntityType<NasaWorkbenchBlockEntity> NASA_WORKBENCH_ENTITY;
    public static BlockEntityType<OxygenBubbleDistributorBlockEntity> OXYGEN_BUBBLE_DISTRIBUTOR_ENTITY;
    public static BlockEntityType<WaterPumpBlockEntity> WATER_PUMP_ENTITY;


    public static void register() {
        // Flag Block Entity.
        FLAG_BLOCK_ENTITY = register("flag_entity", FlagBlockEntity::new,
                ModBlocks.FLAG_BLOCK,
                ModBlocks.FLAG_BLUE_BLOCK,
                ModBlocks.FLAG_BROWN_BLOCK,
                ModBlocks.FLAG_CYAN_BLOCK,
                ModBlocks.FLAG_GRAY_BLOCK,
                ModBlocks.FLAG_GREEN_BLOCK,
                ModBlocks.FLAG_LIGHT_BLUE_BLOCK,
                ModBlocks.FLAG_LIME_BLOCK,
                ModBlocks.FLAG_MAGENTA_BLOCK,
                ModBlocks.FLAG_ORANGE_BLOCK,
                ModBlocks.FLAG_PINK_BLOCK,
                ModBlocks.FLAG_PURPLE_BLOCK,
                ModBlocks.FLAG_RED_BLOCK,
                ModBlocks.FLAG_YELLOW_BLOCK
        );

        // Rocket Launch Pad.
        ROCKET_LAUNCH_PAD_ENTITY = register("rocket_launch_pad_entity", RocketLaunchPadEntity::new, ModBlocks.ROCKET_LAUNCH_PAD);

        // Machine Block Entity.
        FUEL_REFINERY_ENTITY = register("fuel_refinery_entity", FuelRefineryBlockEntity::new, ModBlocks.FUEL_REFINERY);
        COMPRESSOR_ENTITY = register("compressor_entity", CompressorBlockEntity::new, ModBlocks.COMPRESSOR);
        COAL_GENERATOR_ENTITY = register("coal_generator_entity", CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR);
        OXYGEN_LOADER_ENTITY = register("oxygen_loader_entity", OxygenLoaderBlockEntity::new, ModBlocks.OXYGEN_LOADER);
        SOLAR_PANEL_ENTITY = register("solar_panel_entity", SolarPanelBlockEntity::new, ModBlocks.SOLAR_PANEL);
        NASA_WORKBENCH_ENTITY = register("nasa_workbench_entity", NasaWorkbenchBlockEntity::new, ModBlocks.NASA_WORKBENCH);
        OXYGEN_BUBBLE_DISTRIBUTOR_ENTITY = register("oxygen_bubble_distributor_entity", OxygenBubbleDistributorBlockEntity::new, ModBlocks.OXYGEN_BUBBLE_DISTRIBUTOR);
        WATER_PUMP_ENTITY = register("water_pump_entity", WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP);

        EnergyStorage.SIDED.registerForBlockEntities((blockEntity, direction) -> ((AbstractMachineBlockEntity) blockEntity).getSideEnergyStorage(direction),
                SOLAR_PANEL_ENTITY,
                COAL_GENERATOR_ENTITY
        );
    }


    public static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ModIdentifier(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}
