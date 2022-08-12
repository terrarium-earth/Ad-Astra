package com.github.alexnijjar.ad_astra.items;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineBlockItem extends BlockItem {
    public MachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state) {
        if (!world.isClient) {
            if (world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
                NbtCompound nbt = stack.getOrCreateNbt();
                Inventories.readNbt(nbt, machineBlock.getItems());
                if (nbt.contains("energy")) {
                    machineBlock.energyStorage.amount = nbt.getLong("energy");
                }

                if (machineBlock instanceof FluidMachineBlockEntity fluidMachine) {
                    if (nbt.contains("inputFluid")) {
                        fluidMachine.inputTank.variant = FluidVariant.fromNbt(nbt.getCompound("inputFluid"));
                        fluidMachine.inputTank.amount = nbt.getLong("inputAmount");
                    }

                    if (nbt.contains("outputFluid")) {
                        fluidMachine.outputTank.variant = FluidVariant.fromNbt(nbt.getCompound("outputFluid"));
                        fluidMachine.outputTank.amount = nbt.getLong("outputAmount");
                    }

                    if (machineBlock instanceof OxygenDistributorBlockEntity oxygenDistributorMachine) {
                        if (nbt.contains("showOxygen")) {
                            oxygenDistributorMachine.setShowOxygen(nbt.getBoolean("showOxygen"));
                        }
                    }
                }
            }
        }
        return super.postPlacement(pos, world, player, stack, state);
    }
}
