package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.RecipeMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.container.BiFluidContainer;
import earth.terrarium.adastra.common.menus.machines.FuelRefineryMenu;
import earth.terrarium.adastra.common.recipes.machines.RefiningRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class FuelRefineryBlockEntity extends RecipeMachineBlockEntity<RefiningRecipe> implements BotariumFluidBlock<WrappedBlockFluidContainer> {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_EXTRACTION_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_FLUID),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );

    private WrappedBlockFluidContainer fluidContainer;

    public FuelRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 5, ModRecipeTypes.REFINING);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FuelRefineryMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            new InsertOnlyEnergyContainer(MachineConfig.steelTierEnergyCapacity, MachineConfig.steelTierMaxEnergyInOut));
    }

    @Override
    public @Nullable WrappedBlockFluidContainer getFluidContainer(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        return getFluidContainer();
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        if (fluidContainer != null) return fluidContainer;
        return fluidContainer = new WrappedBlockFluidContainer(
            this,
            new BiFluidContainer(
                FluidConstants.fromMillibuckets(MachineConfig.steelTierFluidCapacity),
                1,
                1,
                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.REFINING.get())
                    .stream()
                    .anyMatch(r -> r.value().input().test(holder)),
                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.REFINING.get())
                    .stream()
                    .anyMatch(r -> r.value().result().matches(holder))));
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{3}, sideConfig.get(1), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{2, 4}, sideConfig.get(2), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(3), filter);
        TransferUtils.pullFluidNearby(this, pos, getFluidContainer(), FluidConstants.fromMillibuckets(200), 0, sideConfig.get(4), filter);
        TransferUtils.pushFluidNearby(this, pos, getFluidContainer(), FluidConstants.fromMillibuckets(200), 1, sideConfig.get(5), filter);
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

        fluidContainer.internalExtract(getFluidContainer().getFirstFluid().copyWithAmount(recipe.input().getFluidAmount()), false);
        fluidContainer.internalInsert(recipe.result(), false);

        updateSlots();

        cookTime = 0;
        if (fluidContainer.getFirstFluid().isEmpty()) clearRecipe();
    }

    @Override
    public void update() {
        quickCheck.getRecipeFor(this, level()).ifPresent(r -> {
            recipe = r.value();
            cookTimeTotal = r.value().cookingTime();
        });
        updateSlots();
    }

    @Override
    public void updateSlots() {
        FluidUtils.moveItemToContainer(this, fluidContainer, 1, 2, 0);
        FluidUtils.moveContainerToItem(this, fluidContainer, 3, 4, 1);
        sync();
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2, 3, 4};
    }
}
