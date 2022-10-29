package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.ad_astra.recipes.OxygenConversionRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.screen.handler.ConversionScreenHandler;
import earth.terrarium.ad_astra.util.FluidUtils;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OxygenLoaderBlockEntity extends FluidMachineBlockEntity {

    public OxygenLoaderBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_LOADER.get(), blockPos, blockState);
    }

    @Override
    public long getInputTankCapacity() {
        return AdAstra.CONFIG.oxygenLoader.tankSize;
    }

    @Override
    public long getOutputTankCapacity() {
        return AdAstra.CONFIG.oxygenLoader.tankSize;
    }

    @Override
    public int getInventorySize() {
        return 4;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1 || slot == 3;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ConversionScreenHandler(syncId, inv, this);
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            ItemStack insertSlot = this.getItems().get(0);
            ItemStack extractSlot = this.getItems().get(1);
            ItemStack outputInsertSlot = this.getItems().get(2);
            ItemStack outputExtractSlot = this.getItems().get(3);

            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount() && FluidHooks.isFluidContainingItem(insertSlot)) {
                FluidUtils.insertItemFluidToTank(this.getFluidContainer(), this, 0, 1, 0, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.world).stream().anyMatch(r -> r.matches(f)));
            }

            if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
                FluidUtils.extractTankFluidToItem(this.getTanks().getOutput(), this, 2, 3, 0, f -> true);
            }

            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.get().getRecipes(this.world);
                if (FluidUtils.convertFluid((DoubleFluidTank) this.getFluidContainer(), recipes, 50)) {
                    this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);
                    this.setActive(true);
                } else {
                    this.setActive(false);
                }
            } else {
                this.setActive(false);
            }
        }
    }

    @Override
    public long getEnergyPerTick() {
        return AdAstra.CONFIG.oxygenLoader.energyPerTick;
    }

    @Override
    public InsertOnlyEnergyContainer getEnergyStorage() {
        return this.energyContainer == null ? this.energyContainer = new InsertOnlyEnergyContainer(this, (int) AdAstra.CONFIG.oxygenLoader.maxEnergy) : this.energyContainer;
    }
}