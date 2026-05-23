package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.RecipeMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.OxygenLoaderMenu;
import earth.terrarium.adastra.common.recipes.machines.OxygenLoadingRecipe;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
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

import java.util.List;
import java.util.function.Predicate;

public class OxygenLoaderBlockEntity extends RecipeMachineBlockEntity<OxygenLoadingRecipe> implements FluidProvider.Block {

    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_EXTRACTION_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_FLUID),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );

    private SimpleFluidStorage fluidContainer;

    public OxygenLoaderBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 5);
    }

    public OxygenLoaderBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state, containerSize, ModRecipeTypes.OXYGEN_LOADING);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new OxygenLoaderMenu(id, inventory, this);
    }

    @Override
    public ValueStorage getEnergy(Direction direction) {
        return new SimpleValueStorage(this, ModDataManagers.VALUE_CONTENT, MachineConfig.STEEL.energyCapacity);
    }

    @Override
    public long maxInsertExtract() {
        return MachineConfig.STEEL.maxEnergyInOut;
    }

    @Override
    public CommonStorage<FluidResource> getFluids(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Direction direction) {
        return new SimpleFluidStorage(this, ModDataManagers.FLUID_CONTENTS, 2, FluidAmounts.toPlatformAmount(MachineConfig.STEEL.fluidCapacity))
            .filter(0, f -> level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get())
                .stream()
                .anyMatch(r -> r.value().input().ingredient().test(f)))
            .filter(1, f -> level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get())
                .stream()
                .anyMatch(r -> r.value().result().resource().isOf(f.getType())));
//        return fluidContainer = new WrappedBlockFluidContainer( TODO: Implement fluid storage!
//            this,
//            new BiFluidContainer(
//                FluidAmounts.toPlatformAmount(MachineConfig.STEEL.fluidCapacity),
//                1,
//                1,
//                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get())
//                    .stream()
//                    .anyMatch(r -> r.value().input().test(holder)),
//                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get())
//                    .stream()
//                    .anyMatch(r -> r.value().result().matches(holder))));
    }

    protected CommonStorage<FluidResource> getFluidContainer() {
        return FluidApi.BLOCK.find(this, null);
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{3}, sideConfig.get(1), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{2, 4}, sideConfig.get(2), filter);
        TransferUtils.pullEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(3), filter);
        TransferUtils.pullFluidNearby(this, pos, getFluidContainer(), FluidAmounts.toPlatformAmount(200), 0, sideConfig.get(4), filter);
        TransferUtils.pushFluidNearby(this, pos, getFluidContainer(), FluidAmounts.toPlatformAmount(200), 1, sideConfig.get(5), filter);
    }

    @Override
    public void recipeTick(ServerLevel level, ValueStorage energyStorage) {
        if (recipe == null) return;
        if (fluidContainer == null) getFluidContainer();
        if (!canCraft()) {
            clearRecipe();
            return;
        }

        energyStorage.extract(recipe.energy(), false);

        cookTime++;
        if (cookTime < cookTimeTotal) return;
        craft();
    }

    @Override
    public void craft() {
        if (recipe == null) return;

        fluidContainer.extract(getFluidContainer().getResource(0), recipe.input().getAmount(), false);
        fluidContainer.insert(recipe.result().resource(), recipe.result().amount(), false);

        updateSlots();

        cookTime = 0;
        if (fluidContainer.getContents(0).isEmpty()) clearRecipe();
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
        FluidUtils.moveItemToContainer(this, getFluidContainer(), 1, 2, 0);
        FluidUtils.moveContainerToItem(this, getFluidContainer(), 3, 4, 1);
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
