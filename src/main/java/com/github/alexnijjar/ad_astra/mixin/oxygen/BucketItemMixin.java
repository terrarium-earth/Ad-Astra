package com.github.alexnijjar.ad_astra.mixin.oxygen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.github.alexnijjar.ad_astra.util.entity.OxygenUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@Mixin(BucketItem.class)
@SuppressWarnings("deprecation")
public abstract class BucketItemMixin {

	@Shadow
	private Fluid fluid;

	// Evaporate water in a no-oxygen environment. Water is not evaporated in a oxygen distributor.
	@Inject(method = "placeFluid", at = @At(value = "HEAD"), cancellable = true)
	public void adastra_placeFluid(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> ci) {
		if (!AdAstra.CONFIG.general.doOxygen) {
			return;
		}

		if (!ModUtils.isSpaceWorld(world)) {
			return;
		}

		BucketItem bucketItem = (BucketItem) (Object) this;

		if (!OxygenUtils.posHasOxygen(world, pos) && !this.fluid.equals(ModFluids.CRYO_FUEL_STILL)) {
			int i = pos.getX();
			int j = pos.getY();
			int k = pos.getZ();
			if (ModUtils.getWorldTemperature(world) < 0) {
				BlockState state = world.getBlockState(pos);
				if (state.isAir()) {
					world.setBlockState(pos, Blocks.ICE.getDefaultState());
				}

			}
			world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
			for (int l = 0; l < 8; ++l) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0, 0.0, 0.0);
			}
			ci.setReturnValue(true);
		} else if (world.getDimension().isUltrawarm()) {
			ci.cancel();

			boolean bl2;
			if (!(this.fluid instanceof FlowableFluid)) {
				ci.setReturnValue(false);
			}
			BlockState blockState = world.getBlockState(pos);
			Block block = blockState.getBlock();
			Material material = blockState.getMaterial();
			boolean bl = blockState.canBucketPlace(this.fluid);
			bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable) block).canFillWithFluid(world, pos, blockState, this.fluid);
			if (!bl2) {
				ci.setReturnValue(hitResult != null && bucketItem.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null));
			}
			if (block instanceof FluidFillable && this.fluid.equals(Fluids.WATER)) {
				((FluidFillable) block).tryFillWithFluid(world, pos, blockState, ((FlowableFluid) this.fluid).getStill(false));
				SoundEvent soundEvent = this.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
				world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
				ci.setReturnValue(true);
			}
			if (!world.isClient && bl && !material.isLiquid()) {
				world.breakBlock(pos, true);
			}
			if (!blockState.contains(Properties.WATERLOGGED)) {
				if (world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) || blockState.getFluidState().isStill()) {
					SoundEvent soundEvent = this.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
					world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
					world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
					ci.setReturnValue(true);
				}
			}

			ci.setReturnValue(true);
		}
	}
}
