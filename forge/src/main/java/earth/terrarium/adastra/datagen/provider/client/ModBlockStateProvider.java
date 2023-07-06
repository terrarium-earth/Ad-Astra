package earth.terrarium.adastra.datagen.provider.client;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.BatteryBlock;
import earth.terrarium.adastra.common.blocks.OxygenDistributorBlock;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        oxygenDistributorBlock((OxygenDistributorBlock) ModBlocks.OXYGEN_DISTRIBUTOR.get());
        batteryBlock((BatteryBlock) ModBlocks.BATTERY.get());
    }

    public void oxygenDistributorBlock(OxygenDistributorBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(OxygenDistributorBlock.FACING);
            AttachFace face = state.getValue(OxygenDistributorBlock.FACE);

            return ConfiguredModel.builder()
                .modelFile(cubeAll(block))
                .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                .uvLock(face == AttachFace.WALL)
                .build();
        });
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
