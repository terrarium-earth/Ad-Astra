package earth.terrarium.adastra.common.blockentities;

import dev.architectury.injectables.annotations.PlatformOnly;
import earth.terrarium.adastra.common.blocks.SlidingDoorBlock;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SlidingDoorBlockEntity extends BlockEntity {
    private int slideTicks;
    private int lastSlideTicks;

    public SlidingDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SLIDING_DOOR.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        slideTicks = tag.getInt("SlideTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("SlideTicks", slideTicks);
    }

    public int slideTicks() {
        return slideTicks;
    }

    public int lastSlideTicks() {
        return lastSlideTicks;
    }

    public void tick() {
        boolean isOpen = getBlockState().getValue(SlidingDoorBlock.OPEN) || getBlockState().getValue(SlidingDoorBlock.POWERED);
        lastSlideTicks = slideTicks;

        if (level != null && !level.isClientSide()) {
            if (!isOpen && slideTicks == 97) {
                level.playSound(null, worldPosition, ModSoundEvents.SLIDING_DOOR_CLOSE.get(), SoundSource.BLOCKS, 0.25f, 1);
            } else if (isOpen && slideTicks == 3) {
                level.playSound(null, worldPosition, ModSoundEvents.SLIDING_DOOR_OPEN.get(), SoundSource.BLOCKS, 0.25f, 1);
            }
        }
        slideTicks = Mth.clamp(slideTicks + (isOpen ? 3 : -3), 0, 100);
    }

    @PlatformOnly("forge")
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox() {
        return new AABB(this.getBlockPos()).inflate(3);
    }
}
