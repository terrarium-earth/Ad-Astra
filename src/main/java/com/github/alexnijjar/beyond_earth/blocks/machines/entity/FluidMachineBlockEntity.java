package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity {

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
		@Override
		protected FluidVariant getBlankVariant() {
			return FluidVariant.blank();
		}
 
		@Override
		protected long getCapacity(FluidVariant variant) {
			return getBuckets() * FluidConstants.BUCKET;
		}
 
		@Override
        protected void onFinalCommit() {
            markDirty();
        }
        
        @Override
        protected boolean canInsert(FluidVariant variant) {
            return true;
        };
    };
    
    public abstract int getBuckets();

    public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
		fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
		fluidStorage.amount = nbt.getLong("amount");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
		nbt.putLong("amount", fluidStorage.amount);
		super.writeNbt(nbt);
    }
}