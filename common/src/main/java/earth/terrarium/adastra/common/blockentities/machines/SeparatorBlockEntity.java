package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.PoweredMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.container.BiFluidContainer;
import earth.terrarium.adastra.common.menus.machines.SeparatorMenu;
import earth.terrarium.adastra.common.recipes.machines.SeparatingRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SeparatorBlockEntity extends PoweredMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer>, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    private SeparatingRecipe recipe;
    private int cookTime;
    private int cookTimeTotal;

    private final long[] lastFluid = new long[3];
    private final long[] fluidDifference = new long[3];
    private WrappedBlockFluidContainer fluidContainer;

    public SeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 7);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
//        controllerRegistrar.add(new AnimationController<>(this, state -> // TODO
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new SeparatorMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(
            this,
            new InsertOnlyEnergyContainer(100_000) {
                @Override
                public long maxInsert() {
                    return 2_500;
                }
            });
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer() {
        if (this.fluidContainer != null) return this.fluidContainer;
        return this.fluidContainer = new WrappedBlockFluidContainer(
            this,
            new BiFluidContainer(
                FluidHooks.buckets(10.0f),
                1,
                2,
                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.SEPARATING.get())
                    .stream()
                    .anyMatch(r -> r.ingredient().matches(holder)),
                (tank, holder) -> level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.SEPARATING.get())
                    .stream()
                    .anyMatch(r -> tank == 0 ?
                        r.resultFluid1().matches(holder) :
                        r.resultFluid2().matches(holder))));
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        TransferUtils.pullItemsNearby(this, new int[]{1}, this.getSideConfig().get(0), d -> true);
        TransferUtils.pullItemsNearby(this, new int[]{3, 5}, this.getSideConfig().get(1), d -> true);
        TransferUtils.pushItemsNearby(this, new int[]{2, 4, 6}, this.getSideConfig().get(2), d -> true);
        TransferUtils.pullEnergyNearby(this, this.getEnergyStorage().maxInsert(), this.getSideConfig().get(3), d -> true);
        TransferUtils.pullFluidNearby(this, this.getFluidContainer(), FluidHooks.buckets(0.2f), 0, this.getSideConfig().get(4), d -> true);
        TransferUtils.pushFluidNearby(this, this.getFluidContainer(), FluidHooks.buckets(0.2f), 1, this.getSideConfig().get(5), d -> true);
        TransferUtils.pushFluidNearby(this, this.getFluidContainer(), FluidHooks.buckets(0.2f), 2, this.getSideConfig().get(6), d -> true);

        extractBatterySlot();
        recipeTick();
        if (time % 2 == 0) sync();

        if (time % 20 == 0 && recipe == null && getEnergyStorage().getStoredEnergy() > 0 && !getFluidContainer().getFluids().get(0).isEmpty()) {
            update();
        }
    }

    public void recipeTick() {
        if (recipe == null) return;
        var energyStorage = getEnergyStorage();
        var fluidContainer = getFluidContainer();

        if (fluidContainer.getFluids().get(1).getFluidAmount() >= fluidContainer.getTankCapacity(1)) return;
        if (fluidContainer.getFluids().get(2).getFluidAmount() >= fluidContainer.getTankCapacity(2)) return;

        long energy = energyStorage.internalExtract(recipe.energy(), true);
        if (energy < recipe.energy()) return;

        long fluid = fluidContainer.internalExtract(recipe.ingredient(), true).getFluidAmount();
        if (fluid < recipe.ingredient().getFluidAmount()) return;

        energyStorage.internalExtract(recipe.energy(), false);

        cookTime++;
        if (cookTime < cookTimeTotal) return;
        cookTime = 0;

        fluidContainer.internalExtract(recipe.ingredient(), false);

        fluidContainer.internalInsert(recipe.resultFluid1(), false);
        fluidContainer.internalInsert(recipe.resultFluid2(), false);

        this.updateFluidSlots();
        if (fluidContainer.getFluids().get(0).isEmpty()) {
            recipe = null;
        }
    }

    @Override
    public void update() {
        if (level().isClientSide()) return;
        level().getRecipeManager().getAllRecipesFor(ModRecipeTypes.SEPARATING.get())
            .stream()
            .filter(r -> r.ingredient().matches(getFluidContainer().getFluids().get(0)))
            .findFirst()
            .ifPresent(r -> {
                recipe = r;
                cookTimeTotal = r.cookingTime();
            });
        this.updateFluidSlots();
    }

    @Override
    public void updateFluidSlots() {
        FluidUtils.insertSlotToTank(this, 1, 2, 0);
        FluidUtils.extractTankToSlot(this, 3, 4, 1);
        FluidUtils.extractTankToSlot(this, 5, 6, 2);
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        super.clientTick(level, time, state, pos);
        if (time % 2 == 0) return;
        for (int i = 0; i < 3; i++) {
            this.fluidDifference[i] = this.getFluidContainer().getFluids().get(i).getFluidAmount() - this.lastFluid[i];
            this.lastFluid[i] = this.getFluidContainer().getFluids().get(i).getFluidAmount();
        }
    }

    public long fluidDifference(int tank) {
        return this.fluidDifference[tank];
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return List.of(
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_EXTRACTION_SLOTS),
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
            new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
            new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_FLUID),
            new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID),
            new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
        );
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2, 3, 4, 5, 6};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        if (direction == null) return false;
        ConfigurationEntry config = getConfigForSlot(index);
        return config.get(ModUtils.relative(this, direction)).canPull();
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        ConfigurationEntry config = getConfigForSlot(index);
        return config.get(ModUtils.relative(this, direction)).canPush();
    }

    public ConfigurationEntry getConfigForSlot(int index) {
        return switch (index) {
            case 1 -> this.getSideConfig().get(0);
            case 3, 5 -> this.getSideConfig().get(1);
            default -> this.getSideConfig().get(2);
        };
    }
}
