package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.config.CryoFreezerConfig;
import earth.terrarium.ad_astra.common.recipe.CryoFuelConversionRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.screen.menu.CryoFreezerMenu;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.ExtractOnlyFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CryoFreezerBlockEntity extends AbstractMachineBlockEntity implements FluidAttachment.Block, EnergyAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;

    protected int cookTime;
    protected int cookTimeTotal;
    @Nullable
    protected ItemStack inputItem;
    @Nullable
    protected Fluid outputFluid;
    private WrappedBlockFluidContainer tank;

    public CryoFreezerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.CRYO_FREEZER.get(), blockPos, blockState);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("CookTime", this.cookTime);
        nbt.putInt("CookTimeTotal", this.cookTimeTotal);
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getCookTimeTotal() {
        return this.cookTimeTotal;
    }

    public void finishCooking() {
        if (this.outputFluid != null) {
            CryoFuelConversionRecipe recipe = this.createRecipe(this.getItem(0), false);
            if (recipe != null) {
                FluidHolder outputFluid = FluidHooks.newFluidHolder(recipe.getFluidOutput(), (long) (FluidHooks.buckets(1f) * recipe.getConversionRatio()), null);
                getFluidContainer(this).internalInsert(outputFluid, false);
            }
        }
        this.stopCooking();
    }

    public void stopCooking() {
        this.cookTime = 0;
        this.cookTimeTotal = 0;
        this.outputFluid = null;
        this.inputItem = null;
        this.setChanged();
    }

    @Override
    public int getInventorySize() {
        return 3;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 2;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return new CryoFreezerMenu(syncId, inv, this);
    }

    public CryoFuelConversionRecipe createRecipe(ItemStack testStack, boolean checkOutput) {
        stopCooking();

        CryoFuelConversionRecipe recipe = CryoFuelConversionRecipe.findFirst(this.level, f -> f.test(testStack));

        if (recipe != null) {

            // Stop if something is already in the output.
            if (checkOutput) {
                ItemStack outputSlot = this.getItem(1);
                ItemStack output = recipe.getResultItem();
                if (!outputSlot.isEmpty() && !outputSlot.getItem().equals(recipe.getResultItem().getItem()) || outputSlot.getCount() + output.getCount() > outputSlot.getMaxStackSize()) {
                    return null;
                }
            }

            this.outputFluid = recipe.getFluidOutput();
            this.inputItem = testStack;
        }

        return recipe;
    }

    @Override
    public void tick() {
        if (!this.getLevel().isClientSide) {
            ItemStack input = this.getItem(0);
            ItemStack outputInsertSlot = this.getItem(1);
            ItemStack outputExtractSlot = this.getItem(2);

            if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxStackSize()) {
                FluidUtils.extractTankFluidToItem(this.getFluidContainer(this), this, 1, 2, 0, f -> true);
            }

            if (this.getEnergyStorage(this).internalExtract(this.getEnergyPerTick(), true) > 0) {
                if ((!input.isEmpty() && (input.equals(this.inputItem) || this.inputItem == null)) && getFluidContainer(this).getFluids().get(0).getFluidAmount() < getFluidContainer(this).getTankCapacity(0)) {
                    if (this.cookTime < this.cookTimeTotal) {
                        this.cookTime++;
                        this.getEnergyStorage(this).internalExtract(this.getEnergyPerTick(), false);
                        this.setActive(true);

                    } else if (this.outputFluid != null) {
                        this.finishCooking();
                        input.shrink(1);
                    } else {
                        CryoFuelConversionRecipe recipe = this.createRecipe(input, false);
                        if (recipe != null) {
                            this.cookTimeTotal = 25;
                            this.cookTime = 0;
                        }
                    }
                } else if (this.outputFluid != null) {
                    this.stopCooking();
                    this.setActive(false);
                } else {
                    this.setActive(false);
                }
            }
        }
    }

    public long getEnergyPerTick() {
        return CryoFreezerConfig.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage(this).getMaxCapacity();
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(CryoFreezerConfig.maxEnergy)) : this.energyContainer;
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return tank == null ? tank = new WrappedBlockFluidContainer(this, new ExtractOnlyFluidContainer(i -> CryoFreezerConfig.tankSize, 1, (amount, fluid) -> true)) : this.tank;
    }
}