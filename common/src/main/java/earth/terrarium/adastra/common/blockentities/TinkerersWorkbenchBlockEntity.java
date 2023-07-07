package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
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

public class TinkerersWorkbenchBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, GeoBlockEntity {
    private WrappedBlockEnergyContainer energyContainer;

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.model.idle");
    public static final RawAnimation ACTIVATE = RawAnimation.begin().thenPlayAndHold("animation.model.activate");
    public static final RawAnimation FABRICATING = RawAnimation.begin().thenLoop("animation.model.fabricating");
    public static final RawAnimation COMPLETE = RawAnimation.begin().thenPlayAndHold("animation.model.complete");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final int CONTAINER_SIZE = 5;

    public TinkerersWorkbenchBlockEntity(BlockPos pos, BlockState state) {
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state ->
            state.setAndContinue(FABRICATING)));
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
    }
}
