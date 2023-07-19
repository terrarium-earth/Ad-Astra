package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.base.SidedMachineBlock;
import earth.terrarium.adastra.common.utils.floodfill.FloodFill3D;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import jdk.jfr.Unsigned;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.Set;

public class GravityNormalizerBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, BotariumFluidBlock<WrappedBlockFluidContainer>, GeoBlockEntity {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;

    public static final RawAnimation IDLE_OFF = RawAnimation.begin().thenLoop("animation.model.idle.off");
    public static final RawAnimation IDLE_ON = RawAnimation.begin().thenLoop("animation.model.idle.on");
    public static final RawAnimation TURN_ON = RawAnimation.begin().thenPlayAndHold("animation.model.turn.on");
    public static final RawAnimation TURN_OFF = RawAnimation.begin().thenPlayAndHold("animation.model.turn.off");

    public static final int CONTAINER_SIZE = 5;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final Set<BlockPos> lastDistributedBlocks = new HashSet<>();

    public GravityNormalizerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, CONTAINER_SIZE);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(
            this,
            new InsertOnlyEnergyContainer(10000));
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer() {
        if (this.fluidContainer != null) return this.fluidContainer;
        return this.fluidContainer = new WrappedBlockFluidContainer(
            this,
            new SimpleFluidContainer(
                i -> FluidHooks.buckets(10f),
                1,
                (tank, fluid) -> true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state ->
            state.setAndContinue(IDLE_ON)));
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
        if (time % 40 == 0) {
            this.tickGravity(level, state, pos);
        }
    }

    @Override
    public void onRemoved() {
        this.clearGravityBlocks();
    }

    protected void tickGravity(ServerLevel level, BlockState state, BlockPos pos) {
        int limit = 3000;
        Set<BlockPos> positions = FloodFill3D.run(level, ((SidedMachineBlock) state.getBlock()).getTop(state, pos), limit, FloodFill3D.TEST_FULL_SEAL, false);
        this.resetLastDistributedBlocks(positions);
        GravityApi.API.setGravity(level, positions, 0.5f);
    }

    protected void resetLastDistributedBlocks(Set<BlockPos> positions) {
        this.lastDistributedBlocks.removeAll(positions);
        GravityApi.API.removeGravity(level, this.lastDistributedBlocks);
        this.lastDistributedBlocks.clear();
        this.lastDistributedBlocks.addAll(positions);
    }

    protected void clearGravityBlocks() {
        GravityApi.API.removeGravity(level, this.lastDistributedBlocks);
        this.lastDistributedBlocks.clear();
    }
}
