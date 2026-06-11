package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.ContainerRecipeWrapper;
import earth.terrarium.adastra.common.blockentities.base.RecipeMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.CryoFreezerMenu;
import earth.terrarium.adastra.common.recipes.machines.CryoFreezingRecipe;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.UpdateManager;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
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

public class CryoFreezerBlockEntity extends RecipeMachineBlockEntity<CryoFreezingRecipe> implements FluidProvider.BlockEntity {

    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );
    private final SimpleValueStorage energy = new SimpleValueStorage(this, ModDataManagers.VALUE_CONTENT, MachineConfig.OSTRUM.energyCapacity);
    private final SimpleFluidStorage fluid = new SimpleFluidStorage(this, ModDataManagers.FLUID_CONTENTS, 1, FluidAmounts.toPlatformAmount(MachineConfig.OSTRUM.fluidCapacity)) {
//            @Override
//            public boolean allowsInsertion() {
//                return false;
//            }
    }.filter(0, f -> level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get())
        .stream()
        .anyMatch(r -> r.value().result().resource().isOf(f.getType())));

    public CryoFreezerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4, ModRecipeTypes.CRYO_FREEZING);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CryoFreezerMenu(id, inventory, this);
    }

    @Override
    public ValueStorage getEnergy(Direction direction) {
        return energy;
    }

    @Override
    public long maxInsertExtract() {
        return MachineConfig.OSTRUM.maxEnergyInOut;
    }

    @Override
    public CommonStorage<FluidResource> getFluids(Direction direction) {
        return fluid;
    }

    public CommonStorage<FluidResource> getFluidContainer() {
        return fluid;
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pushItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{2}, sideConfig.get(1), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{2}, sideConfig.get(1), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{3}, sideConfig.get(2), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{3}, sideConfig.get(2), filter);
        TransferUtils.pullEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(3), filter);
        TransferUtils.pushFluidNearby(this, pos, fluid, FluidAmounts.toPlatformAmount(200), 0, sideConfig.get(4), filter);
    }

    @Override
    public void recipeTick(ServerLevel level, ValueStorage energyStorage) {
        if (recipe == null) return;
        if (!canCraft()) {
            clearRecipe();
            return;
        }

        energyStorage.extract(recipe.energy(), false);
        UpdateManager.batch(energyStorage);

        cookTime++;
        if (cookTime < cookTimeTotal) return;
        craft();
    }

    @Override
    public void craft() {
        if (recipe == null) return;

        getItem(1).shrink(1);
        fluid.insert(recipe.result().resource(), recipe.result().amount(), false);

        updateSlots();

        cookTime = 0;
        if (fluid.getResource(0).isBlank()) clearRecipe();
    }

    @Override
    public void update() {
        if (level().isClientSide()) return;
        quickCheck.getRecipeFor(new ContainerRecipeWrapper(this), level()).ifPresent(r -> {
            recipe = r.value();
            cookTimeTotal = r.value().cookingTime();
        });
        updateSlots();
    }

    @Override
    public void updateSlots() {
        FluidUtils.moveContainerToItem(this, fluid, 2, 3, 0);
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
