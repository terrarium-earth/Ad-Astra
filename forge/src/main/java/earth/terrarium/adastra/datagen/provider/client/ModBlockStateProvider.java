package earth.terrarium.adastra.datagen.provider.client;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.BatteryBlock;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        basicRenderedBlock(ModBlocks.OXYGEN_DISTRIBUTOR.get());
        basicRenderedBlock(ModBlocks.ETRIONIC_SOLAR_PANEL.get(), ModItemModelProvider.SOLAR_PANEL);
        basicRenderedBlock(ModBlocks.VESNIUM_SOLAR_PANEL.get(), ModItemModelProvider.SOLAR_PANEL);
        basicRenderedBlock(ModBlocks.HYDRAULIC_PRESS.get(), ModItemModelProvider.SMALL_RENDERED_ITEM);
        batteryBlock((BatteryBlock) ModBlocks.BATTERY.get());
    }

    public void basicRenderedBlock(Block block) {
        basicRenderedBlock(block, ModItemModelProvider.RENDERED_ITEM);
    }

    public void basicRenderedBlock(Block block, ResourceLocation itemModel) {
        simpleBlockItem(block, itemModels().getExistingFile(itemModel));
        simpleBlock(block);
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
}
