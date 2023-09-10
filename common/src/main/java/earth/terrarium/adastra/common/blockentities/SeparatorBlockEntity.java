package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.PoweredMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.container.BiFluidContainer;
import earth.terrarium.adastra.common.menus.SeparatorMenu;
import earth.terrarium.adastra.common.recipes.SeparatingRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class SeparatorBlockEntity extends PoweredMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer>, GeoBlockEntity, SideConfigurable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    private SeparatingRecipe recipe;
    private int cookTime;
    private int cookTimeTotal;

    private final long[] lastFluid = new long[3];
    private final long[] fluidDifference = new long[3];
    private final List<ConfigurationEntry> sideConfig = new ArrayList<>();
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
        extractBatterySlot();
        recipeTick();
        if (time % 2 == 0) sync();
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
        ConfigurationEntry.load(tag, this.sideConfig, defaultConfig());
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ConfigurationEntry.save(tag, this.sideConfig);
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
    }

    @Override
    public List<ConfigurationEntry> getConfigurableEntries() {
        return this.sideConfig;
    }

    @Override
    public List<ConfigurationEntry> defaultConfig() {
        return List.of(
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.INPUT, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.OUTPUT, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
            new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.INPUT, ConstantComponents.SIDE_CONFIG_ENERGY),
            new ConfigurationEntry(ConfigurationType.FLUID, Configuration.INPUT, ConstantComponents.SIDE_CONFIG_INPUT_FLUID),
            new ConfigurationEntry(ConfigurationType.FLUID, Configuration.OUTPUT, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID),
            new ConfigurationEntry(ConfigurationType.FLUID, Configuration.OUTPUT, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
        );
    }
}
