package earth.terrarium.adastra.datagen.provider.client;


import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.BatteryBlock;
import earth.terrarium.adastra.common.blocks.EtrionicBlastFurnaceBlock;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.FLUIDS.stream().map(RegistryEntry::get).forEach(this::fluidBlock);

        ModBlocks.CUBES.stream().map(RegistryEntry::get).forEach(this::basicBlock);

        basicRenderedBlock(ModBlocks.OXYGEN_DISTRIBUTOR.get());
        basicRenderedBlock(ModBlocks.GRAVITY_NORMALIZER.get());
        basicRenderedBlock(ModBlocks.ETRIONIC_SOLAR_PANEL.get(), ModItemModelProvider.SOLAR_PANEL);
        basicRenderedBlock(ModBlocks.VESNIUM_SOLAR_PANEL.get(), ModItemModelProvider.SOLAR_PANEL);
        basicRenderedBlock(ModBlocks.HYDRAULIC_PRESS.get(), ModItemModelProvider.SMALL_RENDERED_ITEM);
        basicRenderedBlock(ModBlocks.OIL_REFINERY.get(), ModItemModelProvider.SMALL_RENDERED_ITEM);
        basicRenderedBlock(ModBlocks.STEAM_GENERATOR.get(), ModItemModelProvider.SMALL_RENDERED_ITEM, modLoc("block/steam_generator/steam_generator"));
        basicRenderedBlock(ModBlocks.EMITTER.get());
        basicRenderedBlock(ModBlocks.RECEIVER.get());
        basicRenderedBlock(ModBlocks.VESNIUM_COIL.get(), ModItemModelProvider.SMALL_RENDERED_ITEM);
        basicRenderedBlock(ModBlocks.TINKERERS_WORKBENCH.get(), ModItemModelProvider.SMALL_RENDERED_ITEM);
        basicRenderedBlock(ModBlocks.RECYCLER.get(), ModItemModelProvider.SMALL_RENDERED_ITEM);
        batteryBlock((BatteryBlock) ModBlocks.BATTERY.get());
        etrionicBlastFurnaceBlock((EtrionicBlastFurnaceBlock) ModBlocks.ETRIONIC_BLAST_FURNACE.get());
    }

    public void basicBlock(Block block) {
        simpleBlockItem(block, models().getBuilder(name(block)));
        simpleBlock(block);
    }

    public void basicRenderedBlock(Block block) {
        basicRenderedBlock(block, ModItemModelProvider.RENDERED_ITEM);
    }

    public void basicRenderedBlock(Block block, ResourceLocation itemModel) {
        simpleBlockItem(block, itemModels().getExistingFile(itemModel));
        simpleBlock(block);
    }

    public void basicRenderedBlock(Block block, ResourceLocation itemModel, ResourceLocation texture) {
        simpleBlockItem(block, itemModels().getExistingFile(itemModel));
        simpleBlock(block, this.models().cubeAll(this.name(block), texture));
    }

    public void batteryBlock(BatteryBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BatteryBlock.FACING);
            int charge = state.getValue(BatteryBlock.CHARGE);

            simpleBlockItem(block, models().getBuilder("battery_100"));
            return ConfiguredModel.builder()
                .modelFile(models().getBuilder("battery_%s".formatted(charge * 25))
                    .texture("0", modLoc("block/battery/battery_%s".formatted(charge * 25)))
                    .texture("particle", modLoc("block/battery/battery_%s".formatted(charge * 25)))
                    .parent(models().getExistingFile(modLoc("block/battery"))))
                .rotationY((int) facing.toYRot())
                .build();
        });
    }

    public void etrionicBlastFurnaceBlock(EtrionicBlastFurnaceBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BatteryBlock.FACING);
            boolean lit = state.getValue(EtrionicBlastFurnaceBlock.LIT);
            ResourceLocation texture = lit ? modLoc("block/etrionic_blast_furnace/etrionic_blast_furnace_on") : modLoc("block/etrionic_blast_furnace/etrionic_blast_furnace");

            simpleBlockItem(block, models().getBuilder("block/etrionic_blast_furnace_on"));
            return ConfiguredModel.builder()
                .modelFile(models().getBuilder("etrionic_blast_furnace" + (lit ? "_on" : ""))
                    .texture("0", texture)
                    .texture("particle", texture)
                    .parent(models().getExistingFile(modLoc("block/etrionic_blast_furnace_base"))))
                .rotationY((int) facing.toYRot())
                .build();
        });
    }

    private void fluidBlock(Block block) {
        simpleBlock(block, models().getBuilder(name(block)).texture("particle", WATER_STILL.toString()));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }
}
