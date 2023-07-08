package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.OxygenDistributorBlock;
import earth.terrarium.adastra.common.utils.KeybindManager;
import earth.terrarium.adastra.common.utils.floodfill.FloodFill3D;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.Set;

public class OxygenDistributorBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, BotariumFluidBlock<WrappedBlockFluidContainer>, GeoBlockEntity {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private final Set<BlockPos> lastDistributedBlocks = new HashSet<>();

    public static final RawAnimation SPIN = RawAnimation.begin().thenLoop("animation.model.spin");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final int CONTAINER_SIZE = 5;

    public OxygenDistributorBlockEntity(BlockPos pos, BlockState state) {
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
            state.setAndContinue(SPIN)));
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
            this.tickOxygen(level, state, pos);
        }
    }

    @Override
    public void onRemoved() {
        this.clearOxygenBlocks();
    }

    protected void tickOxygen(ServerLevel level, BlockState state, BlockPos pos) {
        AttachFace face = state.getValue(OxygenDistributorBlock.FACE);
        Direction facing = state.getValue(OxygenDistributorBlock.FACING);
        BlockPos start = switch (face) {
            case FLOOR -> pos.above();
            case WALL -> pos.relative(facing);
            case CEILING -> pos.below();
        };

        int limit = 3000;
        Set<BlockPos> positions = FloodFill3D.run(level, start, limit, FloodFill3D.TEST_FULL_SEAL);
        this.resetLastDistributedBlocks(positions);
        OxygenApi.API.setOxygen(level, positions, true);
    }

    protected void resetLastDistributedBlocks(Set<BlockPos> positions) {
        this.lastDistributedBlocks.removeAll(positions);
        OxygenApi.API.removeOxygen(level, positions);
        this.lastDistributedBlocks.clear();
        this.lastDistributedBlocks.addAll(positions);
    }

    protected void clearOxygenBlocks() {
        OxygenApi.API.removeOxygen(level, this.lastDistributedBlocks);
        this.lastDistributedBlocks.clear();
    }
}
