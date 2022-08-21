package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import com.github.alexnijjar.ad_astra.util.FluidUtils;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity {

    public final SingleVariantStorage<FluidVariant> inputTank = FluidUtils.createTank(this, getInputSize());
    public final SingleVariantStorage<FluidVariant> outputTank = FluidUtils.createTank(this, getOutputSize());

    public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public abstract long getInputSize();

    public abstract long getOutputSize();

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inputTank.variant = FluidVariant.fromNbt(nbt.getCompound("inputFluid"));
        inputTank.amount = nbt.getLong("inputAmount");

        outputTank.variant = FluidVariant.fromNbt(nbt.getCompound("outputFluid"));
        outputTank.amount = nbt.getLong("outputAmount");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("inputFluid", inputTank.variant.toNbt());
        nbt.putLong("inputAmount", inputTank.amount);

        nbt.put("outputFluid", outputTank.variant.toNbt());
        nbt.putLong("outputAmount", outputTank.amount);
        super.writeNbt(nbt);
    }
}