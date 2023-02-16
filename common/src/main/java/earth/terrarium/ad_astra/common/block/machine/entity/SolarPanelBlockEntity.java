package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.MachineBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.SolarPanelBlock;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.screen.machine.SolarPanelMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SolarPanelBlockEntity extends MachineBlockEntity implements EnergyAttachment.Block, ExtraDataMenuProvider, GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private WrappedBlockEnergyContainer energyContainer;

    private final int energyPerTick;
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlayAndHold("solar_panel.open");
    private static final RawAnimation NIGHT_IDLE = RawAnimation.begin().thenPlayAndHold("solar_panel.close");

    public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.SOLAR_PANEL.get(), blockPos, blockState);
        energyPerTick = ((SolarPanelBlock) blockState.getBlock()).getEnergyPerTick();
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (canGenerate()) {
            getEnergyStorage().internalInsert(getEnergyPerTick(), false);
        }

        EnergyHooks.distributeEnergyNearby(this, 128);
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SolarPanelMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new ExtractOnlyEnergyContainer(20000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            if (canGenerate()) {
                return state.setAndContinue(IDLE);
            } else {
                return state.setAndContinue(NIGHT_IDLE);
            }
        }));
    }

    public boolean canGenerate() {
        return level.getDayTime() % 24000 < 12000 && (!Level.OVERWORLD.equals(level.dimension()) || !level.isRaining() && !level.isThundering()) && level.canSeeSky(getBlockPos().above());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
