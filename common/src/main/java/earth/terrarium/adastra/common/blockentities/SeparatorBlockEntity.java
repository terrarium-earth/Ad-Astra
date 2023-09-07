package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.menus.SeparatorMenu;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SeparatorBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, BotariumFluidBlock<WrappedBlockFluidContainer>, GeoBlockEntity {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private long lastEnergy;
    private long energyDifference;
    private long lastFluid1;
    private long lastFluid2;
    private long lastFluid3;
    private long fluidDifference1;
    private long fluidDifference2;
    private long fluidDifference3;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final int CONTAINER_SIZE = 7;

    public SeparatorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, CONTAINER_SIZE);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
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
            new SimpleFluidContainer(
                FluidHooks.buckets(10.0f),
                3,
                (tank, holder) -> switch (tank) {
                    case 0 -> holder.getFluid().equals(Fluids.WATER); // TODO
                    case 1 -> holder.getFluid().is(ModFluidTags.HYDROGEN); // TODO
                    case 2 -> holder.getFluid().is(ModFluidTags.OXYGEN); // TODO
                    default -> true;
                }));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
//        controllerRegistrar.add(new AnimationController<>(this, state -> // TODO
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void update() {
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        extractBatterySlot();
        this.getFluidContainer().insertFluid(FluidHooks.newFluidHolder(Fluids.WATER, FluidHooks.buckets(0.02), null), false);
        this.getFluidContainer().insertFluid(FluidHooks.newFluidHolder(ModFluids.HYDROGEN.get(), FluidHooks.buckets(0.02), null), false);
        this.getFluidContainer().insertFluid(FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), FluidHooks.buckets(0.02), null), false);
        sync();
    }

    public void extractBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(holder, this, null, getEnergyStorage().maxInsert(), false);
        if (holder.isDirty()) {
            this.setItem(0, holder.getStack());
        }
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        this.energyDifference = this.getEnergyStorage().getStoredEnergy() - this.lastEnergy;
        this.lastEnergy = this.getEnergyStorage().getStoredEnergy();
        this.fluidDifference1 = this.getFluidContainer().getFluids().get(0).getFluidAmount() - this.lastFluid1;
        this.fluidDifference2 = this.getFluidContainer().getFluids().get(1).getFluidAmount() - this.lastFluid2;
        this.fluidDifference3 = this.getFluidContainer().getFluids().get(2).getFluidAmount() - this.lastFluid3;
        this.lastFluid1 = this.getFluidContainer().getFluids().get(0).getFluidAmount();
        this.lastFluid2 = this.getFluidContainer().getFluids().get(1).getFluidAmount();
        this.lastFluid3 = this.getFluidContainer().getFluids().get(2).getFluidAmount();
    }

    public long energyDifference() {
        return this.energyDifference;
    }

    public long fluidDifference1() {
        return this.fluidDifference1;
    }

    public long fluidDifference2() {
        return this.fluidDifference2;
    }

    public long fluidDifference3() {
        return this.fluidDifference3;
    }
}
