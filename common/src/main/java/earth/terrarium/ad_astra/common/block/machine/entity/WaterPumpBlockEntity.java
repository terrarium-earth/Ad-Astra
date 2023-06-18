package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import earth.terrarium.ad_astra.common.config.WaterPumpConfig;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModParticleTypes;
import earth.terrarium.ad_astra.common.screen.menu.WaterPumpMenu;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterPumpBlockEntity extends AbstractMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer>, BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    private WrappedBlockEnergyContainer energyContainer;
    private long waterExtracted;
    private WrappedBlockFluidContainer tank;

    public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.WATER_PUMP.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return new WaterPumpMenu(syncId, inv, this);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.waterExtracted = nbt.getLong("WaterExtracted");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putLong("WaterExtracted", this.waterExtracted);
    }

    @Override
    public void tick() {
        if (level == null) return;
        if (!level.isClientSide()) {
            BlockPos below = this.getBlockPos().below();
            FluidState water = this.level.getFluidState(below);
            if (getFluidContainer().getFluids().get(0).getFluidAmount() < getFluidContainer().getTankCapacity(0)) {
                if (water.getType() instanceof WaterFluid.Source) {

                    // Drain the water block and add it to the tank.
                    FluidHolder waterFluid = FluidHooks.newFluidHolder(Fluids.WATER, WaterPumpConfig.transferPerTick, null);
                    if (!this.getBlockState().getValue(AbstractMachineBlock.POWERED) && this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0 && getFluidContainer().insertFluid(waterFluid, true) > 0) {
                        this.setActive(true);
                        ModUtils.spawnForcedParticles((ServerLevel) this.level, ModParticleTypes.OXYGEN_BUBBLE.get(), this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() - 0.5, this.getBlockPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.01);
                        this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
                        waterExtracted += WaterPumpConfig.transferPerTick;
                        getFluidContainer().insertFluid(waterFluid, false);
                    } else {
                        this.setActive(false);
                    }

                    if (WaterPumpConfig.deleteWaterBelowWaterPump) {
                        // Delete the water block after it has been fully extracted.
                        if (waterExtracted >= FluidHooks.buckets(1f)) {
                            waterExtracted = 0;
                            BlockState blockState = level.getBlockState(below);
                            if (blockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                                level.setBlockAndUpdate(below, blockState.setValue(BlockStateProperties.WATERLOGGED, false));
                            } else {
                                level.setBlockAndUpdate(below, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            } else {
                this.setActive(false);
            }

            // Insert the fluid into nearby tanks.
            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0 && !getFluidContainer().isEmpty()) {
                FluidHolder fluid = FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), WaterPumpConfig.transferPerTick * 2, getFluidContainer().getFluids().get(0).getCompound());
                for (Direction direction : Direction.values()) {
                    if (FluidHooks.moveBlockToBlockFluid(this, direction.getOpposite(), level.getBlockEntity(worldPosition.relative(direction)), direction, fluid) > 0) {
                        this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
                        break;
                    }
                }
            }
        }
    }

    public long getEnergyPerTick() {
        return WaterPumpConfig.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(WaterPumpConfig.maxEnergy)) : this.energyContainer;
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer() {
        return tank == null ? tank = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(i -> WaterPumpConfig.tankSize, 1, (amount, fluid) -> true)) : this.tank;
    }
}