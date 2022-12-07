package earth.terrarium.ad_astra.block.machine.entity;

import earth.terrarium.ad_astra.config.CryoFreezerConfig;
import earth.terrarium.ad_astra.recipe.CryoFuelConversionRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.menu.CryoFreezerMenu;
import earth.terrarium.ad_astra.util.FluidUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.api.fluid.ExtractOnlyUpdatingFluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CryoFreezerBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock, EnergyBlock {
    private InsertOnlyEnergyContainer energyContainer;

    protected int cookTime;
    protected int cookTimeTotal;
    @Nullable
    protected Item inputItem;
    @Nullable
    protected Fluid outputFluid;
    private ExtractOnlyUpdatingFluidContainer tank;

    public CryoFreezerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CRYO_FREEZER.get(), blockPos, blockState);
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
                FluidHolder outputFluid = FluidHooks.newFluidHolder(recipe.getFluidOutput(), (long) (FluidHooks.buckets(1) * recipe.getConversionRatio()), null);
                getFluidContainer().internalInsert(outputFluid, false);
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
            this.inputItem = testStack.getItem();
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
                FluidUtils.extractTankFluidToItem(this.getFluidContainer(), this, 1, 2, 0, f -> true);
            }

            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                if ((!input.isEmpty() && (input.getItem().equals(this.inputItem) || this.inputItem == null)) && getFluidContainer().getFluids().get(0).getFluidAmount() < getFluidContainer().getTankCapacity(0)) {
                    if (this.cookTime < this.cookTimeTotal) {
                        this.cookTime++;
                        this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
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

    @Override
    public ExtractOnlyUpdatingFluidContainer getFluidContainer() {
        return tank == null ? tank = new ExtractOnlyUpdatingFluidContainer(this, i -> CryoFreezerConfig.tankSize, 1, (amount, fluid) -> true) : this.tank;
    }

    public long getEnergyPerTick() {
        return CryoFreezerConfig.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public InsertOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new InsertOnlyEnergyContainer(this, (int) CryoFreezerConfig.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}