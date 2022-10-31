package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.container.WaterPumpFluidTank;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModParticleTypes;
import earth.terrarium.ad_astra.screen.handler.WaterPumpScreenHandler;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import org.jetbrains.annotations.Nullable;

public class WaterPumpBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock, EnergyBlock {
    private InsertOnlyEnergyContainer energyContainer;
    private long waterExtracted;
    private WaterPumpFluidTank tank;

    public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WATER_PUMP.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new WaterPumpScreenHandler(syncId, inv, this);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.waterExtracted = nbt.getLong("waterExtracted");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putLong("waterExtracted", this.waterExtracted);
    }

    @Override
    public void tick() {
        if (!this.getLevel().isClientSide()) {
            FluidState water = this.level.getFluidState(this.getBlockPos().below());
            if (getFluidContainer().getFluids().get(0).getFluidAmount() < getFluidContainer().getTankCapacity(0)) {
                if (water.getType() instanceof WaterFluid.Source) {

                    // Drain the water block and add it to the tank.
                    if (!this.getBlockState().getValue(AbstractMachineBlock.POWERED) && this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                        this.setActive(true);
                        ModUtils.spawnForcedParticles((ServerLevel) this.level, ModParticleTypes.OXYGEN_BUBBLE.get(), this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() - 0.5, this.getBlockPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.01);
                        this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
                        waterExtracted += AdAstra.CONFIG.waterPump.transferPerTick;
                        FluidHolder waterFluid = FluidHooks.newFluidHolder(Fluids.WATER, AdAstra.CONFIG.waterPump.transferPerTick, null);
                        getFluidContainer().insertInternal(waterFluid, false);
                    } else {
                        this.setActive(false);
                    }

                    if (AdAstra.CONFIG.waterPump.deleteWaterBelowWaterPump) {
                        // Delete the water block after it has been fully extracted.
                        if (waterExtracted >= FluidHooks.buckets(1)) {
                            waterExtracted = 0;
                            level.setBlockAndUpdate(this.getBlockPos().below(), Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            } else {
                this.setActive(false);
            }

            // Insert the fluid into nearby tanks.
            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0 && !getFluidContainer().isEmpty()) {
                for (Direction direction : new Direction[]{Direction.UP, this.getBlockState().getValue(AbstractMachineBlock.FACING)}) {
                    FluidHolder fluid = FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), AdAstra.CONFIG.waterPump.transferPerTick * 2, getFluidContainer().getFluids().get(0).getCompound());
                    this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
                    if (FluidHooks.moveBlockToBlockFluid(this, direction.getOpposite(), level.getBlockEntity(worldPosition.relative(direction)), direction, fluid) > 0) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public WaterPumpFluidTank getFluidContainer() {
        return tank == null ? tank = new WaterPumpFluidTank(this) : this.tank;
    }

    public long getEnergyPerTick() {
        return AdAstra.CONFIG.waterPump.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public InsertOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new InsertOnlyEnergyContainer(this, (int) AdAstra.CONFIG.waterPump.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}