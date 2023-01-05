package earth.terrarium.ad_astra.common.block.door;

import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SlidingDoorBlockEntity extends BlockEntity {

    private int slideTicks;
    private int previousSlideTicks;

    public SlidingDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.SLIDING_DOOR.get(), blockPos, blockState);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.slideTicks = nbt.getInt("SlideTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("SlideTicks", this.slideTicks);
    }

    public int getSlideTicks() {
        return this.slideTicks;
    }

    public int getPreviousSlideTicks() {
        return this.previousSlideTicks;
    }

    public void tick() {
        if (this.getBlockState().getValue(SlidingDoorBlock.LOCATION).equals(LocationState.BOTTOM)) {
            boolean open = this.getBlockState().getValue(SlidingDoorBlock.OPEN) || this.getBlockState().getValue(SlidingDoorBlock.POWERED);
            this.previousSlideTicks = this.slideTicks;

            if (open) {
                slideTicks += 3;
            } else {
                slideTicks -= 3;
            }

            if (level != null && !level.isClientSide) {
                if (!open && slideTicks == 94) {
                    level.playSound(null, worldPosition, ModSoundEvents.LARGE_DOOR_CLOSE.get(), SoundSource.BLOCKS, 0.25f, 1);
                } else if (open && slideTicks == 6) {
                    level.playSound(null, worldPosition, ModSoundEvents.LARGE_DOOR_OPEN.get(), SoundSource.BLOCKS, 0.25f, 1);
                }
            }
            slideTicks = Mth.clamp(slideTicks, 0, 100);
        }
    }
}
