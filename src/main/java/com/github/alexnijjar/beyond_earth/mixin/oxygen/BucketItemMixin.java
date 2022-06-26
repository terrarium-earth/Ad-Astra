package com.github.alexnijjar.beyond_earth.mixin.oxygen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@Mixin(BucketItem.class)
@SuppressWarnings("deprecation")
public class BucketItemMixin {

    // Evaporate water in a no-oxygen environment. Water is not evaporated in a oxygen distributor.
    @Inject(at = @At(value = "HEAD"), method = "placeFluid", cancellable = true)
    public void placeFluid(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> info) {
        if (!BeyondEarth.CONFIG.mainConfig.doOxygen) {
            return;
        }
        BucketItem bucketItem = (BucketItem) (Object) this;

        if (bucketItem.fluid.isIn(FluidTags.WATER)) {

            if (!ModUtils.worldHasOxygen(world, pos)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
                for (int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0, 0.0, 0.0);
                }
                info.setReturnValue(true);
            } else if (world.getDimension().ultrawarm()) {
                info.cancel();

                boolean bl2;
                if (!(bucketItem.fluid instanceof FlowableFluid)) {
                    info.setReturnValue(false);
                }
                BlockState blockState = world.getBlockState(pos);
                Block block = blockState.getBlock();
                Material material = blockState.getMaterial();
                boolean bl = blockState.canBucketPlace(bucketItem.fluid);
                bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable) ((Object) block)).canFillWithFluid(world, pos, blockState, bucketItem.fluid);
                if (!bl2) {
                    info.setReturnValue(hitResult != null && bucketItem.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null));
                }
                if (block instanceof FluidFillable && bucketItem.fluid.equals(Fluids.WATER)) {
                    ((FluidFillable) ((Object) block)).tryFillWithFluid(world, pos, blockState, ((FlowableFluid) bucketItem.fluid).getStill(false));
                    SoundEvent soundEvent = bucketItem.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                    world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.emitGameEvent((Entity) player, GameEvent.FLUID_PLACE, pos);
                    info.setReturnValue(true);
                }
                if (!world.isClient && bl && !material.isLiquid()) {
                    world.breakBlock(pos, true);
                }
                if (world.setBlockState(pos, bucketItem.fluid.getDefaultState().getBlockState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD) || blockState.getFluidState().isStill()) {
                    SoundEvent soundEvent = bucketItem.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                    world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.emitGameEvent((Entity) player, GameEvent.FLUID_PLACE, pos);
                    info.setReturnValue(true);
                }

                info.setReturnValue(true);
            }
        }
    }
}