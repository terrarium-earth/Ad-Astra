package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.machines.*;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Block> MACHINES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> FLUIDS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CUBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CUBE_COLUMNS = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<Block> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new OxygenDistributorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> GRAVITY_NORMALIZER = MACHINES.register("gravity_normalizer", () -> new GravityNormalizerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> BATTERY = MACHINES.register("battery", () -> new BatteryBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ETRIONIC_BLAST_FURNACE = MACHINES.register("etreonic_blast_furnace", () -> new EtrionicBlastFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ETRIONIC_SOLAR_PANEL = MACHINES.register("etreonic_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 30, 100000));

    public static final RegistryEntry<Block> VESNIUM_SOLAR_PANEL = MACHINES.register("vesnium_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 150, 1000000));

    public static final RegistryEntry<Block> HYDRAULIC_PRESS = MACHINES.register("hydraulic_press", () -> new HydraulicPressBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> OIL_REFINERY = MACHINES.register("oil_refinery", () -> new OilRefineryBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> SEPARATOR = MACHINES.register("separator", () -> new SeparatorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> STEAM_GENERATOR = MACHINES.register("steam_generator", () -> new SteamGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ENERGY_CONTROLLER = MACHINES.register("energy_controller", () -> new EnergyControllerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ENERGY_RELAY = MACHINES.register("energy_relay", () -> new EnergyRelayBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ENERGY_INJECTOR = MACHINES.register("energy_injector", () -> new EnergyInjectorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ENERGY_RECEIVER = MACHINES.register("energy_receiver", () -> new EnergyReceiverBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> VESNIUM_COIL = MACHINES.register("vesnium_coil", () -> new VesniumCoilBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> TINKERERS_WORKBENCH = MACHINES.register("tinkerers_workbench", () -> new TinkerersWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));

    public static final RegistryEntry<Block> RECYCLER = MACHINES.register("recycler", () -> new RecyclerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> DETECTOR = MACHINES.register("detector", () -> new DetectorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumLiquidBlock(ModFluidProperties.OXYGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> HYDROGEN = FLUIDS.register("hydrogen", () -> new BotariumLiquidBlock(ModFluidProperties.HYDROGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> OIL = FLUIDS.register("oil", () -> new BotariumLiquidBlock(ModFluidProperties.OIL, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryEntry<Block> MOON_SAND = CUBES.register("moon_sand", () -> new SandBlock(0x5c6466, BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryEntry<Block> MOON_STONE = CUBES.register("moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_DEEPSLATE = CUBE_COLUMNS.register("moon_deepslate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));

    public static final RegistryEntry<Block> MARS_SAND = CUBES.register("mars_sand", () -> new SandBlock(0xc28152, BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryEntry<Block> MARS_STONE = CUBES.register("mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> VENUS_SAND = CUBES.register("venus_sand", () -> new SandBlock(0xd4a85e, BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryEntry<Block> VENUS_STONE = CUBES.register("venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> MERCURY_STONE = CUBES.register("mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
}
