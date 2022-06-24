package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.beyond_earth.util.ModInventory;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleSidedEnergyContainer;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ModInventory, SidedInventory {

    private final DefaultedList<ItemStack> inventory;

    public AbstractMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        inventory = DefaultedList.ofSize(getInventorySize(), ItemStack.EMPTY);
    }

    public void tick() {
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    public final SimpleSidedEnergyContainer energyStorage = new SimpleSidedEnergyContainer() {
        @Override
        public long getCapacity() {
            return getMaxGeneration();
        }

        @Override
        public long getMaxInsert(@Nullable Direction side) {
            return getMaxEnergyInsert();
        }

        @Override
        public long getMaxExtract(@Nullable Direction side) {
            return getMaxEnergyExtract();
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public boolean usesEnergy() {
        return false;
    }

    public long getMaxGeneration() {
        return 0;
    }

    public long getEnergyPerTick() {
        return 0;
    }

    public long getMaxEnergyInsert() {
        return 0;
    }

    public long getMaxEnergyExtract() {
        return 0;
    }

    public int getInventorySize() {
        return 0;
    }

    public void setActive(boolean active) {
        if (this.getCachedState().contains(AbstractMachineBlock.LIT)) {
            this.world.setBlockState(this.getPos(), this.getCachedState().with(AbstractMachineBlock.LIT, active));
        }
    }

    public void cumulateEnergy() {
        if (this.energyStorage.amount < this.getMaxGeneration()) {
            this.energyStorage.amount += this.getEnergyPerTick();
        } else if (this.energyStorage.amount > this.getMaxGeneration()) {
            this.energyStorage.amount = this.getMaxGeneration();
        }
        this.markDirty();
    }

    public boolean drainEnergy() {
        return this.drainEnergy(this.getEnergyPerTick());
    }

    public boolean drainEnergy(long amount) {
        if (this.energyStorage.amount - amount > 0) {
            this.energyStorage.amount -= amount;
            this.markDirty();
            return true;
        } else {
            this.energyStorage.amount = 0;
            this.markDirty();
            return false;
        }
    }

    // Send energy to surrounding machines.
    public void energyOut() {
        if (usesEnergy()) {
            for (Direction direction : Direction.values()) {
                EnergyStorageUtil.move(getSideEnergyStorage(direction), EnergyStorage.SIDED.find(world, pos.offset(direction), direction.getOpposite()), Long.MAX_VALUE, null);
            }
        }
    }

    public EnergyStorage getSideEnergyStorage(@Nullable Direction side) {
        return this.energyStorage.getSideStorage(side);
    }

    public long getEnergy() {
        return this.energyStorage.amount;
    }

    public boolean hasEnergy() {
        return this.usesEnergy() && this.energyStorage.amount > 0;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (getInventorySize() > 0) {
            Inventories.readNbt(nbt, this.inventory);
        }
        if (usesEnergy()) {
            this.energyStorage.amount = nbt.getLong("energy");
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (getInventorySize() > 0) {
            Inventories.writeNbt(nbt, this.inventory);
        }
        if (usesEnergy()) {
            nbt.putLong("energy", energyStorage.amount);
        }
    }

    // Updates the chunk every time the energy is changed. Important for updating
    // the screen to show the latest energy value.
    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world instanceof ServerWorld world) {
            world.getChunkManager().markForUpdate(this.pos);
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        ItemStack slotStack = this.getStack(slot);
        return slotStack.isEmpty() || (slotStack.isOf(stack.getItem()) && slotStack.getCount() <= slotStack.getMaxCount());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}