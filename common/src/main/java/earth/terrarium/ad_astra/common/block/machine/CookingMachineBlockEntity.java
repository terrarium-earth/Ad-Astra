package earth.terrarium.ad_astra.common.block.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CookingMachineBlockEntity extends ContainerMachineBlockEntity {
    protected int cookTime;
    protected int cookTimeTotal;

    public CookingMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int containerSize) {
        super(blockEntityType, blockPos, blockState, containerSize);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, getItems());
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, getItems());
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
    }
}
