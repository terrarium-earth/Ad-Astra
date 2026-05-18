package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.TickableBlockEntity;
import earth.terrarium.adastra.common.blocks.SlidingDoorBlock;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SlidingDoorBlockEntity extends BlockEntity implements TickableBlockEntity {

    private int slideTicks;
    private int lastSlideTicks;

    public SlidingDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SLIDING_DOOR.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        slideTicks = tag.getInt("SlideTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("SlideTicks", slideTicks);
    }

    public int slideTicks() {
        return slideTicks;
    }

    public int lastSlideTicks() {
        return lastSlideTicks;
    }

    @Override
    public void tick(Level level, long time, BlockState state, BlockPos pos) {
        boolean isOpen = getBlockState().getValue(SlidingDoorBlock.OPEN) || getBlockState().getValue(SlidingDoorBlock.POWERED);
        lastSlideTicks = slideTicks;

        if (!level.isClientSide()) {
            if (!isOpen && slideTicks == 97) {
                level.playSound(null, worldPosition, ModSoundEvents.SLIDING_DOOR_CLOSE.get(), SoundSource.BLOCKS, 0.25f, 1);
            } else if (isOpen && slideTicks == 3) {
                level.playSound(null, worldPosition, ModSoundEvents.SLIDING_DOOR_OPEN.get(), SoundSource.BLOCKS, 0.25f, 1);
            }
        }
        slideTicks = Mth.clamp(slideTicks + (isOpen ? 3 : -3), 0, 100);
    }
}
