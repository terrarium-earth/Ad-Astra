package net.mrscauthd.boss_tools.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;

import javax.annotation.Nullable;
import java.util.Random;

public class FuelFluid extends FlowingFluid {
	public Fluid getFlowingFluid() {
		return ModInnet.FLOWING_FUEL.get();
	}

	public Fluid getStillFluid() {
		return ModInnet.FUEL_STILL.get();
	}

	public Item getFilledBucket() {
		return ModInnet.FUEL_BUCKET.get();
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(World worldIn, BlockPos pos, FluidState state, Random random) {
		if (!state.isSource() && !state.get(FALLING)) {
			if (random.nextInt(64) == 0) {
				worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
			}
		} else if (random.nextInt(10) == 0) {
			worldIn.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}

	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public IParticleData getDripParticleData() {
		return ParticleTypes.DRIPPING_WATER;
	}

	protected boolean canSourcesMultiply() {
		return false;
	}

	protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
		TileEntity tileentity = state.hasTileEntity() ? worldIn.getTileEntity(pos) : null;
		Block.spawnDrops(state, worldIn, pos, tileentity);
	}

	public int getSlopeFindDistance(IWorldReader worldIn) {
		return 4;
	}

	public BlockState getBlockState(FluidState state) {
		return ModInnet.FUEL_BLOCK.get().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
	}

	@Override
	public boolean isSource(FluidState state) {
		return false;
	}

	@Override
	public int getLevel(FluidState state) {
		return 4;
	}

	public boolean isEquivalentTo(Fluid fluidIn) {
		return fluidIn == ModInnet.FUEL_STILL.get() || fluidIn == ModInnet.FLOWING_FUEL.get();
	}

	public int getLevelDecreasePerBlock(IWorldReader worldIn) {
		return 1;
	}

	public int getTickRate(IWorldReader p_205569_1_) {
		return 8;
	}

	public boolean canDisplace(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction) {
		return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
	}

	protected float getExplosionResistance() {
		return 100.0F;
	}

	@Override
	protected FluidAttributes createAttributes() {
		return net.minecraftforge.fluids.FluidAttributes.builder(
			new ResourceLocation(BossToolsMod.ModId,"blocks/fluid_fuel_still"),
			new ResourceLocation(BossToolsMod.ModId,"blocks/fluid_fuel_flow"))
				.overlay(new ResourceLocation(BossToolsMod.ModId,"blocks/fluid_fuel_still"))
				.translationKey("block." + BossToolsMod.ModId + ".fuel")
				.sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
				.build(this);
	}

	public static class Flowing extends FuelFluid {
		protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		public int getLevel(FluidState state) {
			return state.get(LEVEL_1_8);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}

	public static class Source extends FuelFluid {
		public int getLevel(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}
}