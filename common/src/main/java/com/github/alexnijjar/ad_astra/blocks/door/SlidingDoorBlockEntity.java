package com.github.alexnijjar.ad_astra.blocks.door;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModSoundEvents;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class SlidingDoorBlockEntity extends BlockEntity {

    private int slideTicks;
    private int previousSlideTicks;

    private BlockState mainState;

    public SlidingDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SLIDING_DOOR.get(), blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.slideTicks = nbt.getInt("slideTicks");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("slideTicks", this.slideTicks);
    }

    public int getSlideTicks() {
        return this.slideTicks;
    }

    public int getPreviousSlideTicks() {
        return this.previousSlideTicks;
    }

    public BlockState getMainState() {
        return this.mainState;
    }

    public void setMainState(BlockState mainState) {
        this.mainState = mainState;
    }

    public void tick() {
        if (this.getCachedState().get(SlidingDoorBlock.LOCATION).equals(LocationState.BOTTOM)) {
            boolean open = this.getCachedState().get(SlidingDoorBlock.OPEN) || this.getCachedState().get(SlidingDoorBlock.POWERED);
            this.previousSlideTicks = this.slideTicks;

            if (open) {
                slideTicks += 3;
            } else {
                slideTicks -= 3;
            }

            if (!world.isClient) {
                if (!open && slideTicks == 94) {
                    world.playSound(null, pos, ModSoundEvents.LARGE_DOOR_CLOSE.get(), SoundCategory.BLOCKS, 0.3f, 1);
                } else if (open && slideTicks == 6) {
                    world.playSound(null, pos, ModSoundEvents.LARGE_DOOR_OPEN.get(), SoundCategory.BLOCKS, 0.3f, 1);
                }
            }
            slideTicks = MathHelper.clamp(slideTicks, 0, 100);
        }
    }
}
