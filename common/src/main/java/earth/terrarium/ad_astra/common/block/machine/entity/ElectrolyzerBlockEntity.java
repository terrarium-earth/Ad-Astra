package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.recipe.machine.ElectrolysisRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.ElecrolyzerMenu;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class ElectrolyzerBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block, FluidAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private ElectrolysisRecipe recipe;

    public ElectrolyzerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.ELECTROLYZER.get(), blockPos, blockState, 6);
    }

    @Override
    public void serverTick() {
        if (recipe != null) {
            if (!getFluidContainer().isEmpty() && canCraft()) {
                if (getEnergyStorage().internalExtract(recipe.energy(), true) >= recipe.energy()) {
                    if (getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), recipe.ingredient().getFluidAmount(), null), true).getFluidAmount() <= 0)
                        return;
                    if (getFluidContainer().insertFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(1).getFluid(), recipe.resultFluid1().getFluidAmount(), null), true) <= 0
                            && getFluidContainer().insertFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(2).getFluid(), recipe.resultFluid2().getFluidAmount(), null), true) <= 0) {
                        return;
                    }

                    getEnergyStorage().internalExtract(recipe.energy(), false);
                    cookTime++;
                    if (cookTime >= cookTimeTotal) {
                        cookTime = 0;
                        getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), recipe.ingredient().getFluidAmount(), null), false);
                        getFluidContainer().insertFluid(recipe.resultFluid1(), false);
                        getFluidContainer().insertFluid(recipe.resultFluid2(), false);
                        updateFluidSlots();
                    }
                    if (fluidContainer.getFluids().get(0).isEmpty()) {
                        recipe = null;
                    }
                }
            }
        } else {
            cookTime = 0;
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ElecrolyzerMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(20000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return fluidContainer == null ? fluidContainer = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(i -> FluidHooks.buckets(5f), 3, (tank, fluid) -> tank != 0 || Objects.requireNonNull(level).getRecipeManager().getAllRecipesFor(ModRecipeTypes.ELECTROLYSIS.get()).stream().anyMatch(r -> r.ingredient().matches(fluid)))) : fluidContainer;
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        return getFluidContainer(this);
    }

    @Override
    public void update() {
        if (level == null) return;
        recipe = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.ELECTROLYSIS.get()).stream().filter(r -> r.matches(this)).findFirst().orElse(null);
        if (recipe == null) {
            cookTime = 0;
        } else {
            cookTimeTotal = recipe.cookingTime();
        }
        updateFluidSlots();
    }

    private boolean canCraft() {
        return getFluidContainer().getFluids().get(0).getFluid().equals(recipe.ingredient().getFluid());
    }

    public void updateFluidSlots() {
        FluidUtils.insertItemFluidToTank(this, this, 0, 1, f -> recipe == null || f.equals(recipe.ingredient().getFluid()));
        FluidUtils.insertItemFluidToTank(this, this, 2, 3, f -> true);
        FluidUtils.insertItemFluidToTank(this, this, 4, 5, f -> true);

        FluidUtils.extractTankFluidToItem(this, this, 0, 1, 0, f -> true);
        FluidUtils.extractTankFluidToItem(this, this, 2, 3, 1, f -> true);
        FluidUtils.extractTankFluidToItem(this, this, 4, 5, 2, f -> true);
    }
}
