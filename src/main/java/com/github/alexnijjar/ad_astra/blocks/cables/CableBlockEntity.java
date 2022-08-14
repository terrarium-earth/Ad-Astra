package com.github.alexnijjar.ad_astra.blocks.cables;

import javax.annotation.Nullable;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleSidedEnergyContainer;

public class CableBlockEntity extends BlockEntity {

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE, pos, state);
    }

    @SuppressWarnings("unchecked")
	private final BlockApiCache<EnergyStorage, Direction>[] adjacentCaches = new BlockApiCache[6];

    public final SimpleSidedEnergyContainer energyStorage = new SimpleSidedEnergyContainer() {
        @Override
        public long getCapacity() {
            return energyStorageCapacity();
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

    public EnergyStorage getSideEnergyStorage(@Nullable Direction side) {
        return this.energyStorage.getSideStorage(side);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.energyStorage.amount = nbt.getLong("energy");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("energy", energyStorage.amount);
    }

    public long energyStorageCapacity() {
        return ((CableBlock) this.getCachedState().getBlock()).getEnergyCapacity();
    }

    public long getMaxEnergyInsert() {
        return ((CableBlock) this.getCachedState().getBlock()).getEnergyTransfer();
    }

    public long getMaxEnergyExtract() {
        return ((CableBlock) this.getCachedState().getBlock()).getEnergyTransfer();
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world instanceof ServerWorld world) {
            world.getChunkManager().markForUpdate(this.pos);
        }
    }

    private BlockApiCache<EnergyStorage, Direction> getAdjacentCache(Direction direction) {
		if (adjacentCaches[direction.getId()] == null) {
			adjacentCaches[direction.getId()] = BlockApiCache.create(EnergyStorage.SIDED, (ServerWorld) world, pos.offset(direction));
		}
		return adjacentCaches[direction.getId()];
	}

	@Nullable
	BlockEntity getAdjacentBlockEntity(Direction direction) {
		return getAdjacentCache(direction).getBlockEntity();
	}

    @SuppressWarnings("deprecation")
    public void tick() {
        if (this.world instanceof ServerWorld severWorld) {
            if (!severWorld.isChunkLoaded(this.getPos())) {
                return;
            }

            for (Direction dir : Direction.values()) {
                BlockApiCache<EnergyStorage, Direction> adjacentEnergy = this.getAdjacentCache(dir);

                if (adjacentEnergy == null) {
                    continue;
                }
                
                if (adjacentEnergy.getBlockEntity() instanceof CableBlockEntity adjacentCable) {

                }


                







                // try (Transaction transaction = Transaction.openOuter()) {




                //     if (storage.getAmount() < this.energyStorage.amount) {
                //         long amountMoved = EnergyStorageUtil.move(getSideEnergyStorage(dir), storage, Long.MAX_VALUE, transaction);
                //         transaction.commit();
                //     }
                // }
            }
        }
    }
}