package earth.terrarium.ad_astra.common.block.slidingdoor;

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
    public void load(CompoundTag tag) {
        slideTicks = tag.getInt("SlideTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("SlideTicks", slideTicks);
    }

    public int getSlideTicks() {
        return slideTicks;
    }

    public int getPreviousSlideTicks() {
        return previousSlideTicks;
    }

    public void tick() {
        if (getBlockState().getValue(SlidingDoorBlock.LOCATION) == LocationState.BOTTOM) {
            boolean open = getBlockState().getValue(SlidingDoorBlock.OPEN) || getBlockState().getValue(SlidingDoorBlock.POWERED);
            previousSlideTicks = slideTicks;

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
