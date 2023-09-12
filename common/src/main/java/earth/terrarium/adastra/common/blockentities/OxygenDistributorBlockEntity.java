package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blocks.base.SidedMachineBlock;
import earth.terrarium.adastra.common.entities.AirVortex;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OxygenDistributorBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, BotariumFluidBlock<WrappedBlockFluidContainer>, GeoBlockEntity {
    public static final int MAX_BLOCKS = 3000;
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;

    public static final RawAnimation SPIN = RawAnimation.begin().thenLoop("animation.model.spin");

    public static final int CONTAINER_SIZE = 5;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final Set<BlockPos> lastDistributedBlocks = new HashSet<>();

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
        int limit = MAX_BLOCKS;
        Set<BlockPos> positions = FloodFill3D.run(level, ((SidedMachineBlock) state.getBlock()).getTop(state, pos), limit, FloodFill3D.TEST_FULL_SEAL, true);
        OxygenApi.API.setOxygen(level, positions, true);

        Set<BlockPos> lastPositionsCopy = new HashSet<>(this.lastDistributedBlocks);
        this.resetLastDistributedBlocks(positions);

        if (positions.size() < limit) return;
        if (lastPositionsCopy.size() >= limit) return;
        if (OxygenApi.API.hasOxygen(level)) return;

        positions.removeAll(lastPositionsCopy);
        BlockPos target = positions.stream()
            .skip(1)
            .findFirst()
            .orElse(positions.stream().findFirst().orElse(null));
        if (target == null) return;
        AirVortex vortex = new AirVortex(level, pos, lastPositionsCopy);
        vortex.setPos(Vec3.atCenterOf(target));
        level.addFreshEntity(vortex);
    }

    protected void resetLastDistributedBlocks(Set<BlockPos> positions) {
        this.lastDistributedBlocks.removeAll(positions);
        this.clearOxygenBlocks();
        this.lastDistributedBlocks.addAll(positions);
    }

    protected void clearOxygenBlocks() {
        OxygenApi.API.removeOxygen(level, this.lastDistributedBlocks);
        this.lastDistributedBlocks.clear();
    }

    public Set<BlockPos> lastDistributedBlocks() {
        return this.lastDistributedBlocks;
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return List.of();
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return false;
    }
}
