package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.screen.machine.WaterPumpMenu;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.impl.ExtractOnlyFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

@MethodsReturnNonnullByDefault
public class WaterPumpBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block, FluidAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private long waterExtracted;

    public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.WATER_PUMP.get(), blockPos, blockState, 2);
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
    public void serverTick() {
        if (level == null) return;
        if (this.getEnergyStorage().internalExtract(10, true) >= 10) {
            if (getFluidContainer().internalInsert(FluidHooks.newFluidHolder(Fluids.WATER, FluidHooks.buckets(0.01f), null), true) > 0) {
                if (level.getBlockState(this.getBlockPos().below()).is(Blocks.WATER)) {
                    this.getEnergyStorage().internalExtract(10, false);
                    waterExtracted += getFluidContainer().internalInsert(FluidHooks.newFluidHolder(Fluids.WATER, FluidHooks.buckets(0.01f), null), false);

                    if (waterExtracted >= FluidHooks.buckets(1)) {
                        level.setBlockAndUpdate(this.getBlockPos().below(), Blocks.AIR.defaultBlockState());
                        waterExtracted = 0;
                    }
                }
            }
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new WaterPumpMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(20000)) : this.energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return fluidContainer == null ? fluidContainer = new WrappedBlockFluidContainer(this, new ExtractOnlyFluidContainer(i -> FluidHooks.buckets(5f), 1, (tank, fluid) -> true)) : this.fluidContainer;
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        return getFluidContainer(this);
    }

    @Override
    public void update() {
        this.updateFluidSlots();
    }

    @Override
    public void updateFluidSlots() {
        FluidUtils.insertItemFluidToTank(this, this, 0, 1, f -> f.equals(Fluids.WATER));
        FluidUtils.extractTankFluidToItem(this, this, 0, 1, 0, f -> true);
    }
}
