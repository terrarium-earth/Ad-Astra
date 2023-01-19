package earth.terrarium.ad_astra.datagen.provider.server;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.ad_astra.common.block.flag.FlagBlock;
import earth.terrarium.ad_astra.common.block.slidingdoor.LocationState;
import earth.terrarium.ad_astra.common.block.slidingdoor.SlidingDoorBlock;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
    }

    public static class BlockLootTables extends BlockLootSubProvider {
        public BlockLootTables() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            ModBlocks.MACHINES.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.CUBES.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.PILLARS.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.BUTTONS.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.PRESSURE_PLATES.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.FLAGS.stream().map(RegistryEntry::get).forEach(b -> add(b, (arg) -> createSinglePropConditionTable(arg, FlagBlock.HALF, DoubleBlockHalf.LOWER)));
            ModBlocks.GLOBES.stream().map(RegistryEntry::get).forEach(this::dropSelf);
            ModBlocks.SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(b -> add(b, (arg) -> createSinglePropConditionTable(arg, SlidingDoorBlock.LOCATION, LocationState.BOTTOM)));

            add(ModBlocks.ETRIUM_ORE.get(), createEtriumOreDrops(ModBlocks.ETRIUM_ORE.get()));
            dropSelf(ModBlocks.ETRIUM_CABLE.get());
            dropSelf(ModBlocks.DESMIUM_FLUID_PIPE.get());
            dropSelf(ModBlocks.CABLE_DUCT.get());
            dropSelf(ModBlocks.FLUID_PIPE_DUCT.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.stream().map(RegistryEntry::get).filter(b -> !(b instanceof LiquidBlock)).toList();
        }

        protected LootTable.Builder createEtriumOreDrops(Block block) {
            return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(ModItems.ETRIUM_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
        }
    }
}
