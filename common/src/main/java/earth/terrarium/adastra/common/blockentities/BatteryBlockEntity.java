package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blocks.BatteryBlock;
import earth.terrarium.adastra.common.menus.BatteryMenu;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    private WrappedBlockEnergyContainer energyContainer;
    private long lastEnergy;
    private long energyDifference;

    public static final int CONTAINER_SIZE = 5;

    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, CONTAINER_SIZE);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BatteryMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(
            this,
            new SimpleEnergyContainer(2_000_000) {
                @Override
                public long maxInsert() {
                    return 5_000;
                }

                @Override
                public long maxExtract() {
                    return 5_000;
                }

                @Override
                public void setEnergy(long energy) {
                    super.setEnergy(energy);
                    energyDifference = energy - lastEnergy;
                    if (BatteryBlockEntity.this.level().getGameTime() % 10 == 0) {
                        energyChanged();
                    }
                }
            });
    }

    @Override
    public void update() {
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        EnergyUtils.distributeEnergyNearby(this, energyContainer.maxExtract(), pair -> !pair.getFirst().equals(Direction.UP));
        extractBatterySlot();
        distributeToChargeSlots();
    }

    public void extractBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(holder, this, null, energyContainer.maxInsert() * 4, false);
        if (holder.isDirty()) {
            this.setItem(0, holder.getStack());
        }
    }

    public void distributeToChargeSlots() {
        int filledSlots = 0;
        for (int i = 0; i < 4; i++) {
            if (!this.getItem(i + 1).isEmpty()) {
                filledSlots++;
            }
        }
        if (filledSlots == 0) return;

        for (int i = 0; i < 4; i++) {
            ItemStack stack = this.getItem(i + 1);
            if (stack.isEmpty()) continue;
            if (!EnergyApi.isEnergyItem(stack)) continue;
            ItemStackHolder holder = new ItemStackHolder(stack);
            EnergyApi.moveEnergy(this, null, holder, energyContainer.maxExtract() / filledSlots, false);
            if (holder.isDirty()) {
                this.setItem(i, holder.getStack());
            }
        }
    }

    public void energyChanged() {
        int charge = Math.round(this.energyContainer.getStoredEnergy() / (float) this.getEnergyStorage().getMaxCapacity() * 4);
        this.level().setBlock(this.getBlockPos(), this.getBlockState().setValue(BatteryBlock.CHARGE, charge), Block.UPDATE_CLIENTS);
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        this.energyDifference = this.energyContainer.getStoredEnergy() - this.lastEnergy;
        this.lastEnergy = this.energyContainer.getStoredEnergy();
    }

    public long energyDifference() {
        return this.energyDifference;
    }
}
