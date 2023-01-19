package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.recipe.machine.GeothermalRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.GeothermalGeneratorMenu;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
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

public class GeothermalGeneratorBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block, FluidAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private GeothermalRecipe recipe;

    public GeothermalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.GEOTHERMAL_GENERATOR.get(), blockPos, blockState, 2);
    }

    @Override
    public void serverTick() {
        if (recipe != null) {
            if (!getFluidContainer().isEmpty() && getFluidContainer().getFluids().get(0).getFluid().equals(recipe.ingredient().getFluid())) {
                if (getEnergyStorage().internalInsert(recipe.energy(), true) >= recipe.energy()) {
                    if (getFluidContainer().extractFluid(recipe.ingredient(), true).getFluidAmount() > 0) {
                        getEnergyStorage().internalInsert(recipe.energy(), false);
                        cookTime++;
                        if (cookTime >= cookTimeTotal) {
                            cookTime = 0;
                            getFluidContainer().extractFluid(recipe.ingredient(), false);
                            updateFluidSlots();
                        }
                        if (fluidContainer.getFluids().get(0).isEmpty()) {
                            recipe = null;
                        }
                    }
                }
            }
        } else {
            cookTime = 0;
        }

        EnergyHooks.distributeEnergyNearby(this, 128);
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new GeothermalGeneratorMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new ExtractOnlyEnergyContainer(200000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return fluidContainer == null ? fluidContainer = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(i -> FluidHooks.buckets(5f), 1, (tank, fluid) -> true)) : fluidContainer;
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        return getFluidContainer(this);
    }

    @Override
    public void update() {
        if (level == null) return;
        recipe = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.GEOTHERMAL_GENERATING.get()).stream().filter(r -> r.matches(this)).findFirst().orElse(null);
        if (recipe == null) {
            cookTime = 0;
        } else {
            cookTimeTotal = recipe.cookingTime();
        }
        updateFluidSlots();
    }

    @Override
    public void updateFluidSlots() {
        FluidUtils.insertItemFluidToTank(this, this, 0, 1, f -> recipe == null || f.equals(recipe.ingredient().getFluid()));
        FluidUtils.extractTankFluidToItem(this, this, 0, 1, 0, f -> true);
    }
}
