package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModParticleTypes;
import earth.terrarium.ad_astra.screen.handler.WaterPumpScreenHandler;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.fluid.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class WaterPumpBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

	private long waterExtracted;
	private SimpleUpdatingFluidContainer tank;

	public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.WATER_PUMP.get(), blockPos, blockState);
	}

	@Override
	public boolean usesEnergy() {
		return true;
	}

	@Override
	public long getCapacity() {
		return AdAstra.CONFIG.waterPump.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return AdAstra.CONFIG.waterPump.energyPerTick;
	}

	@Override
	public boolean canInsertEnergy() {
		return true;
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new WaterPumpScreenHandler(syncId, inv, this);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.waterExtracted = nbt.getLong("waterExtracted");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putLong("waterExtracted", this.waterExtracted);
	}

	@Override
	public void tick() {
		if (!this.getWorld().isClient) {
			FluidHolder waterFluid = FluidHooks.newFluidHolder(Fluids.WATER, AdAstra.CONFIG.waterPump.transferPerTick, null);
			FluidState water = this.world.getFluidState(this.getPos().down());
			if (getFluidContainer().getFluids().get(0).getFluidAmount() < getFluidContainer().getTankCapacity(0)) {
				if (water.isOf(Fluids.WATER) && water.get(FluidBlock.LEVEL) == 0) {

					// Drain the water block and add it to the tank.
					if (!this.getCachedState().get(AbstractMachineBlock.POWERED) && this.hasEnergy()) {
						this.setActive(true);
						ModUtils.spawnForcedParticles((ServerWorld) this.world, ModParticleTypes.OXYGEN_BUBBLE.get(), this.getPos().getX() + 0.5, this.getPos().getY() - 0.5, this.getPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.01);
						this.drainEnergy();
						waterExtracted += AdAstra.CONFIG.waterPump.transferPerTick;
						getFluidContainer().insertFluid(waterFluid, false);
					} else {
						this.setActive(false);
					}

					if (AdAstra.CONFIG.waterPump.deleteWaterBelowWaterPump) {
						// Delete the water block after it has been fully extracted.
						if (waterExtracted >= FluidHooks.BLOCK) {
							waterExtracted = 0;
							world.setBlockState(this.getPos().down(), Blocks.AIR.getDefaultState());
						}
					}
				}
			} else {
				this.setActive(false);
			}

			if (this.hasEnergy()) {
				// Insert the fluid into nearby tanks.
				for (Direction direction : new Direction[] { Direction.UP, this.getCachedState().get(AbstractMachineBlock.FACING) }) {
					FluidHolder fluid = FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), AdAstra.CONFIG.waterPump.transferPerTick, getFluidContainer().getFluids().get(0).getCompound());
					this.drainEnergy();
					if (FluidHooks.moveBlockToBlockFluid(this, direction.getOpposite(), world.getBlockEntity(pos.offset(direction)), direction, fluid) > 0) {
						break;
					}
				}
			}
		}
	}

	@Override
	public UpdatingFluidContainer getFluidContainer() {
		return tank == null ? tank = new SimpleUpdatingFluidContainer(this, AdAstra.CONFIG.waterPump.tankBuckets, 1, (amount, fluid) -> true) : this.tank;
	}
}