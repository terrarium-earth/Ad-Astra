package earth.terrarium.adastra.datagen.provider.server;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.common.blocks.FlagBlock;
import earth.terrarium.adastra.common.blocks.LaunchPadBlock;
import earth.terrarium.adastra.common.blocks.SlidingDoorBlock;
import earth.terrarium.adastra.common.blocks.properties.LaunchPadPartProperty;
import earth.terrarium.adastra.common.blocks.properties.SlidingDoorPartProperty;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;


public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    private static final Set<Supplier<Block>> DOESNT_DROP_SELF = Set.of(
        ModBlocks.MOON_CHEESE_ORE,
        ModBlocks.MOON_DESH_ORE,
        ModBlocks.DEEPSLATE_DESH_ORE,
        ModBlocks.MOON_IRON_ORE,
        ModBlocks.MOON_ICE_SHARD_ORE,
        ModBlocks.DEEPSLATE_ICE_SHARD_ORE,
        ModBlocks.MARS_IRON_ORE,
        ModBlocks.MARS_DIAMOND_ORE,
        ModBlocks.MARS_OSTRUM_ORE,
        ModBlocks.DEEPSLATE_OSTRUM_ORE,
        ModBlocks.MARS_ICE_SHARD_ORE,
        ModBlocks.MERCURY_IRON_ORE,
        ModBlocks.VENUS_COAL_ORE,
        ModBlocks.VENUS_GOLD_ORE,
        ModBlocks.VENUS_DIAMOND_ORE,
        ModBlocks.VENUS_CALORITE_ORE,
        ModBlocks.DEEPSLATE_CALORITE_ORE,
        ModBlocks.GLACIO_ICE_SHARD_ORE,
        ModBlocks.GLACIO_COAL_ORE,
        ModBlocks.GLACIO_COPPER_ORE,
        ModBlocks.GLACIO_IRON_ORE,
        ModBlocks.GLACIO_LAPIS_ORE,
        ModBlocks.AERONOS_DOOR,
        ModBlocks.STROPHAR_DOOR,
        ModBlocks.GLACIAN_DOOR,
        ModBlocks.STEEL_DOOR,
        ModBlocks.MOON_STONE,
        ModBlocks.MARS_STONE,
        ModBlocks.VENUS_STONE,
        ModBlocks.MERCURY_STONE,
        ModBlocks.GLACIO_STONE
    );

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
    }

    public static class BlockLootTables extends BlockLootSubProvider {
        public BlockLootTables() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            ModBlocks.BLOCKS.stream()
                .filter(b -> !DOESNT_DROP_SELF.contains(b)
                    && !(b.get() instanceof LiquidBlock)
                    && !(b.get() instanceof FlagBlock)
                    && !(b.get() instanceof SlidingDoorBlock))
                .map(RegistryEntry::get)
                .forEach(this::dropSelf);

            add(ModBlocks.MOON_CHEESE_ORE.get(), createOreDrop(ModBlocks.MOON_CHEESE_ORE.get(), ModItems.CHEESE.get()));
            add(ModBlocks.MOON_DESH_ORE.get(), createOreDrop(ModBlocks.MOON_DESH_ORE.get(), ModItems.RAW_DESH.get()));
            add(ModBlocks.DEEPSLATE_DESH_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_DESH_ORE.get(), ModItems.RAW_DESH.get()));
            add(ModBlocks.MOON_IRON_ORE.get(), createOreDrop(ModBlocks.MOON_IRON_ORE.get(), Items.RAW_IRON));
            add(ModBlocks.MOON_ICE_SHARD_ORE.get(), createOreDrop(ModBlocks.MOON_ICE_SHARD_ORE.get(), ModItems.ICE_SHARD.get()));
            add(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(), ModItems.ICE_SHARD.get()));
            add(ModBlocks.MARS_IRON_ORE.get(), createOreDrop(ModBlocks.MARS_IRON_ORE.get(), Items.RAW_IRON));
            add(ModBlocks.MARS_DIAMOND_ORE.get(), createOreDrop(ModBlocks.MARS_DIAMOND_ORE.get(), Items.DIAMOND));
            add(ModBlocks.MARS_OSTRUM_ORE.get(), createOreDrop(ModBlocks.MARS_OSTRUM_ORE.get(), ModItems.RAW_OSTRUM.get()));
            add(ModBlocks.DEEPSLATE_OSTRUM_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_OSTRUM_ORE.get(), ModItems.RAW_OSTRUM.get()));
            add(ModBlocks.MARS_ICE_SHARD_ORE.get(), createOreDrop(ModBlocks.MARS_ICE_SHARD_ORE.get(), ModItems.ICE_SHARD.get()));
            add(ModBlocks.MERCURY_IRON_ORE.get(), createOreDrop(ModBlocks.MERCURY_IRON_ORE.get(), Items.RAW_IRON));
            add(ModBlocks.VENUS_COAL_ORE.get(), createOreDrop(ModBlocks.VENUS_COAL_ORE.get(), Items.COAL));
            add(ModBlocks.VENUS_GOLD_ORE.get(), createOreDrop(ModBlocks.VENUS_GOLD_ORE.get(), Items.RAW_GOLD));
            add(ModBlocks.VENUS_DIAMOND_ORE.get(), createOreDrop(ModBlocks.VENUS_DIAMOND_ORE.get(), Items.DIAMOND));
            add(ModBlocks.VENUS_CALORITE_ORE.get(), createOreDrop(ModBlocks.VENUS_CALORITE_ORE.get(), ModItems.RAW_CALORITE.get()));
            add(ModBlocks.DEEPSLATE_CALORITE_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_CALORITE_ORE.get(), ModItems.RAW_CALORITE.get()));
            add(ModBlocks.GLACIO_ICE_SHARD_ORE.get(), createOreDrop(ModBlocks.GLACIO_ICE_SHARD_ORE.get(), ModItems.ICE_SHARD.get()));
            add(ModBlocks.GLACIO_COAL_ORE.get(), createOreDrop(ModBlocks.GLACIO_COAL_ORE.get(), Items.COAL));
            add(ModBlocks.GLACIO_COPPER_ORE.get(), createCopperOreDrops(ModBlocks.GLACIO_COPPER_ORE.get()));
            add(ModBlocks.GLACIO_IRON_ORE.get(), createOreDrop(ModBlocks.GLACIO_IRON_ORE.get(), Items.RAW_IRON));
            add(ModBlocks.GLACIO_LAPIS_ORE.get(), createLapisOreDrops(ModBlocks.GLACIO_LAPIS_ORE.get()));

            add(ModBlocks.AERONOS_DOOR.get(), createDoorTable(ModBlocks.AERONOS_DOOR.get()));
            add(ModBlocks.STROPHAR_DOOR.get(), createDoorTable(ModBlocks.STROPHAR_DOOR.get()));
            add(ModBlocks.GLACIAN_DOOR.get(), createDoorTable(ModBlocks.GLACIAN_DOOR.get()));
            add(ModBlocks.STEEL_DOOR.get(), createDoorTable(ModBlocks.STEEL_DOOR.get()));

            add(ModBlocks.MOON_STONE.get(), b -> createSingleItemTableWithSilkTouch(b, ModItems.MOON_COBBLESTONE.get()));
            add(ModBlocks.MARS_STONE.get(), b -> createSingleItemTableWithSilkTouch(b, ModItems.MARS_COBBLESTONE.get()));
            add(ModBlocks.VENUS_STONE.get(), b -> createSingleItemTableWithSilkTouch(b, ModItems.VENUS_COBBLESTONE.get()));
            add(ModBlocks.MERCURY_STONE.get(), b -> createSingleItemTableWithSilkTouch(b, ModItems.MERCURY_COBBLESTONE.get()));
            add(ModBlocks.GLACIO_STONE.get(), b -> createSingleItemTableWithSilkTouch(b, ModItems.GLACIO_COBBLESTONE.get()));

            ModBlocks.FLAGS.stream().forEach(b -> add(b.get(), b2 ->
                createSinglePropConditionTable(b2, FlagBlock.HALF, DoubleBlockHalf.LOWER)));

            ModBlocks.SLIDING_DOORS.stream().forEach(b -> add(b.get(), b2 ->
                createSinglePropConditionTable(b2, SlidingDoorBlock.PART, SlidingDoorPartProperty.BOTTOM)));

            add(ModBlocks.LAUNCH_PAD.get(), b ->
                createSinglePropConditionTable(b, LaunchPadBlock.PART, LaunchPadPartProperty.CENTER));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.stream().map(RegistryEntry::get).filter(b -> !(b instanceof LiquidBlock)).toList();
        }
    }
}
