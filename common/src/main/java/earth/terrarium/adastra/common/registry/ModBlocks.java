package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.BatteryBlock;
import earth.terrarium.adastra.common.blocks.OxygenDistributorBlock;
import earth.terrarium.adastra.common.blocks.SolarPanelBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Block> MACHINES = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<Block> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new OxygenDistributorBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> BATTERY = MACHINES.register("battery", () -> new BatteryBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryEntry<Block> ETRIONIC_SOLAR_PANEL = MACHINES.register("etreonic_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 32, 100000));

    public static final RegistryEntry<Block> VESNIUM_SOLAR_PANEL = MACHINES.register("vesnium_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 128, 1000000));
}
