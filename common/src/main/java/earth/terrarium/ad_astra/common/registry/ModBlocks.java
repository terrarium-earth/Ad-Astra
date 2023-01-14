package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.fluid.CryogenicFuelLiquidBlock;
import earth.terrarium.ad_astra.common.block.machine.MachineBlock;
import earth.terrarium.ad_astra.common.block.machine.SolarPanelBlock;
import earth.terrarium.ad_astra.common.block.pipe.PipeBlock;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;


@SuppressWarnings("unused")
public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Block> STORAGE_BLOCKS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> MACHINES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SOLAR_PANELS = ResourcefulRegistries.create(MACHINES);
    public static final ResourcefulRegistry<Block> FLUIDS = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<Block> ETRIUM_CABLE = BLOCKS.register("etrium_cable", () -> new PipeBlock(1024, 0.344, PipeBlock.PipeType.CABLE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));
    public static final RegistryEntry<Block> DESMIUM_FLUID_PIPE = BLOCKS.register("desmium_fluid_pipe", () -> new PipeBlock(FluidHooks.buckets(0.05f), 0.185, PipeBlock.PipeType.FLUID_PIPE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));

    public static final RegistryEntry<Block> ETRIONIC_GENERATOR = MACHINES.register("etrionic_generator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> COMBUSTION_GENERATOR = MACHINES.register("combustion_generator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> ETRIONIC_SOLAR_PANEL = SOLAR_PANELS.register("etrionic_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 16, 10000));
    public static final RegistryEntry<Block> VESNIUM_SOLAR_PANEL = SOLAR_PANELS.register("vesnium_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 64, 1000000));
    public static final RegistryEntry<Block> GEOTHERMAL_GENERATOR = MACHINES.register("geothermal_generator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryEntry<Block> ETRIONIC_BLAST_FURNACE = MACHINES.register("etrionic_blast_furnace", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> HYDRAULIC_PRESS = MACHINES.register("hydraulic_press", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> ELECTROLYZER = MACHINES.register("electrolyzer", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OXYGEN_SENSOR = MACHINES.register("oxygen_sensor", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OIL_REFINERY = MACHINES.register("oil_refinery", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> CRYOGENIC_FREEZER = MACHINES.register("cryogenic_freezer", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> RECYCLER = MACHINES.register("recycler", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> TEMPERATURE_REGULATOR = MACHINES.register("temperature_regulator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> WATER_PUMP = MACHINES.register("water_pump", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryEntry<Block> SKY_STONE = BLOCKS.register("sky_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> ETRIUM_ORE = BLOCKS.register("etrium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE), UniformInt.of(5, 12)));

    public static final RegistryEntry<Block> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumLiquidBlock(ModFluidProperties.OXYGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> HYDROGEN = FLUIDS.register("hydrogen", () -> new BotariumLiquidBlock(ModFluidProperties.HYDROGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> CRUDE_OIL = FLUIDS.register("crude_oil", () -> new BotariumLiquidBlock(ModFluidProperties.CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> KEROSENE = FLUIDS.register("kerosene", () -> new BotariumLiquidBlock(ModFluidProperties.KEROSENE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> ETRIONIC_FUEL = FLUIDS.register("etrionic_fuel", () -> new BotariumLiquidBlock(ModFluidProperties.ETRIONIC_FUEL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> CRYOGENIC_FUEL = FLUIDS.register("cryogenic_fuel", () -> new CryogenicFuelLiquidBlock(ModFluidProperties.CRYOGENIC_FUEL, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryEntry<Block> DESMIUM_BLOCK = STORAGE_BLOCKS.register("desmium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> STEEL_BLOCK = STORAGE_BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> ETRIUM_BLOCK = STORAGE_BLOCKS.register("etrium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> AEROLYTE_BLOCK = STORAGE_BLOCKS.register("aerolyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> XEBRIUM_BLOCK = STORAGE_BLOCKS.register("xebrium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> VESNIUM_BLOCK = STORAGE_BLOCKS.register("vesnium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
}
