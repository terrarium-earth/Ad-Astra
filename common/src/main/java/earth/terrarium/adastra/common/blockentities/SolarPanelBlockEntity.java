package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.SolarPanelBlock;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
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

public class SolarPanelBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer>, GeoBlockEntity {
    private WrappedBlockEnergyContainer energyContainer;

    public static final RawAnimation OPEN = RawAnimation.begin().thenPlayAndHold("animation.model.open");
    public static final RawAnimation CLOSE = RawAnimation.begin().thenPlayAndHold("animation.model.close");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final int CONTAINER_SIZE = 5;

    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
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
            new ExtractOnlyEnergyContainer(((SolarPanelBlock) this.getBlockState().getBlock()).capacity()));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            if (this.isDay()) {
                return state.setAndContinue(OPEN);
            } else {
                return state.setAndContinue(CLOSE);
            }
        }));
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
        if (time % 10 == 0 && this.isDay()) {
            long accumulated = ((SolarPanelBlock) state.getBlock()).generationRate() * 10;
            this.energyContainer.internalInsert(accumulated, false);
        }
    }

    public boolean isDay() {
        return this.level().getDayTime() % 24000 < 12000;
    }
}
