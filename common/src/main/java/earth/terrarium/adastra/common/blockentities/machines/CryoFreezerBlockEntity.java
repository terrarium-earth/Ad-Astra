package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.RecipeMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.CryoFreezerMenu;
import earth.terrarium.adastra.common.recipes.machines.CryoFreezingRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.ExtractOnlyFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class CryoFreezerBlockEntity extends RecipeMachineBlockEntity<CryoFreezingRecipe> implements BotariumFluidBlock<WrappedBlockFluidContainer> {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );

    private WrappedBlockFluidContainer fluidContainer;

    public CryoFreezerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4, ModRecipeTypes.CRYO_FREEZING);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CryoFreezerMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(
            this,
            new InsertOnlyEnergyContainer(MachineConfig.ostrumTierEnergyCapacity, MachineConfig.ostrumTierMaxEnergyInOut));
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer() {
        if (fluidContainer != null) return fluidContainer;
        return fluidContainer = new WrappedBlockFluidContainer(
            this,
            new ExtractOnlyFluidContainer(
                i -> FluidConstants.fromMillibuckets(MachineConfig.ostrumTierFluidCapacity),
                1,
                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get())
                    .stream()
                    .anyMatch(r -> r.result().matches(holder))));
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pushItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{2}, sideConfig.get(1), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{2}, sideConfig.get(1), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{3}, sideConfig.get(2), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{3}, sideConfig.get(2), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(3), filter);
        TransferUtils.pushFluidNearby(this, pos, getFluidContainer(), FluidConstants.fromMillibuckets(200), 1, sideConfig.get(4), filter);
    }

    @Override
    public void recipeTick(ServerLevel level, WrappedBlockEnergyContainer energyStorage) {
        if (recipe == null) return;
        if (fluidContainer == null) getFluidContainer();
        if (!canCraft()) {
            clearRecipe();
            return;
        }

        energyStorage.internalExtract(recipe.energy(), false);

        cookTime++;
        if (cookTime < cookTimeTotal) return;
        craft();
    }

    @Override
    public void craft() {
        if (recipe == null) return;

        getItem(1).shrink(1);
        fluidContainer.internalInsert(recipe.result(), false);

        updateSlots();

        cookTime = 0;
        if (fluidContainer.getFirstFluid().isEmpty()) clearRecipe();
    }

    @Override
    public void update() {
        if (level().isClientSide()) return;
        quickCheck.getRecipeFor(this, level()).ifPresent(r -> {
            recipe = r;
            cookTimeTotal = r.cookingTime();
        });
        updateSlots();
    }

    @Override
    public void updateSlots() {
        FluidUtils.moveContainerToItem(this, fluidContainer, 2, 3, 0);
        sync();
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2, 3};
    }
}
