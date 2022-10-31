package earth.terrarium.ad_astra.datagen;

import earth.terrarium.ad_astra.blocks.door.LocationState;
import earth.terrarium.ad_astra.blocks.door.SlidingDoorBlock;
import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;

class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    ModBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {

        ModBlocks.BLOCKS.iterator().forEachRemaining(block -> this.dropSelf(block.get()));


        this.dropOther(ModBlocks.WALL_EXTINGUISHED_TORCH.get(), ModItems.EXTINGUISHED_TORCH.get());
        this.dropOther(ModBlocks.GLACIAN_WALL_SIGN.get(), ModItems.GLACIAN_SIGN.get());
        this.add(ModBlocks.STEEL_DOOR.get(), BlockLoot::createDoorTable);
        this.add(ModBlocks.GLACIAN_DOOR.get(), BlockLoot::createDoorTable);

        this.add(ModBlocks.LAUNCH_PAD.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.LAUNCH_PAD.get(), SlidingDoorBlock.LOCATION, LocationState.CENTER));

        this.add(ModBlocks.IRON_SLIDING_DOOR.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.IRON_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
        this.add(ModBlocks.STEEL_SLIDING_DOOR.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.STEEL_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
        this.add(ModBlocks.DESH_SLIDING_DOOR.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.DESH_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
        this.add(ModBlocks.OSTRUM_SLIDING_DOOR.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.OSTRUM_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
        this.add(ModBlocks.CALORITE_SLIDING_DOOR.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.CALORITE_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
        this.add(ModBlocks.AIRLOCK.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.AIRLOCK.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
        this.add(ModBlocks.REINFORCED_DOOR.get(), BlockLoot.createSinglePropConditionTable(ModBlocks.REINFORCED_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));

        this.add(ModBlocks.MOON_STONE.get(), block -> BlockLoot.createSingleItemTableWithSilkTouch(block, ModBlocks.MOON_COBBLESTONE.get()));
        this.add(ModBlocks.MARS_STONE.get(), block -> BlockLoot.createSingleItemTableWithSilkTouch(block, ModBlocks.MARS_COBBLESTONE.get()));
        this.add(ModBlocks.VENUS_STONE.get(), block -> BlockLoot.createSingleItemTableWithSilkTouch(block, ModBlocks.VENUS_COBBLESTONE.get()));
        this.add(ModBlocks.MERCURY_STONE.get(), block -> BlockLoot.createSingleItemTableWithSilkTouch(block, ModBlocks.MERCURY_COBBLESTONE.get()));
        this.add(ModBlocks.GLACIO_STONE.get(), block -> BlockLoot.createSingleItemTableWithSilkTouch(block, ModBlocks.GLACIO_COBBLESTONE.get()));

        this.add(ModBlocks.MOON_CHEESE_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.CHEESE.get()));
        this.add(ModBlocks.MOON_DESH_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.RAW_DESH.get()));
        this.add(ModBlocks.MOON_IRON_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.RAW_IRON));
        this.add(ModBlocks.MOON_ICE_SHARD_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.ICE_SHARD.get()));
        this.add(ModBlocks.MARS_IRON_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.RAW_IRON));
        this.add(ModBlocks.MARS_ICE_SHARD_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.ICE_SHARD.get()));
        this.add(ModBlocks.MARS_DIAMOND_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.DIAMOND));
        this.add(ModBlocks.MARS_OSTRUM_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.RAW_OSTRUM.get()));
        this.add(ModBlocks.MARS_ICE_SHARD_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.ICE_SHARD.get()));
        this.add(ModBlocks.MERCURY_IRON_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.RAW_IRON));
        this.add(ModBlocks.VENUS_COAL_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.COAL));
        this.add(ModBlocks.VENUS_GOLD_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.RAW_GOLD));
        this.add(ModBlocks.VENUS_DIAMOND_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.DIAMOND));
        this.add(ModBlocks.VENUS_CALORITE_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.RAW_CALORITE.get()));
        this.add(ModBlocks.GLACIO_ICE_SHARD_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.ICE_SHARD.get()));
        this.add(ModBlocks.GLACIO_COAL_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.COAL));
        this.add(ModBlocks.GLACIO_COPPER_ORE.get(), BlockLoot::createCopperOreDrops);
        this.add(ModBlocks.GLACIO_IRON_ORE.get(), block -> BlockLoot.createOreDrop(block, Items.RAW_IRON));
        this.add(ModBlocks.GLACIO_LAPIS_ORE.get(), BlockLoot::createLapisOreDrops);
        this.add(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.ICE_SHARD.get()));
        this.add(ModBlocks.DEEPSLATE_DESH_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.RAW_DESH.get()));
        this.add(ModBlocks.DEEPSLATE_OSTRUM_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.RAW_OSTRUM.get()));
        this.add(ModBlocks.DEEPSLATE_CALORITE_ORE.get(), block -> BlockLoot.createOreDrop(block, ModItems.RAW_CALORITE.get()));
    }
}