package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.BatteryBlock;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    private WrappedBlockEnergyContainer energyContainer;

    public static final int CONTAINER_SIZE = 5;

    public BatteryBlockEntity(BlockPos pos, BlockState state) {
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
            new SimpleEnergyContainer(1000000));
    }

    @Override
    public void update() {
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (time % 10 == 0) {
            int charge = Math.round(this.energyContainer.getStoredEnergy() / (float) this.getEnergyStorage().getMaxCapacity() * 4);
            level.setBlock(pos, state.setValue(BatteryBlock.CHARGE, charge), Block.UPDATE_CLIENTS);
        }
    }
}
