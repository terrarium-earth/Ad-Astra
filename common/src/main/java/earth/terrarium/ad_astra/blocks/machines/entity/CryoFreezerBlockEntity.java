package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.recipes.CryoFuelConversionRecipe;
import earth.terrarium.ad_astra.recipes.ModRecipeType;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.screen.handler.CryoFreezerScreenHandler;
import earth.terrarium.ad_astra.util.FluidUtils;
import earth.terrarium.botarium.api.fluid.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class CryoFreezerBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

    protected short cookTime;
    protected short cookTimeTotal;
    @Nullable
    protected Item inputItem;
    @Nullable
    protected Fluid outputFluid;
    private SimpleUpdatingFluidContainer tank;

    public CryoFreezerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CRYO_FREEZER.get(), blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.cookTime = nbt.getShort("cookTime");
        this.cookTimeTotal = nbt.getShort("cookTimeTotal");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("cookTime", this.cookTime);
        nbt.putShort("cookTimeTotal", this.cookTimeTotal);
    }

    public short getCookTime() {
        return this.cookTime;
    }

    public short getCookTimeTotal() {
        return this.cookTimeTotal;
    }

    public void finishCooking() {
        if (this.outputFluid != null) {
            CryoFuelConversionRecipe recipe = this.createRecipe(ModRecipes.CRYO_FUEL_CONVERSION_RECIPE.get(), this.getStack(0), false);
            if (recipe != null) {
                FluidHolder outputFluid = FluidHooks.newFluidHolder(recipe.getFluidOutput(), (long) (FluidHooks.buckets(1) * recipe.getConversionRatio()), null);
                getFluidContainer().insertFluid(outputFluid, false);
            }
        }
        this.stopCooking();
    }

    public void stopCooking() {
        this.cookTime = 0;
        this.cookTimeTotal = 0;
        this.outputFluid = null;
        this.inputItem = null;
        this.markDirty();
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getCapacity() {
        return AdAstra.CONFIG.cryoFreezer.maxEnergy;
    }

    @Override
    public long getEnergyPerTick() {
        return AdAstra.CONFIG.cryoFreezer.energyPerTick;
    }

    @Override
    public boolean canInsertEnergy() {
        return true;
    }

    @Override
    public int getInventorySize() {
        return 3;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 2;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CryoFreezerScreenHandler(syncId, inv, this);
    }

    public CryoFuelConversionRecipe createRecipe(ModRecipeType<CryoFuelConversionRecipe> type, ItemStack testStack, boolean checkOutput) {
        stopCooking();

        CryoFuelConversionRecipe recipe = type.findFirst(this.world, f -> f.test(testStack));

        if (recipe != null) {

            // Stop if something is already in the output.
            if (checkOutput) {
                ItemStack outputSlot = this.getStack(1);
                ItemStack output = recipe.getOutput();
                if (!outputSlot.isEmpty() && !outputSlot.getItem().equals(recipe.getOutput().getItem()) || outputSlot.getCount() + output.getCount() > outputSlot.getMaxCount()) {
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
        if (!this.getWorld().isClient) {
            ItemStack input = this.getStack(0);
            ItemStack outputInsertSlot = this.getStack(1);
            ItemStack outputExtractSlot = this.getStack(2);

            if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
                FluidUtils.extractFluidFromItem(this, 1, 2, 0, this, f -> true);
            }

            if (this.hasEnergy()) {
                if ((!input.isEmpty() && (input.getItem().equals(this.inputItem) || this.inputItem == null)) && getFluidContainer().getFluids().get(0).getFluidAmount() < getFluidContainer().getTankCapacity(0)) {
                    this.setActive(true);
                    if (this.cookTime < this.cookTimeTotal) {
                        this.cookTime++;
                        this.drainEnergy();

                    } else if (this.outputFluid != null) {
                        this.finishCooking();
                        input.decrement(1);

                    } else {
                        CryoFuelConversionRecipe recipe = this.createRecipe(ModRecipes.CRYO_FUEL_CONVERSION_RECIPE.get(), input, false);
                        if (recipe != null) {
                            this.cookTimeTotal = 25;
                            this.cookTime = 0;
                        }
                    }
                } else if (this.outputFluid != null) {
                    this.stopCooking();
                } else {
                    this.setActive(false);
                }
            }
        }
    }

    @Override
    public UpdatingFluidContainer getFluidContainer() {
        return tank == null ? tank = new SimpleUpdatingFluidContainer(this, AdAstra.CONFIG.cryoFreezer.tankSize, 1, (amount, fluid) -> true) : this.tank;
    }
}