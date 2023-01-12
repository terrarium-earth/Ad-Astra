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
import net.minecraft.MethodsReturnNonnullByDefault;
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
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public class SolarPanelBlockEntity extends MachineBlockEntity implements EnergyAttachment.Block, ExtraDataMenuProvider {
    private WrappedBlockEnergyContainer energyContainer;

    private final int energyPerTick;
    private final int capacity;

    public SolarPanelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.SOLAR_PANEL.get(), blockPos, blockState);
        this.energyPerTick = ((SolarPanelBlock) blockState.getBlock()).getEnergyPerTick();
        this.capacity = ((SolarPanelBlock) blockState.getBlock()).getCapacity();
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (level.isDay() && (!Level.OVERWORLD.equals(this.level.dimension()) || !this.level.isRaining() && !this.level.isThundering()) && level.canSeeSky(this.getBlockPos().above())) {
            this.getEnergyStorage().internalInsert(getEnergyPerTick(), false);
        }

        EnergyHooks.distributeEnergyNearby(this, 128);
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SolarPanelMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new ExtractOnlyEnergyContainer(20000)) : this.energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    public int getEnergyPerTick() {
        return this.energyPerTick;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
